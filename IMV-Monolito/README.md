
# Dental Analytics Safe

## 1. Integrantes
- RM554012 Gabriel Pescarolli Galiza
- RM554258 Guilherme Gambarão Baptista
- RM553640 Marcelo Vieira Junior

### Descrição do Projeto
Nosso projeto consiste em uma API desenvolvida em Java utilizando o framework Spring Boot para gestão de voluntários em projetos que ajudam pessoas em áreas afetadas por desastres naturais. A API segue a arquitetura monolítica mvc permitindo operações crud utilizando views integradas na aplicação, gerenciadas pelo Thymeleaf.

### Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **JPA / Hibernate**
- **Thymeleaf**
- **Docker**
- **Banco de Dados Oracle**
- **Maven**

### Sumário
- [Integrantes](#1-integrantes)
- [Passos para rodar a Aplicação](#2-passos-para-rodar-a-aplicação-sem-docker)
- [Endpoints Disponíveis](#3-endpoints-disponíveis)
- [Testes](#4-testes)

---
## 2. Passos para rodar a aplicação (Sem Docker):
> É necessário que o RabbitMq esteja rodando para garantir o funcionamento da aplicação.
>
> Recomendamos que você suba a aplicação utilizando o Docker, dessa forma, todos os serviços necessários para o funcionamento também serão iniciados.
>
> Deixamos um passo a passo para você rodar a aplicação com o Docker na documentação raíz do projeto.
>
> Para o funcionamento da aplicação você deve mudar as credenciais do banco de dados no arquivo application.properties.

1. Clone o repositório:
   ```bash
   git clone https://github.com/brielpg/Impacto_Voluntario.git

2. Abra o diretório do projeto clonado:
    ```bash
   cd Impacto_Voluntario/IMV-Monolito

3. Execute o comando para gerar um .jar da aplicação
    ```bash
   mvn clean package

4. Acesse o diretório target para acessar o .jar
    ```bash
    cd target

5. Rode a aplicação
    ```bash
   java -jar impacto.voluntario-0.0.1-SNAPSHOT.jar  

---
## 3. Endpoints Disponíveis
> Ao lado da descrição de cada endpoint está o requisito de autenticação para utiliza-lo.
>
> A aplicação é inicializada com um login `ADMIN` por padrão, email: **admin@email.com**, senha: **admin**

### 3.1 Endpoints de Templates

```http
  /
```

| Método  | Endpoint             | Descrição                                                   | Autenticação |
|:--------|:---------------------|:------------------------------------------------------------|:-------------|
| `GET`   | `/`                  | Acessa a página home                                        | N/A          |
| `GET`   | `/login`             | Acessa a página para realizar o login                       | N/A          |
| `GET`   | `/primeirosSocorros` | Acessa a página que exibe informações de primeiros socorros | N/A          |

### 3.2 Endpoints de Voluntario

```http
  /voluntario
```

| Método     | Endpoint         | Descrição                                           | Autenticação |
|:-----------|:-----------------|:----------------------------------------------------|:-------------|
| `GET`      | `/`              | Acessa a página para cadastro de um novo voluntário | N/A          |
| `GET`      | `/dashboard`     | Acessa o dashboard do usuário logado                | USER         |
| `GET`      | `/ajudar/{id}`   | Endpoint para se associar a uma necessidade         | USER         |
| `GET`      | `/cancelar/{id}` | Endpoint para se desassociar a uma necessidade      | USER         |
| `POST`     | `/`              | Endpoint para criar um novo voluntário              | N/A          |

### 3.3 Endpoints de Necessidade

```http
  /necessidade
```

| Método     | Endpoint          | Descrição                                              | Autenticação |
|:-----------|:------------------|:-------------------------------------------------------|:-------------|
| `GET`      | `/`               | Acessa a página que lista todas as necessidades ativas | USER         |
| `GET`      | `/excluir/{id}`   | Endpoint para exclusão de uma necessidade ativa        | ADMIN        |
| `GET`      | `/finalizar/{id}` | Endpoint para finalizar uma necessidade                | ADMIN        |
| `POST`     | `/`               | Endpoint para criar uma nova necessidade               | ADMIN        |


### 3.4 Endpoints de Solicitação Ajuda

```http
  /ajuda
```

| Método       | Endpoint        | Descrição                                                                                       | Autenticação |
|:-------------|:----------------|:------------------------------------------------------------------------------------------------|:-------------|
| `GET`        | `/`             | Acessa o formulário para solicitação de ajuda em uma área que sofreu com algum desastre natural | N/A          |
| `GET`        | `/aprovar/{id}` | Endpoint para aprovar um pedido de solicitação de ajuda                                         | ADMIN        |
| `GET`        | `/negar/{id}`   | Endpoint para negar um pedido de solicitação de ajuda                                           | ADMIN        |
| `GET`        | `/excluir/{id}` | Endpoint para excluir uma solicitação de ajuda                                                  | ADMIN        |
| `POST`       | `/`             | Endpoint para criar uma nova solicitação de ajuda                                               | N/A          |

### 3.5 Endpoints de Admin

```http
  /admin
```

| Método       | Endpoint  | Descrição                                | Autenticação |
|:-------------|:----------|:-----------------------------------------|:-------------|
| `GET`        | `/painel` | Acessa o painel de controle da aplicação | ADMIN        |

---
## 4. Testes
> Abaixo você verá uma separaçao de testes em dois sub-títulos, uma para os testes unitário e de integração, e uma com dados para os testes manuais da aplicação caso queira testar.
>
> Temos um perfil `ADMIN` cadastrado por padrão, email: **admin@email.com**, senha: **admin**


### 4.1 Testes Unitário e de Integração
> Para realizar os testes da aplicação é importante que você siga os passos abaixo:

1. Clone o repositório:
   ```bash
   git clone https://github.com/brielpg/Impacto_Voluntario.git

2. Abra o diretório do projeto clonado:
    ```bash
   cd Impacto_Voluntario/IMV-Monolito

3. Execute o comando para rodar os testes:
    ```bash
   mvn test

### 4.2 Testes Manuais

### Login
```
Email:   admin@email.com --padrão
Senha:   admin           --padrão
```

### Endereço
```
CEP:            00000-123
Cidade:         São Paulo
UF:             SP
Bairro:         Jardim Paulista
Número:         12
Complemento:    Apto 101
```

### Cadastro de Voluntário
```
Nome:           Voluntário Teste
Email:          voluntario@email.com
Telefone:       (11) 00000-0000
Cpf:            12345678900
Senha:          123!@#Senha
Habilidades:    Logística, Médico(a), Engenheiro(a)
```

### Cadastro de Solicitação de Ajuda
```
Nome:                      Solicitante Teste
Email:                     solicitante@email.com
Telefone:                  (11) 00000-0000
Descrição do Problema:     Região foi afetada com o terremoto, muitas casas desabaram
Tipo de Desastre:          Terremoto
Pessoas Afetadas:          20
Nível de Urgência:         Alto
Ajuda Requerida:           Logística, Abrigo Temporário, Ajuda médica
```

### Cadastro de Necessidade
```
Título:                  Suporte em Região Alagada
Tipo de Desastre:        Enchente
Descrição Detalhada:     Região foi afetada com a enchente devido a chuva, muitas casas foram levadas.
Nível de Urgência:       Altíssimo
Pessoas Afetadas:        50
Habilidades Requeridas:  Resgate, Abrigo Temporário, Ajuda médica
```

---