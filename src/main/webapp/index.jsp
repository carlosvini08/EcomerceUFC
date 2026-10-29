<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="modelo.produto.Produto"%>
<%@page import="modelo.produto.ProdutoDAO"%>
<%@page import="modelo.categoria.Categoria"%>
<%@page import="modelo.categoria.CategoriaDAO"%>
<% String titulo = "Catálogo"; %>
<%@include file="cabecalho.jsp" %>
<%
    // página pública: lista apenas os produtos com estoque maior que zero
    List<Produto> produtos = new ProdutoDAO().listarEmEstoque();
    List<Categoria> categorias = new CategoriaDAO().listar();
    Map<Integer, Boolean> categoriaComProduto = new HashMap<>();
    for (Produto p : produtos) {
        categoriaComProduto.put(p.getCategoriaId(), true);
    }
    if (produtos.isEmpty()) {
%>
<p class="vazio">O armazém está vazio no momento. Volte depois da próxima diligência!</p>
<%
    }
    for (Categoria categoria : categorias) {
        if (!categoriaComProduto.containsKey(categoria.getId())) {
            continue;
        }
%>
<h2 class="section-title"><%= Html.escapar(categoria.getDescricao()) %></h2>
<div class="container">
    <% for (Produto p : produtos) {
        if (!p.getCategoriaId().equals(categoria.getId())) {
            continue;
        }
    %>
    <div class="product-card">
        <% if (p.getQuantidade() <= 5) { %><div class="badge">Esgotando!</div><% } %>
        <div class="product-img">
            <% if (p.getFoto() != null && !p.getFoto().isBlank()) { %>
            <img src="<%= contexto %>/fotos/<%= Html.escapar(p.getFoto()) %>" alt="<%= Html.escapar(p.getDescricao()) %>">
            <% } else { %>
            [ Ilustração de <%= Html.escapar(p.getDescricao()) %> ]
            <% } %>
        </div>
        <h2><%= Html.escapar(p.getDescricao()) %></h2>
        <p><%= Html.escapar(p.getDetalhes()) %></p>
        <div class="price"><%= Html.preco(p.getPreco()) %></div>
        <div class="estoque">Em estoque: <%= p.getQuantidade() %></div>
        <div class="action-btns">
            <button class="buy-btn" onclick="adicionar(this)">Encomendar</button>
            <button class="fav-btn" onclick="favoritar(this)" aria-label="Favoritar">★</button>
        </div>
    </div>
    <% } %>
</div>
<% } %>
<%@include file="rodape.jsp" %>
