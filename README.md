# API de Cadastro com Confirmação por E-mail

Esta é uma API REST desenvolvida para gerenciar o fluxo de cadastro de usuários, implementando uma camada extra de segurança através da validação de e-mail via token.

## 🛠 Tecnologias Utilizadas
* **Java 17+**
* **Spring Boot 3**
* **Spring Security** (para autenticação e tokens)
* **Spring Data JPA**
* **Spring Mail** (para envio de e-mails)
* **MySQL/PostgreSQL** (ou o banco que você usou)

## ⚙️ Funcionalidades
- [x] Cadastro de novos usuários.
- [x] Geração de token único para validação.
- [x] Envio automático de e-mail de confirmação.
- [x] Ativação de conta após validação do token.

## 🚀 Como executar o projeto
1. Clone o repositório.
2. Configure as credenciais de e-mail no `application.properties`.
3. Execute `./mvnw spring-boot:run`.
