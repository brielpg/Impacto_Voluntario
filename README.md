
# Impacto Voluntário

## Integrantes
- RM554012 Gabriel Pescarolli Galiza
- RM554258 Guilherme Gambarão Baptista
- RM553640 Marcelo Vieira Junior


### Descrição Geral sobre o Projeto
> O projeto consiste em duas API desenvolvidas em Java utilizando o framework Spring Boot.  
As API estão divididas em dois diretórios: 📂**IMV-Monolito/** e 📂**IMV-Microservice/**, em cada um deles você encontrará um **README** detalhando endpoints, testes e outras informações relevantes sobre a respectiva api.


Nosso projeto propõe uma solução inovadora para conectar voluntários a comunidades afetadas por desastres naturais.
Criamos uma plataforma digital que centraliza a gestão de voluntários, permitindo que pessoas interessadas em ajudar possam se cadastrar e se inscrever em campanhas e projetos voltados para a reconstrução e suporte a áreas devastadas.

Além disso, nossa plataforma conta com uma página dedicada a primeiros socorros, onde usuários podem acessar informações essenciais sobre medidas de segurança e atendimento emergencial.
Para fortalecer a mobilização e a resposta rápida, também disponibilizamos um canal de denúncia, permitindo que cidadãos relatem áreas afetadas por desastres naturais e facilitem a coordenação de esforços de ajuda.

### Sumário
- [Integrantes](#integrantes)
- [Descrição Geral sobre o Projeto](#descrição-geral-sobre-o-projeto)
- [Passos para rodar a aplicação](#1-passos-para-rodar-a-aplicação)
- [Videos](#2-vídeos-apresentando-o-projeto)
- [Endpoints Disponíveis](#3-endpoints-disponíveis)
---

## 1. Passos para rodar a aplicação:
> Antes de rodar a aplicação é necessário que você tenha o Docker instalado na sua máquina.

1. Clone o repositório:
   ```bash
   git clone https://github.com/brielpg/Impacto_Voluntario.git

2. Abra o diretório do projeto clonado:
    ```bash
   cd Impacto_Voluntario

3. Abra o Docker Desktop


4. Execute o comando para subir os serviços:
    ```bash
   docker-compose up --build -d

> Pronto, agora está tudo certo para você testar nossa aplicação.

---
## 2. Vídeos apresentando o projeto
`Aplicação`: https://www.youtube.com/watch?v=BUngqOXYSI4

`Pitch`: https://www.youtube.com/watch?v=5ZphSnd5dYQ

---
## 3. Endpoints Disponíveis
> Abaixo você pode verificar alguns dos endpoints disponíveis do projeto.
>
> Você também pode verificar as portas que estão sendo utilizadas, basta acessar a área de "Containers" dentro do Docker Desktop.
>
> É importante ressaltar que caso queira se aprofundar mais nos endpoints acesse a documentação de cada uma das API.

```http
  localhost:8080/
```

| Método  | Endpoint             | Descrição                                                     | Autenticação |
|:--------|:---------------------|:--------------------------------------------------------------|:-------------|
| `GET`   | `/`                  | Acessa a página home                                          | N/A          |
| `GET`   | `/login`             | Acessa a página para realizar o login                         | N/A          |
| `GET`   | `/ajuda`             | Acessa um formulário para envio de solicitação de ajuda       | N/A          |
| `GET`   | `/primeirosSocorros` | Acessa a página que exibe informações de primeiros socorros   | N/A          |
| `GET`   | `/necessidade`       | Acessa a página com a listagem de áreas que precisam de ajuda | USER         |
| `GET`   | `/dashboard`         | Acessa a página de dashboard do usuário logado                | USER         |
| `GET`   | `/admin/painel`      | Acessa o painel de administrador da plataforma                | ADMIN        |

---