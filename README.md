# Wheeler, Rawson & Co. — E-commerce (Programação para Web I)

E-commerce inspirado no catálogo de Red Dead Redemption, feito com **Java (Servlets + JSP)**, **PostgreSQL** e **HTML/CSS/JavaScript**, seguindo a arquitetura MVC do projeto de exemplo do professor (`smdecommerce_v6`).

## Estrutura (MVC)

```
src/main/java/
  modelo/                 -> camada MODEL
    ConexaoBD.java          configuração única de acesso ao banco
    usuario/  Usuario, UsuarioDAO
    categoria/ Categoria, CategoriaDAO
    produto/  Produto, ProdutoDAO
    venda/    Venda, VendaProduto, VendaDAO
  controle/               -> camada CONTROLLER (servlets)
    acesso/   LoginServlet, LogoutServlet
    usuario/  InserirCliente, UsuarioForm, AtualizarUsuario, RemoverUsuario, ValidacaoUsuario
  filtro/                 -> SegurancaFilter (/secure/*), AdministradorFilter (/admin/*)
  util/Html.java          -> escape de HTML (proteção contra XSS) e formatação de preço
src/main/webapp/          -> camada VIEW
  index.jsp  login.jsp  novoCliente.jsp  carrinho.jsp
  secure/principal.jsp  secure/alterarUsuario.jsp   (exigem sessão)
  cabecalho.jsp  rodape.jsp  css/estilo.css  js/validacao.js  js/catalogo.js
  WEB-INF/web.xml         -> mapeamento de servlets e filtros
sql/banco.sql             -> criação das tabelas + dados iniciais
```

## O que a 2ª entrega cobre

| Requisito | Onde |
|---|---|
| Camada model com as entidades | `modelo/*` (Usuario, Categoria, Produto, Venda, Venda_Produto) |
| Inserção de novo cliente | `novoCliente.jsp` → `/InserirCliente` |
| Validação em JavaScript antes de enviar | `js/validacao.js` (campos obrigatórios, e-mail, login, senha ≥ 6, confirmação) |
| Login/Logout de clientes e administradores com sessão | `/Login`, `/Logout`, filtros em `filtro/` |
| Área exclusiva do cliente | `/secure/principal.jsp` |
| Alterar dados pessoais | `/secure/UsuarioForm` → `/secure/AtualizarUsuario` |
| Excluir cadastro | botão em `principal.jsp` → `/secure/RemoverUsuario` (POST + confirmação) |

## Como rodar

Pré-requisitos: **JDK 21+** e **PostgreSQL**. Não precisa instalar Maven (o projeto usa o Maven Wrapper `mvnw.cmd`).

1. **Criar o banco** (apaga e recria o banco `wheelerrawson`):

   ```bash
   psql -U postgres -f sql/banco.sql
   ```

2. **Subir o servidor** — no VS Code: `Ctrl+Shift+B` (tarefa *Rodar servidor (Jetty)*), que pede a senha do `postgres`. Ou no terminal (PowerShell):

   ```powershell
   $env:DB_SENHA = "sua_senha_do_postgres"; ./mvnw.cmd jetty:run
   ```

3. Abrir **http://localhost:8080**

Variáveis de conexão (opcionais): `DB_URL` (padrão `jdbc:postgresql://localhost:5432/wheelerrawson`), `DB_USUARIO` (padrão `postgres`), `DB_SENHA` (padrão `postgres`).

Para entregar/rodar em Tomcat 10.1+: `./mvnw.cmd clean package` gera `target/wheelerrawson.war`.

### Usuário administrador de teste

Criado pelo `sql/banco.sql` (o enunciado permite cadastrar o administrador direto no banco):
login `admin`, senha `admin123`.
