<%@page import="modelo.categoria.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../cabecalho.jsp" />

<% Categoria categoria = (Categoria) request.getAttribute("categoria"); %>

<div class="container" style="justify-content: center;">
    <div class="login-box" style="width: 400px;">
        <h2><%= (categoria != null) ? "Alterar Categoria" : "Nova Categoria" %></h2>
        <form action="<%= request.getContextPath() %>/admin/SalvarCategoria" method="POST">
            <input type="hidden" name="id" value="<%= (categoria != null) ? categoria.getId() : "" %>" />
            <div class="input-group">
                <label for="descricao">Descrição da Categoria:</label>
                <input type="text" id="descricao" name="descricao" required value="<%= (categoria != null) ? categoria.getDescricao() : "" %>" />
            </div>
            <button type="submit" class="login-btn">Salvar Categoria</button>
        </form>
        <br/>
        <a href="<%= request.getContextPath() %>/admin/ListarCategorias" class="back-link">Voltar para Lista de Categorias</a>
    </div>
</div>

<jsp:include page="../rodape.jsp" />