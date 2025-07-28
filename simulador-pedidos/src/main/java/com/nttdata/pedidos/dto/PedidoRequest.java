package com.nttdata.pedidos.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;
import java.util.List;

public class PedidoRequest {
    
    @NotBlank(message = "Cliente é obrigatório")
    private String cliente;
    
    @NotEmpty(message = "Lista de itens não pode estar vazia")
    @Valid
    private List<ItemPedidoRequest> itens;
    
    public PedidoRequest() {
    }
    
    public PedidoRequest(String cliente, List<ItemPedidoRequest> itens) {
        this.cliente = cliente;
        this.itens = itens;
    }
    
    public String getCliente() { 
        return cliente; 
    }
    
    public void setCliente(String cliente) { 
        this.cliente = cliente; 
    }
    
    public List<ItemPedidoRequest> getItens() { 
        return itens; 
    }
    
    public void setItens(List<ItemPedidoRequest> itens) { 
        this.itens = itens; 
    }
}
