package controle.usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import modelo.usuario.Usuario;
import modelo.usuario.UsuarioDAO;

/**
 * Classe de controle utilizada para o usuário logado excluir o seu próprio
 * cadastro. Só aceita POST (vindo do formulário com confirmação), para que um
 * simples link não apague a conta
 */
public class RemoverUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // entrada
        HttpSession sessao = request.getSession();
        Usuario logado = (Usuario) sessao.getAttribute("usuario");
        // processamento
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean sucesso = usuarioDAO.excluir(logado.getId());
        // saída
        if (sucesso) {
            sessao.invalidate();
            request.setAttribute("mensagem", "Seu cadastro foi excluído. Boa viagem, forasteiro!");
            request.setAttribute("tipoMensagem", "sucesso");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            request.setAttribute("mensagem", "Não foi possível excluir seu cadastro");
            request.setAttribute("tipoMensagem", "erro");
            request.getRequestDispatcher("/secure/principal.jsp").forward(request, response);
        }
    }

}
