<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String titulo = "Novo Cliente"; %>
<%@include file="cabecalho.jsp" %>
<%
    // em caso de erro no servidor, reapresenta o que o cliente já tinha digitado
    String nome = Html.escapar(request.getParameter("nome"));
    String endereco = Html.escapar(request.getParameter("endereco"));
    String email = Html.escapar(request.getParameter("email"));
    String login = Html.escapar(request.getParameter("login"));
%>
<div class="form-box">
    <h2>Registro de Novo Cliente</h2>
    <p class="dica">Todos os campos são obrigatórios.</p>
    <form action="<%= contexto %>/InserirCliente" method="post" class="form-validado" novalidate>
        <div class="input-group">
            <label for="nome">Nome Completo</label>
            <input type="text" id="nome" name="nome" maxlength="100" placeholder="Arthur Morgan" autocomplete="name" value="<%= nome %>" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="endereco">Endereço</label>
            <input type="text" id="endereco" name="endereco" maxlength="200" placeholder="Acampamento de Horseshoe Overlook" autocomplete="street-address" value="<%= endereco %>" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="email">Código Telegráfico (E-mail)</label>
            <input type="email" id="email" name="email" maxlength="100" placeholder="cidadao@fronteira.com" autocomplete="email" value="<%= email %>" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="login">Nome de Registro (Login)</label>
            <input type="text" id="login" name="login" maxlength="50" placeholder="arthur.morgan" autocomplete="username" value="<%= login %>" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="senha">Senha de Acesso</label>
            <input type="password" id="senha" name="senha" maxlength="100" placeholder="mínimo 6 caracteres" autocomplete="new-password" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="confirmacaoSenha">Confirme a Senha</label>
            <input type="password" id="confirmacaoSenha" name="confirmacaoSenha" maxlength="100" placeholder="repita a senha" autocomplete="new-password" required>
            <span class="erro-campo"></span>
        </div>
        <button type="submit" class="login-btn">Assinar o Registro</button>
    </form>
    <a href="<%= contexto %>/login.jsp" class="back-link">Já possui registro? Identifique-se</a><br>
    <a href="<%= contexto %>/index.jsp" class="back-link">← Retornar ao Catálogo</a>
</div>
<%@include file="rodape.jsp" %>
