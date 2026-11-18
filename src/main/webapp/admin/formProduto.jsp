<%@page import="java.util.List"%>
<%@page import="modelo.categoria.Categoria"%>
<%@page import="modelo.produto.Produto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../cabecalho.jsp" />

<% Produto produto = (Produto) request.getAttribute("produto");
   List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias"); %>

<div class="container" style="justify-content: center;">
    <div class="login-box" style="width: 450px;">
        <h2><%= (produto != null) ? "Alterar Produto / Estoque" : "Novo Produto" %></h2>
        <form action="<%= request.getContextPath() %>/admin/SalvarProduto" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="id" value="<%= (produto != null) ? produto.getId() : "" %>" />

            <div class="input-group">
                <label for="descricao">Descrição do Produto:</label>
                <input type="text" id="descricao" name="descricao" required value="<%= (produto != null) ? produto.getDescricao() : "" %>" />
            </div>

            <div class="input-group">
                <label for="detalhes">Detalhes do Produto:</label>
                <input type="text" id="detalhes" name="detalhes" required value="<%= (produto != null && produto.getDetalhes() != null) ? produto.getDetalhes() : "" %>" />
            </div>

            <div class="input-group">
                <label for="preco">Preço (R$):</label>
                <input type="number" step="0.01" min="0" id="preco" name="preco" required value="<%= (produto != null) ? produto.getPreco() : "" %>" />
            </div>

            <div class="input-group">
                <label for="quantidade">Quantidade em Estoque:</label>
                <input type="number" min="0" id="quantidade" name="quantidade" required value="<%= (produto != null) ? produto.getQuantidade() : "1" %>" />
            </div>

            <div class="input-group">
                <label for="categoriaId">Categoria:</label>
                <select id="categoriaId" name="categoriaId" required style="width: 95%; padding: 10px;">
                    <option value="">Selecione uma categoria...</option>
                    <% if (categorias != null) {
                           for (Categoria c : categorias) {
                               boolean selecionada = (produto != null && produto.getCategoriaId() != null && produto.getCategoriaId() == c.getId()); %>
                        <option value="<%= c.getId() %>" <%= selecionada ? "selected" : "" %>><%= c.getDescricao() %></option>
                    <%     }
                       } %>
                </select>
            </div>

            <div class="input-group">
                <label for="foto">Foto do Produto (opcional):</label>
                <input type="file" id="foto" name="foto" accept="image/*" />
                <% if (produto != null && produto.getFoto() != null && !produto.getFoto().isEmpty()) { %>
                    <div style="margin-top: 8px;">
                        <label style="font-weight: normal; font-size: 0.9rem;">
                            <input type="checkbox" name="removerFoto" value="true" style="width: auto;" />
                            Remover foto atual (<%= produto.getFoto() %>)
                        </label>
                    </div>
                <% } %>
            </div>

            <button type="submit" class="login-btn">Salvar Produto</button>
        </form>
        <br/>
        <a href="<%= request.getContextPath() %>/admin/ListarProdutos" class="back-link">Voltar para Lista de Produtos</a>
    </div>
</div>

<jsp:include page="../rodape.jsp" />