# starwars
# Projeto de API de Personagens de Star Wars

Este projeto é uma API Spring Boot que permite gerenciar personagens de Star Wars. Ele se integra com a SWAPI (Star Wars API) para obter dados atualizados sobre os personagens. A API permite operações CRUD (Criar, Ler, Atualizar, Deletar) para gerenciar os personagens no banco de dados.

## Estrutura do Projeto

- **src/main/java/com/example/demo**: Diretório principal do código fonte.
  - **controller**: Contém os controladores REST da API.
  - **entity**: Contém as entidades JPA que mapeiam as tabelas do banco de dados.
  - **repository**: Contém os repositórios JPA para acesso ao banco de dados.
  - **service**: Contém a lógica de negócio e serviços da aplicação.
  - **DemoApplication.java**: Classe principal que inicia a aplicação Spring Boot.

## Dependências Principais

- Spring Boot
- Spring Data JPA
- Spring Web
- H2 Database (para ambiente de desenvolvimento)
- MySQL (para ambiente de produção)
- Hibernate
- Swagger (para documentação da API)

## Endpoints da API

### GET /api/characters
Retorna todos os personagens.
- **Response**: 200 OK
- **Response Body**: Lista de objetos `CharacterEntity`

### GET /api/characters/{id}
Retorna um personagem específico pelo ID.
- **Path Variable**: `id` (Long)
- **Response**: 200 OK (se encontrado), 404 NOT FOUND (se não encontrado)
- **Response Body**: Objeto `CharacterEntity`

### POST /api/characters
Adiciona um novo personagem.
- **Request Body**: Objeto `CharacterEntity`
- **Response**: 201 CREATED
- **Response Body**: Objeto `CharacterEntity` criado

### PUT /api/characters/{id}
Atualiza um personagem existente pelo ID.
- **Path Variable**: `id` (Long)
- **Request Body**: Objeto `CharacterEntity` atualizado
- **Response**: 200 OK
- **Response Body**: Objeto `CharacterEntity` atualizado

### DELETE /api/characters/{id}
Deleta um personagem pelo ID.
- **Path Variable**: `id` (Long)
- **Response**: 204 NO CONTENT

## Configuração do Banco de Dados

O projeto está configurado para usar um banco de dados H2 em ambiente de desenvolvimento e MySQL em ambiente de produção.

### Configuração H2 (development)

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

