package com.nttdata.catalogo.service;
import com.nttdata.catalogo.model.Produto;
import com.nttdata.catalogo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    public List<Produto> listarTodos() {
        return produtoRepository.findByAtivoTrue();
    }
    
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findByIdAndAtivoTrue(id);
    }
    
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome);
    }
    
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    public Optional<Produto> atualizar(Long id, Produto produto) {
        return produtoRepository.findByIdAndAtivoTrue(id)
            .map(p -> {
                p.setNome(produto.getNome());
                p.setDescricao(produto.getDescricao());
                p.setPreco(produto.getPreco());
                p.setEstoque(produto.getEstoque());
                return produtoRepository.save(p);
            });
    }
    
    public boolean excluir(Long id) {
        return produtoRepository.findByIdAndAtivoTrue(id)
            .map(p -> {
                p.setAtivo(false);
                produtoRepository.save(p);
                return true;
            })
            .orElse(false);
    }
    
    public boolean verificarEstoque(Long id, Integer quantidade) {
        return produtoRepository.findByIdAndAtivoTrue(id)
            .map(p -> p.getEstoque() >= quantidade)
            .orElse(false);
    }
    
    public boolean reduzirEstoque(Long id, Integer quantidade) {
        return produtoRepository.findByIdAndAtivoTrue(id)
            .map(p -> {
                if (p.getEstoque() >= quantidade) {
                    p.setEstoque(p.getEstoque() - quantidade);
                    produtoRepository.save(p);
                    return true;
                }
                return false;
            })
            .orElse(false);
    }
}