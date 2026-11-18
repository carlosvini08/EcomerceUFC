<%@page import="modelo.categoria.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Categoria categoria = (Categoria) request.getAttribute("categoria");
   String titulo = (categoria != null) ? "Alterar Categoria" : "Nova Categoria"; %>
<%@include file="../cabecalho.jsp" %>

<div class="form-box">
    <div class="selo">Gabinete do Xerife</div>
    <h2><%= titulo %></h2>

    <form action="<%= contexto %>/admin/SalvarCategoria" method="POST">
        <input type="hidden" name="id" value="<%= (categoria != null) ? categoria.getId() : "" %>" />

        <div style="margin-bottom: 18px; text-align: left;">
            <label for="descricao" style="display: block; font-weight: bold; margin-bottom: 6px;">Descrição da Categoria:</label>
            <input type="text" id="descricao" name="descricao" required
                   value="<%= (categoria != null) ? categoria.getDescricao() : "" %>"
                   style="width: 100%; padding: 10px; box-sizing: border-box;" />
        </div>

        <div class="acoes">
            <button type="submit" class="nav-btn" style="cursor: pointer;">Salvar Categoria</button>
            <a class="nav-btn" href="<%= contexto %>/admin/ListarCategorias">Cancelar</a>
        </div>
    </form>
</div>

<%@include file="../rodape.jsp" %>