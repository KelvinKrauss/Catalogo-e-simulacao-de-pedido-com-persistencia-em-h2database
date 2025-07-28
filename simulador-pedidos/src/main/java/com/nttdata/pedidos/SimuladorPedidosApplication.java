package com.nttdata.pedidos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SimuladorPedidosApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimuladorPedidosApplication.class, args);
    }
}