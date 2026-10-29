package controle.acesso;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import modelo.usuario.Usuario;
import modelo.usuario.UsuarioDAO;

/**
 * Classe de controle utilizada para criar uma sessão de usuário (cliente ou
 * administrador)
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // entrada
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        // processamento
        Usuario usuario = null;
        if (login != null && senha != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuario = usuarioDAO.obterPorLogin(login.trim());
        }
        boolean sucesso = usuario != null && usuario.getSenha().equals(senha);
        // saída
        if (sucesso) {
            // descarta a sessão anônima e cria uma nova (evita fixação de sessão)
            HttpSession antiga = request.getSession(false);
            if (antiga != null) {
                antiga.invalidate();
            }
            HttpSession sessao = request.getSession(true);
            sessao.setAttribute("usuario", usuario);
            // redirect: um F5 na página seguinte não reenvia a senha
            response.sendRedirect(request.getContextPath() + "/secure/principal.jsp");
        } else {
            request.setAttribute("mensagem", "Login ou senha inválidos");
            request.setAttribute("tipoMensagem", "erro");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}
