@echo off
echo Iniciando todos os microserviços...

start cmd /k "cd eureka-server && mvn spring-boot:run"
timeout /t 3

start cmd /k "cd catalogo-produtos && mvn spring-boot:run"
timeout /t 3

start cmd /k "cd simulador-pedidos && mvn spring-boot:run"
timeout /t 3

start cmd /k "cd api-gateway && mvn spring-boot:run"
