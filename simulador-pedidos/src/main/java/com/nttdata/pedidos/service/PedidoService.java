package com.nttdata.pedidos.service;
import com.nttdata.pedidos.dto.PedidoRequest;
import com.nttdata.pedidos.dto.ItemPedidoRequest;
import com.nttdata.pedidos.dto.ProdutoResponse;
import com.nttdata.pedidos.model.Pedido;
import com.nttdata.pedidos.model.ItemPedido;
import com.nttdata.pedidos.model.StatusPedido;
import com.nttdata.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${catalogo.service.url:http://catalogo-produtos}")
    private String catalogoServiceUrl;
    
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
    
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }
    
    public Pedido simularPedido(PedidoRequest request) {
        Pedido pedido = new Pedido(request.getCliente());
        
        for (ItemPedidoRequest itemRequest : request.getItens()) {
            String url = catalogoServiceUrl + "/api/produtos/" + itemRequest.getProdutoId();
            
            try {
                ResponseEntity<ProdutoResponse> response = restTemplate.getForEntity(url, ProdutoResponse.class);
                
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    ProdutoResponse produto = response.getBody();
                    
                    String estoqueUrl = catalogoServiceUrl + "/api/produtos/" + 
                                       produto.getId() + "/estoque/" + itemRequest.getQuantidade();
                    
                    ResponseEntity<Map> estoqueResponse = restTemplate.getForEntity(estoqueUrl, Map.class);
                    
                    if (estoqueResponse.getBody() != null && 
                        Boolean.TRUE.equals(estoqueResponse.getBody().get("disponivel"))) {
                        
                        ItemPedido item = new ItemPedido(
                            produto.getId(),
                            produto.getNome(),
                            itemRequest.getQuantidade(),
                            produto.getPreco()
                        );
                        item.setPedido(pedido);
                        pedido.getItens().add(item);
                        
                        pedido.setValorTotal(pedido.getValorTotal().add(item.getSubtotal()));
                    } else {
                        throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
                    }
                } else {
                    throw new RuntimeException("Produto não encontrado: " + itemRequest.getProdutoId());
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao consultar produto: " + e.getMessage());
            }
        }
        
        return pedidoRepository.save(pedido);
    }
    
    public Optional<Pedido> confirmarPedido(Long id) {
        return pedidoRepository.findById(id)
            .map(pedido -> {
                if (pedido.getStatus() == StatusPedido.PENDENTE) {
                    for (ItemPedido item : pedido.getItens()) {
                        String url = catalogoServiceUrl + "/api/produtos/" + 
                                   item.getProdutoId() + "/reduzir-estoque/" + item.getQuantidade();
                        
                        try {
                            ResponseEntity<Map> response = restTemplate.postForEntity(url, null, Map.class);
                            if (response.getBody() == null || 
                                !Boolean.TRUE.equals(response.getBody().get("sucesso"))) {
                                throw new RuntimeException("Erro ao reduzir estoque do produto: " + item.getNomeProduto());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("Erro na comunicação com o catálogo: " + e.getMessage());
                        }
                    }
                    
                    pedido.setStatus(StatusPedido.CONFIRMADO);
                    return pedidoRepository.save(pedido);
                }
                return pedido;
            });
    }
    
    public Optional<Pedido> cancelarPedido(Long id) {
        return pedidoRepository.findById(id)
            .map(pedido -> {
                pedido.setStatus(StatusPedido.CANCELADO);
                return pedidoRepository.save(pedido);
            });
    }
}