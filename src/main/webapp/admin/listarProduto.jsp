<%@page import="java.util.List"%>
<%@page import="modelo.produto.Produto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../cabecalho.jsp" />

<div class="container" style="flex-direction: column; align-items: center;">
    <h2 class="section-title">Gerenciar Estoque e Produtos</h2>

    <% String mensagem = (String) request.getAttribute("mensagem");
       if (mensagem != null) { %>
        <p style="font-weight: bold; color: #3e2723;"><%= mensagem %></p>
    <% } %>

    <div style="margin-bottom: 20px;">
        <a href="<%= request.getContextPath() %>/admin/ProdutoForm" class="buy-btn" style="text-decoration: none; padding: 10px 20px;">+ Novo Produto</a>
        <a href="<%= request.getContextPath() %>/secure/principal.jsp" class="buy-btn" style="text-decoration: none; padding: 10px 20px;">Voltar ao Painel</a>
    </div>

    <table border="1" cellpadding="10" style="width: 90%; border-collapse: collapse; background: #f5eedc;">
        <thead>
            <tr style="background: #3e2723; color: #eaddcf;">
                <th>ID</th>
                <th>Foto</th>
                <th>Descrição</th>
                <th>Detalhes</th>
                <th>Preço</th>
                <th>Estoque</th>
                <th>Ações</th>
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
                            <img src="<%= request.getContextPath() %>/MostrarFotoProduto?foto=<%= p.getFoto() %>" alt="Foto" style="max-height: 50px;" />
                        <% } else { %>
                            Sem foto
                        <% } %>
                    </td>
                    <td><%= p.getDescricao() %></td>
                    <td><%= (p.getDetalhes() != null) ? p.getDetalhes() : "" %></td>
                    <td>R$ <%= String.format("%.2f", p.getPreco()) %></td>
                    <td><%= p.getQuantidade() %> un.</td>
                    <td style="text-align: center;">
                        <a href="<%= request.getContextPath() %>/admin/ListarProdutoForm?id=<%= p.getId() %>">Alterar / Estoque</a> |
                        <a href="<%= request.getContextPath() %>/admin/ExcluirProduto?id=<%= p.getId() %>" onclick="return confirm('Deseja realmente excluir este produto?');">Excluir</a>
                    </td>
                </tr>
            <%     }
               } else { %>
                <tr>
                    <td colspan="7" style="text-align: center;">Nenhum produto cadastrado.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>

<jsp:include page="../rodape.jsp" />