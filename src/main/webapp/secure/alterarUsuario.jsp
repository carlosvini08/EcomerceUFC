<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String titulo = "Alterar Dados"; %>
<%@include file="../cabecalho.jsp" %>
<%
    // "dados" é preparado pelo UsuarioFormServlet / AtualizarUsuarioServlet
    Usuario dados = (Usuario) request.getAttribute("dados");
    if (dados == null) {
        dados = usuario;
    }
%>
<div class="form-box">
    <h2>Alterar Dados Pessoais</h2>
    <form action="<%= contexto %>/secure/AtualizarUsuario" method="post" class="form-validado" novalidate>
        <div class="input-group">
            <label for="nome">Nome Completo</label>
            <input type="text" id="nome" name="nome" maxlength="100" autocomplete="name" value="<%= Html.escapar(dados.getNome()) %>" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="endereco">Endereço</label>
            <input type="text" id="endereco" name="endereco" maxlength="200" autocomplete="street-address" value="<%= Html.escapar(dados.getEndereco()) %>" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="email">Código Telegráfico (E-mail)</label>
            <input type="email" id="email" name="email" maxlength="100" autocomplete="email" value="<%= Html.escapar(dados.getEmail()) %>" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="login">Nome de Registro (Login)</label>
            <input type="text" id="login" name="login" maxlength="50" autocomplete="username" value="<%= Html.escapar(dados.getLogin()) %>" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="senha">Nova Senha</label>
            <input type="password" id="senha" name="senha" maxlength="100" placeholder="deixe em branco para manter" autocomplete="new-password">
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="confirmacaoSenha">Confirme a Nova Senha</label>
            <input type="password" id="confirmacaoSenha" name="confirmacaoSenha" maxlength="100" autocomplete="new-password">
            <span class="erro-campo"></span>
        </div>
        <button type="submit" class="login-btn">Salvar Alterações</button>
    </form>
    <a href="<%= contexto %>/secure/principal.jsp" class="back-link">← Voltar para Minha Conta</a>
</div>
<%@include file="../rodape.jsp" %>
