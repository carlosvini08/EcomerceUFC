<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String titulo = "Carrinho de Compras"; %>
<%@include file="cabecalho.jsp" %>
<h2 class="section-title">Carrinho de Compras</h2>
<div class="form-box largo">
    <h2>Sua Encomenda</h2>
    <p>O carrinho de compras (com cookies), a alteração de quantidades e a finalização da compra fazem parte da próxima entrega.</p>
    <a href="<%= contexto %>/index.jsp" class="back-link">← Retornar ao Catálogo</a>
</div>
<%@include file="rodape.jsp" %>
