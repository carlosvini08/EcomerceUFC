package controle.inicio;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.produto.Produto;
import modelo.produto.ProdutoDAO;

@WebServlet("/Inicio")
public class InicioServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtos = produtoDAO.listarEmEstoque();
        request.setAttribute("produtos", produtos);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}