# Transactions — API de Transações e Análise de Fraude

> *Projeto de aprendizado.* Este repositório foi desenvolvido para praticar conceitos de desenvolvimento backend, APIs REST, persistência de dados e mensageria. É um projeto didático.

## Sobre o projeto

A aplicação expõe uma API REST para registrar transações. Depois de salvar uma transação no MongoDB com status inicial `PENDENTE`, publica um evento no Apache Kafka. Um consumidor processa o evento, aplica regras simples de demonstração, atualiza o status da transação e publica uma mensagem no tópico de notificações.

O fluxo é assíncrono: a resposta da API confirma o recebimento da solicitação, enquanto o processamento das regras acontece por meio do Kafka.

## Tecnologias utilizadas

* **Java 21**
* **Spring Boot** — criação da aplicação e da API REST
* **Spring Data MongoDB** — persistência das transações
* **Apache Kafka** — comunicação assíncrona por eventos
* **Maven** — gerenciamento de dependências e build
* **Docker Compose** — execução local do MongoDB e do Kafka

## Como funciona

1. O cliente envia uma requisição `POST` para `/v1/transaction` com o valor da transação.
2. A API registra a transação no MongoDB com o status `PENDENTE`.
3. A aplicação publica o evento no tópico Kafka `fraud`.
4. O consumidor de análise de fraude verifica as regras didáticas e atualiza o status para `BLOQUEADO` ou `ACEITO`.
5. O resultado é publicado no tópico `notification` e consumido por um serviço que, atualmente, escreve a mensagem no console.

### Regras de demonstração atuais

* Bloquear transações com valor **maior que 5.000**.
* Bloquear a transação quando forem encontradas **3 ou mais transações registradas no último minuto**.
* Caso nenhuma das condições seja acionada, marcar a transação como `ACEITO`.

Essas regras são intencionalmente simples e servem apenas para estudo. Não representam um sistema real de prevenção a fraudes e não devem ser usadas para tomar decisões financeiras.

## Pré-requisitos

* JDK **21** instalado
* Maven instalado
* Docker Desktop ou Docker Engine com o plugin Docker Compose

## Configuração local

1. Clone ou extraia o projeto e abra um terminal na pasta raiz, onde está o arquivo `pom.xml`.
2. Crie um arquivo `.env` a partir do exemplo:

   **Linux/macOS:**

```bash
   cp .env.example .env
   ```

**Windows (PowerShell):**

```powershell
   Copy-Item .env.example .env
   ```

3. Abra o arquivo `.env` e ajuste as variáveis conforme necessário. Não compartilhe credenciais reais nem envie o arquivo `.env` para o controle de versão.
4. Inicie os serviços de infraestrutura:

```bash
   docker compose up -d
   ```

5. Na pasta do projeto, execute a aplicação:

```bash
   mvn spring-boot:run
   ```

Por padrão, a API Spring Boot fica disponível em `http://localhost:8080`, o MongoDB em `localhost:27017` e o Kafka em `localhost:9092`.

Para encerrar os serviços do Docker Compose:

```bash
docker compose down
```

Os dados do MongoDB ficam no volume Docker `mongodb\\\\\\\\\\\\\\\_data`. Para remover também esse volume de dados de teste, execute `docker compose down -v`.

## Endpoint disponível

### Criar uma transação

`POST /v1/transaction`

Exemplo de requisição:

```http
POST http://localhost:8080/v1/transaction
Content-Type: application/json
```

```json
{
  "value": 250.00
}
```

A API responde com HTTP `201 Created` e uma mensagem indicando que a solicitação foi recebida. O status inicial da transação é `PENDENTE`; o resultado da análise é atualizado posteriormente pelo consumidor Kafka.

Exemplo com `curl`:

```bash
curl -X POST http://localhost:8080/v1/transaction \\\\\\\\\\\\\\\\
  -H "Content-Type: application/json" \\\\\\\\\\\\\\\\
  -d '{"value": 250.00}'
```

## Estrutura do código

```text
src/main/java/com/antony/transactions/
├── fraud/          # Consumo dos eventos e regras didáticas de fraude
├── notification/   # Consumo e impressão das notificações
├── transaction/    # Controller, serviço, entidade, repositório e producer
├── utils/           # DTOs compartilhados
├── KafkaTopicConfig.java
└── TransactionsApplication.java

src/main/resources/
└── application.yaml # Configuração da aplicação

docs/                # Diagrama de apoio ao projeto
compose.yaml         # MongoDB e Kafka para execução local
pom.xml              # Dependências e configuração Maven
```

## Escopo e limitações

Este projeto tem finalidade exclusivamente educacional e pode conter simplificações, limitações ou bugs. Entre outros pontos, as regras de fraude são básicas, a notificação é apenas impressa no console e não há garantias de segurança, resiliência, observabilidade ou consistência adequadas para um sistema financeiro real.

Use somente dados fictícios em ambiente local. Antes de qualquer uso além do aprendizado, seria necessária uma revisão técnica e de segurança abrangente, além de testes, tratamento robusto de falhas, autenticação, autorização, monitoramento e regras de negócio apropriadas.

