package controle.categoria;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.categoria.CategoriaDAO;

@WebServlet("/admin/SalvarCategoria")
public class SalvarCategoriaServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        String descricao = request.getParameter("descricao");

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        boolean sucesso;

        if (idParam == null || idParam.trim().isEmpty() || idParam.equals("0")) {
            sucesso = categoriaDAO.inserir(descricao);
        } else {
            int id = Integer.parseInt(idParam);
            sucesso = categoriaDAO.atualizar(id, descricao);
        }

        request.setAttribute("mensagem", sucesso ? "Categoria salva com sucesso!" : "Erro ao salvar categoria.");
        request.getRequestDispatcher("/admin/ListarCategorias").forward(request, response);
    }
}