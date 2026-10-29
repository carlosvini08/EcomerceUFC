<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String titulo = "Minha Conta"; %>
<%@include file="../cabecalho.jsp" %>
<div class="form-box largo">
    <% if (usuario.getAdministrador()) { %>
    <div class="selo">Administrador</div>
    <% } %>
    <h2>Bem-vindo, <%= Html.escapar(usuario.getNome()) %>!</h2>

    <table class="ficha">
        <tr><th>Nome</th><td><%= Html.escapar(usuario.getNome()) %></td></tr>
        <tr><th>Endereço</th><td><%= Html.escapar(usuario.getEndereco()) %></td></tr>
        <tr><th>E-mail</th><td><%= Html.escapar(usuario.getEmail()) %></td></tr>
        <tr><th>Login</th><td><%= Html.escapar(usuario.getLogin()) %></td></tr>
    </table>

    <div class="acoes">
        <a class="nav-btn" href="<%= contexto %>/secure/UsuarioForm">Alterar Dados</a>
        <a class="nav-btn" href="<%= contexto %>/index.jsp">Ver Catálogo</a>
        <a class="nav-btn" href="<%= contexto %>/Logout">Sair</a>
    </div>

    <% if (usuario.getAdministrador()) { %>
    <h2 style="margin-top: 35px;">Gabinete do Xerife</h2>
    <p class="dica">Disponível nas próximas entregas:</p>
    <div class="acoes">
        <span class="nav-btn em-breve">Categorias</span>
        <span class="nav-btn em-breve">Produtos</span>
        <span class="nav-btn em-breve">Compras</span>
        <span class="nav-btn em-breve">Relatórios</span>
    </div>
    <% } else { %>
    <h2 style="margin-top: 35px;">Minhas Compras</h2>
    <p class="dica">O histórico de compras estará disponível na próxima entrega.</p>
    <% } %>

    <form action="<%= contexto %>/secure/RemoverUsuario" method="post"
          data-confirmar="Tem certeza que deseja excluir seu cadastro? Essa ação não pode ser desfeita."
          style="margin-top: 35px;">
        <button type="submit" class="danger-btn">Excluir Meu Cadastro</button>
    </form>
</div>
<%@include file="../rodape.jsp" %>
