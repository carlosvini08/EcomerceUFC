<%--
    Cabeçalho comum a todas as páginas. Antes de incluir, a página deve
    declarar a variável "titulo":  <% String titulo = "Catálogo"; %>
--%>
<%@page import="modelo.usuario.Usuario"%>
<%@page import="util.Html"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // impede que o navegador mostre páginas privadas pelo botão "voltar" após o logout
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    String contexto = request.getContextPath();
    String paginaAtual = request.getServletPath();
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= Html.escapar(titulo) %> - Wheeler, Rawson &amp; Co.</title>
    <link rel="stylesheet" href="<%= contexto %>/css/estilo.css">
</head>
<body>

<header>
    <h1><a href="<%= contexto %>/index.jsp">Wheeler, Rawson &amp; Co.</a></h1>
    <div class="subtitle">Fornecedores de Armas, Roupas e Provisões para o Velho Oeste</div>

    <nav>
        <a class="nav-btn<%= paginaAtual.equals("/index.jsp") ? " ativo" : "" %>" href="<%= contexto %>/index.jsp">Início</a>
        <a class="nav-btn<%= paginaAtual.equals("/carrinho.jsp") ? " ativo" : "" %>" href="<%= contexto %>/carrinho.jsp">Carrinho de Compras: <span id="cart-count">0</span></a>
        <% if (usuario == null) { %>
        <a class="nav-btn<%= paginaAtual.equals("/login.jsp") ? " ativo" : "" %>" href="<%= contexto %>/login.jsp">Identificar-se</a>
        <a class="nav-btn<%= paginaAtual.equals("/novoCliente.jsp") ? " ativo" : "" %>" href="<%= contexto %>/novoCliente.jsp">Cadastrar-se</a>
        <% } else { %>
        <a class="nav-btn<%= paginaAtual.startsWith("/secure/") ? " ativo" : "" %>" href="<%= contexto %>/secure/principal.jsp"><%= usuario.getAdministrador() ? "Gabinete do Xerife" : "Minha Conta" %></a>
        <a class="nav-btn" href="<%= contexto %>/Logout">Sair</a>
        <% } %>
    </nav>
</header>

<main>
<%
    String mensagem = (String) request.getAttribute("mensagem");
    String tipoMensagem = (String) request.getAttribute("tipoMensagem");
    if (mensagem != null && !mensagem.isBlank()) {
%>
<div class="mensagem <%= tipoMensagem == null ? "" : Html.escapar(tipoMensagem) %>" role="alert"><%= Html.escapar(mensagem) %></div>
<% } %>
