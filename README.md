Resumo rapido deste sistema de catalogo de produtos e simulaçao de pedidos com persistencia em h2database, voce pode listar os produtos, fazer o pedido, cancelar o pedido, buscar produto por id, buscar produto por nome, criar produto, manipular estoque, todos os produtos estão presentes no h2 database para aplicar persistencia aos produtos criados(como este é apenas um teste os dados do h2 são deletados ao parar a aplicação ou reiniciar o computador)
--------------------------------------------------------------------------------------------------
TUTORIAL DE USO(rodando)
Apenas de dois cliques no arquivo start-all.bat, ou se quiser ir do jeito dificil, no terminal rode todas as 4 pastas seguindo esta ordem eureka-server,catalogo-produtos,simulador-pedidos,api-gateway, cd (nomedapasta) e dai, mvn spring-boot:run(em cada uma das 4 pastas)
após rodar todas voce podera ver o status em http://localhost:8761/
--------------------------------------------------------------------------------------------------
TUTORIAL DE USO(comandos) (use postman para rodar os comandos, é muito mais facil: https://www.postman.com/downloads/)
--------------------------------------------------------------------------------------------------
PARA ACOMPANHAR VIA H2DATABASE
http://localhost:8100/h2-console
JDBC URL: jdbc:h2:mem:catalogo
User Name: sa
Password:(vazio)
--------------------------------------------------------------------------------------------------
COMANDOS PARA H2 DATABASE
Ver todos os produtos:
SELECT * FROM PRODUTOS;
Ver apenas produtos ativos:
SELECT * FROM PRODUTOS WHERE ATIVO = true;
Buscar produtos por faixa de preço:
SELECT * FROM PRODUTOS WHERE PRECO BETWEEN 300 AND 1000;
Contar quantos produtos existem:
SELECT COUNT(*) FROM PRODUTOS;
Ver produtos ordenados por preço:
SELECT * FROM PRODUTOS ORDER BY PRECO DESC;
--------------------------------------------------------------------------------------------------
para listar os produtos do catalogo:
GET---http://localhost:8100/api/produtos
--------------------------------------------------------------------------------------------------
para buscar os produtos por id
GET---http://localhost:8100/api/produtos/(digite aqui o id)
--------------------------------------------------------------------------------------------------
BUSCAR PRODUTOS POR NOME
GET---http://localhost:8100/api/produtos/buscar?nome=(coloque alguma palavra que tenha no nome do produto)
--------------------------------------------------------------------------------------------------
para criar um pedido:
POST---http://localhost:8200/api/pedidos/simular
{
    "cliente": "joaozinho",
    "itens": [
      {
        "produtoId": 1,
        "quantidade": 1
      }
    ]
  }
--------------------------------------------------------------------------------------------------
PARA VERIFICAR O ESTOQUE DE UM ITEM
http://localhost:8100/api/produtos/(ID DO PRODUTO)/estoque/(QUANTIDADE QUE VOCE QUER)
--------------------------------------------------------------------------------------------------
PARA CANCELAR UM PEDIDO
POST http://localhost:8200/api/pedidos/(ID DO PEDIDO)/cancelar
(mostrara a data e a hora do cancelamento e atualizara o pedido como cancelado, ira tambem listar qual foi o pedido )
--------------------------------------------------------------------------------------------------
LISTAR PEDIDOS POR ID
GET---http://localhost:8200/api/pedidos/1
(ira mostrar o pedido e se ele foi cancelado ou nao junto com horario)
--------------------------------------------------------------------------------------------------
LISTAR TODOS OS PEDIDOS
GET---http://localhost:8200/api/pedidos
--------------------------------------------------------------------------------------------------
REDUZIR ESTOQUE DE UM PRODUTO
POST---http://localhost:8100/api/produtos/(id do produto)/reduzir-estoque/(quantidade para diminuir)
--------------------------------------------------------------------------------------------------
CRIAR NOVO PRODUTO AO ESTOQUE!
POST---http://localhost:8100/api/produtos 
  
 {
  "nome": "Smartphone top",
  "descricao": "Smartphone topzera",
  "preco": 1200.00,
  "estoque": 30
}
--------------------------------------------------------------------------------------------------
