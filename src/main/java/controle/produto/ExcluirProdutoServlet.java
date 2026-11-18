package controle.produto;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.produto.ProdutoDAO;

@WebServlet("/admin/ExcluirProduto")
public class ExcluirProdutoServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProdutoDAO produtoDAO = new ProdutoDAO();
        boolean sucesso = produtoDAO.excluir(id);

        request.setAttribute("mensagem", sucesso ? "Produto excluído com sucesso!" : "Erro ao excluir produto.");
        request.getRequestDispatcher("/admin/ListarProdutos").forward(request, response);
    }
}