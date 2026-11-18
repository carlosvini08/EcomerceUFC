<%@page import="java.util.List"%>
<%@page import="modelo.categoria.Categoria"%>
<%@page import="modelo.produto.Produto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Produto produto = (Produto) request.getAttribute("produto");
   List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
   String titulo = (produto != null) ? "Alterar Produto / Estoque" : "Novo Produto"; %>
<%@include file="../cabecalho.jsp" %>

<div class="form-box largo">
    <div class="selo">Gabinete do Xerife</div>
    <h2><%= titulo %></h2>

    <form action="<%= contexto %>/admin/SalvarProduto" method="POST" enctype="multipart/form-data" style="text-align: left;">
        <input type="hidden" name="id" value="<%= (produto != null) ? produto.getId() : "" %>" />

        <div style="margin-bottom: 14px;">
            <label for="descricao" style="display: block; font-weight: bold; margin-bottom: 4px;">Descrição do Produto:</label>
            <input type="text" id="descricao" name="descricao" required
                   value="<%= (produto != null) ? produto.getDescricao() : "" %>"
                   style="width: 100%; padding: 8px; box-sizing: border-box;" />
        </div>

        <div style="margin-bottom: 14px;">
            <label for="detalhes" style="display: block; font-weight: bold; margin-bottom: 4px;">Detalhes do Produto:</label>
            <input type="text" id="detalhes" name="detalhes" required
                   value="<%= (produto != null && produto.getDetalhes() != null) ? produto.getDetalhes() : "" %>"
                   style="width: 100%; padding: 8px; box-sizing: border-box;" />
        </div>

        <div style="margin-bottom: 14px;">
            <label for="preco" style="display: block; font-weight: bold; margin-bottom: 4px;">Preço (R$):</label>
            <input type="number" step="0.01" min="0" id="preco" name="preco" required
                   value="<%= (produto != null) ? produto.getPreco() : "" %>"
                   style="width: 100%; padding: 8px; box-sizing: border-box;" />
        </div>

        <div style="margin-bottom: 14px;">
            <label for="quantidade" style="display: block; font-weight: bold; margin-bottom: 4px;">Quantidade em Estoque:</label>
            <input type="number" min="0" id="quantidade" name="quantidade" required
                   value="<%= (produto != null) ? produto.getQuantidade() : "1" %>"
                   style="width: 100%; padding: 8px; box-sizing: border-box;" />
        </div>

        <div style="margin-bottom: 14px;">
            <label for="categoriaId" style="display: block; font-weight: bold; margin-bottom: 4px;">Categoria:</label>
            <select id="categoriaId" name="categoriaId" required style="width: 100%; padding: 8px; box-sizing: border-box;">
                <option value="">Selecione uma categoria...</option>
                <% if (categorias != null) {
                       for (Categoria c : categorias) {
                           boolean selecionada = (produto != null && produto.getCategoriaId() != null && produto.getCategoriaId() == c.getId()); %>
                    <option value="<%= c.getId() %>" <%= selecionada ? "selected" : "" %>><%= c.getDescricao() %></option>
                <%     }
                   } %>
            </select>
        </div>

        <div style="margin-bottom: 20px;">
            <label for="foto" style="display: block; font-weight: bold; margin-bottom: 4px;">Foto do Produto (opcional):</label>
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

        <div class="acoes">
            <button type="submit" class="nav-btn" style="cursor: pointer;">Salvar Produto</button>
            <a class="nav-btn" href="<%= contexto %>/admin/ListarProdutos">Cancelar</a>
        </div>
    </form>
</div>

<%@include file="../rodape.jsp" %>