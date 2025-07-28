package com.nttdata.catalogo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CatalogoProdutosApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogoProdutosApplication.class, args);
    }
}