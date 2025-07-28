package com.nttdata.catalogo.config;
import com.nttdata.catalogo.model.Produto;
import com.nttdata.catalogo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        produtoRepository.save(new Produto("Notebook Dell", "Notebook Dell Inspiron 15", new BigDecimal("2500.00"), 10));
        produtoRepository.save(new Produto("Mouse Logitech", "Mouse sem fio Logitech MX Master", new BigDecimal("350.00"), 25));
        produtoRepository.save(new Produto("Teclado Mecânico", "Teclado mecânico RGB", new BigDecimal("450.00"), 15));
        produtoRepository.save(new Produto("Monitor 24\"", "Monitor Full HD 24 polegadas", new BigDecimal("800.00"), 8));
        produtoRepository.save(new Produto("Webcam HD", "Webcam 1080p com microfone", new BigDecimal("200.00"), 20));
    }
}