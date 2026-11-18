<%@page import="java.util.List"%>
<%@page import="modelo.categoria.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String titulo = "Gerenciar Categorias"; %>
<%@include file="../cabecalho.jsp" %>

<div class="form-box largo">
    <div class="selo">Gabinete do Xerife</div>
    <h2>Gerenciar Categorias</h2>

    <% String msgAdmin = (String) request.getAttribute("mensagem");
       if (msgAdmin != null) { %>
        <p class="dica" style="font-weight: bold; color: #3e2723;"><%= msgAdmin %></p>
    <% } %>

    <div class="acoes" style="margin-bottom: 25px;">
        <a class="nav-btn" href="<%= contexto %>/admin/CategoriaForm">+ Nova Categoria</a>
        <a class="nav-btn" href="<%= contexto %>/secure/principal.jsp">Voltar ao Gabinete</a>
    </div>

    <table class="ficha">
        <thead>
            <tr>
                <th style="width: 15%;">ID</th>
                <th>Descrição</th>
                <th style="width: 30%; text-align: center;">Ações</th>
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
                        <a href="<%= contexto %>/admin/ListarCategoriaForm?id=<%= c.getId() %>">Alterar</a> |
                        <a href="<%= contexto %>/admin/ExcluirCategoria?id=<%= c.getId() %>" onclick="return confirm('Deseja realmente excluir esta categoria?');">Excluir</a>
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

<%@include file="../rodape.jsp" %>