
# Impacto Voluntário

## 1. Integrantes
- RM554012 Gabriel Pescarolli Galiza
- RM554258 Guilherme Gambarão Baptista
- RM553640 Marcelo Vieira Junior

### Descrição
Esta aplicação consiste em uma API desenvolvida em Java utilizando o framework Spring Boot, para o envio de emails, ela
recebe através do serviço de mensageria um dto com as informações do email para quem será enviado a notificação, o
título e a mensagem.

A aplicação foi criada para manter um contato mais próximo com o voluntário sempre o notificando a cada
ação feita relacionada a ele na nossa aplicação.

### Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **JPA / Hibernate**
- **Spring Mail**
- **Docker**
- **Maven**

### Sumário
- [Integrantes](#1-integrantes)
- [Descrição](#descrição)
- [Passos para rodar a Aplicação](#2-passos-para-rodar-a-aplicação-sem-docker)
- [Testes](#3-testes)

---
## 2. Passos para rodar a aplicação (Sem Docker):
> É necessário que o RabbitMq esteja rodando para garantir o funcionamento da aplicação.
>
> Recomendamos que você suba a aplicação utilizando o Docker, dessa forma, todos os serviços necessários para o funcionamento também serão iniciados.
>
> Deixamos um passo a passo para você rodar a aplicação com o Docker na documentação raíz do projeto.
>
> Para o funcionamento da aplicação você deve mudar as credenciais do spring mail no arquivo application.properties.

1. Clone o repositório:
   ```bash
   git clone https://github.com/brielpg/Impacto_Voluntario.git

2. Abra o diretório do projeto clonado:
    ```bash
   cd Impacto_Voluntario/IMV-Microservice

3. Execute o comando para gerar um .jar da aplicação
    ```bash
   mvn clean package

4. Acesse o diretório target para acessar o .jar
    ```bash
    cd target

5. Rode a aplicação
    ```bash
   java -jar microservice-0.0.1-SNAPSHOT.jar  

---
## 3. Testes
> Para testar a aplicação você pode realizar alguma ação no monolito, assim passando a informação de um para o outro, ou
> acessando o console do RabbitMq na porta `15672` e enviando uma requisição.

---