<%@page import="java.util.List"%>
<%@page import="modelo.categoria.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../cabecalho.jsp" />

<div class="container" style="flex-direction: column; align-items: center;">
    <h2 class="section-title">Gerenciar Categorias</h2>

    <% String mensagem = (String) request.getAttribute("mensagem");
       if (mensagem != null) { %>
        <p style="font-weight: bold; color: #3e2723;"><%= mensagem %></p>
    <% } %>

    <div style="margin-bottom: 20px;">
        <a href="<%= request.getContextPath() %>/admin/CategoriaForm" class="buy-btn" style="text-decoration: none; padding: 10px 20px;">+ Nova Categoria</a>
        <a href="<%= request.getContextPath() %>/secure/principal.jsp" class="buy-btn" style="text-decoration: none; padding: 10px 20px;">Voltar ao Painel</a>
    </div>

    <table border="1" cellpadding="10" style="width: 80%; border-collapse: collapse; background: #f5eedc;">
        <thead>
            <tr style="background: #3e2723; color: #eaddcf;">
                <th>ID</th>
                <th>Descrição</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
               if (categorias != null && !categorias.isEmpty()) {
                   for (Categoria c : categorias) { %>
                <tr>
                    <td><%= c.getId() %></td>
                    <td><%= c.getDescricao() %></td>
                    <td style="text-align: center;">
                        <a href="<%= request.getContextPath() %>/admin/ListarCategoriaForm?id=<%= c.getId() %>">Alterar</a> |
                        <a href="<%= request.getContextPath() %>/admin/ExcluirCategoria?id=<%= c.getId() %>" onclick="return confirm('Deseja realmente excluir esta categoria?');">Excluir</a>
                    </td>
                </tr>
            <%     }
               } else { %>
                <tr>
                    <td colspan="3" style="text-align: center;">Nenhuma categoria cadastrada.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>

<jsp:include page="../rodape.jsp" />git add src/main/webapp/admin/
git commit -m "atualização pra entrega(jsp) 3"
git push origin main