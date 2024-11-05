<h1 align="center">Back-end Sistema De Autenticação</h1>
<p>
<h1 align="center"><img src="./src/main/resources/static/project.gif"></h1>
<br>

Este é um sistema de autenticação desenvolvido com Spring Boot. Ele fornece funcionalidades de autenticação e autorização para aplicações web e APIs RESTful.

URL Frontend: https://github.com/AAndersonSantos/Frontend-Authentication-System

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3.3.4
- Spring Security
- JWT (JSON Web Token) para autenticação
- H2 Database para o banco de dados em memória
- ModelMapper para conversão de objetos
- Lombok para redução de boilerplate no código

## Pré-requisitos

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou superior
- [Maven](https://maven.apache.org/install.html)
- [Git](https://git-scm.com/)

## Instalação e Execução

### 1. Clonar o repositório

Clone o projeto para a sua máquina local:

```bash
git clone https://github.com/SeuUsuario/authentication-system.git
```

## Configuração do Banco de Dados
### application.properties
```
server.port=8081

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
```
## Iniciar o Servidor
Inicie a aplicação com o seguinte comando Maven:

```bash
mvn spring-boot:run
```

## Endpoints Principais
* POST /auth/login: Realiza o login e retorna um token JWT.
* GET /auth/validate-token: Valida o token JWT.
* POST /auth/logout: Realiza o logout do usuário.

## Observações
* A configuração httpOnly e secure do token JWT faz com que o token seja armazenado de forma segura em cookies.
* Certifique-se de atualizar as propriedades para ambientes de produção.



