package controle.produto;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.categoria.Categoria;
import modelo.categoria.CategoriaDAO;
import modelo.produto.Produto;
import modelo.produto.ProdutoDAO;

@WebServlet("/admin/ListarProdutoForm")
public class ListarProdutoFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto = produtoDAO.obter(id);

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> categorias = categoriaDAO.listar();

        request.setAttribute("produto", produto);
        request.setAttribute("categorias", categorias);
        request.getRequestDispatcher("/admin/formProduto.jsp").forward(request, response);
    }
}