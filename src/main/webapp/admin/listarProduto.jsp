<%@page import="java.util.List"%>
<%@page import="modelo.produto.Produto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String titulo = "Gerenciar Produtos"; %>
<%@include file="../cabecalho.jsp" %>

<div class="form-box largo">
    <div class="selo">Gabinete do Xerife</div>
    <h2>Gerenciar Estoque e Produtos</h2>

    <% String msgAdmin = (String) request.getAttribute("mensagem");
       if (msgAdmin != null) { %>
        <p class="dica" style="font-weight: bold; color: #010101;"><%= msgAdmin %></p>
    <% } %>

    <div class="acoes" style="margin-bottom: 25px;">
        <a class="nav-btn" href="<%= contexto %>/admin/ProdutoForm">+ Novo Produto</a>
        <a class="nav-btn" href="<%= contexto %>/secure/principal.jsp">Voltar ao Gabinete</a>
    </div>

    <table class="ficha">
        <thead>
            <tr>
                <th>ID</th>
                <th>Foto</th>
                <th>Descrição</th>
                <th>Preço</th>
                <th>Estoque</th>
                <th style="text-align: center;">Ações</th>
            </tr>
        </thead>
        <tbody>
            <% List<Produto> produtos = (List<Produto>) request.getAttribute("produtos");
               if (produtos != null && !produtos.isEmpty()) {
                   for (Produto p : produtos) { %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td style="text-align: center;">
                        <% if (p.getFoto() != null && !p.getFoto().isEmpty()) { %>
                            <img src="<%= request.getContextPath() %>/MostrarFotoProduto?foto=<%= p.getFoto() %>" alt="Foto" style="max-height: 45px;" />
                        <% } else { %>
                            -
                        <% } %>
                    </td>
                    <td>
                        <strong><%= p.getDescricao() %></strong><br/>
                        <small><%= (p.getDetalhes() != null) ? p.getDetalhes() : "" %></small>
                    </td>
                    <td>R$ <%= String.format("%.2f", p.getPreco()) %></td>
                    <td><%= p.getQuantidade() %> un.</td>
                    <td style="text-align: center;">
                        <a href="<%= contexto %>/admin/ListarProdutoForm?id=<%= p.getId() %>">Alterar</a> |
                        <a href="<%= contexto %>/admin/ExcluirProduto?id=<%= p.getId() %>" onclick="return confirm('Deseja realmente excluir este produto?');">Excluir</a>
                    </td>
                </tr>
            <%     }
               } else { %>
                <tr>
                    <td colspan="6" style="text-align: center;">Nenhum produto cadastrado.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>

<%@include file="../rodape.jsp" %>