<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String titulo = "Identificação"; %>
<%@include file="cabecalho.jsp" %>
<%
    String loginDigitado = request.getParameter("login");
%>
<div class="form-box">
    <h2>Identificação</h2>
    <p class="dica">Clientes e administradores entram por aqui.</p>
    <form action="<%= contexto %>/Login" method="post" class="form-validado" novalidate>
        <div class="input-group">
            <label for="login">Nome de Registro (Login)</label>
            <input type="text" id="login" name="login" placeholder="arthur.morgan" autocomplete="username"
                   value="<%= Html.escapar(loginDigitado) %>" required>
            <span class="erro-campo"></span>
        </div>
        <div class="input-group">
            <label for="senha">Senha de Acesso</label>
            <input type="password" id="senha" name="senha" placeholder="••••••••" autocomplete="current-password" required>
            <span class="erro-campo"></span>
        </div>
        <button type="submit" class="login-btn">Entrar no Sistema</button>
    </form>
    <a href="<%= contexto %>/novoCliente.jsp" class="back-link">Ainda não tem registro? Cadastre-se</a><br>
    <a href="<%= contexto %>/index.jsp" class="back-link">← Retornar ao Catálogo</a>
</div>
<%@include file="rodape.jsp" %>
