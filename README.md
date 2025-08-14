# Desafio BTG

## Descrição

API resonsável por buscar pedidos e registrar novos pedidos

## Detalhes de como montar o ambiente

* Instalar o Docker [Docker link](https://docs.docker.com/desktop/install/windows-install/)
* Instalar o Docker composer [Docker Compose Link](https://docs.docker.com/compose/install/)
* Executar o comando **docker-compose up -d** para montar o ambiente
* Executar o ./mvnw test para validar se o projeto está funcionando
* Executar "./mvnw quarkus:dev -Dquarkus.profile=local" para rodar em modo de desenvolvimento

## Detalhes sobre a API

* A Api possui uma rota para acessar a documentação do swagger <http://servidor:porta/api/docs>
* Respostas de Sucesso
  * Status code 200
  * Status code 201
  * Status code 202
* Respostas de Erro
  * messagem(String)
  * Status Code 400 (parâmetros faltando)
  * Status Code 401 (Usuário não autorizado)
  * Status Code 404 (conteúdo não encontrado)
  * Status Code 500 para erro interno

## Estrura base do projeto

* A **main** dentro da **src** inicia o servidor
* A pasta **infra** contém as aplicações de terceiros
* A pasta **core** contém as regras de negócio
* A pasta **app** contém as regras da aplicação,
como o gerenciamento de quais classes vão ser chamadas e formatação de dados
* A pasta **shared** contém os arquivos que são acessado por várias pastas

## Detalhe técnico

* Arquitetura base - [Port and Adapter](https://alistair.cockburn.us/hexagonal-architecture/)
* DIP - [Dependency inversion principle](https://medium.com/@tbaragao/solid-d-i-p-dependency-inversion-principle-e87527f8d0be)
* Swagger
* Quarkus (versão 3.25.1) - [Quarkus](https://quarkus.io/)
* Java (versão 21)
* PostgreSQL (versão 17.5-alpine)
* RabbitMQ (versão 4.1.management-alpine)
