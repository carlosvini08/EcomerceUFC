package controle.categoria;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.categoria.CategoriaDAO;

@WebServlet("/admin/ExcluirCategoria")
public class ExcluirCategoriaServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        boolean sucesso = categoriaDAO.excluir(id);

        request.setAttribute("mensagem", sucesso ? "Categoria excluída com sucesso!" : "Erro ao excluir categoria (verifique se há produtos vinculados).");
        request.getRequestDispatcher("/admin/ListarCategorias").forward(request, response);
    }
}