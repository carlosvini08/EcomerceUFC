package controle.acesso;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Classe de controle utilizada para encerrar a sessão de um usuário
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession(false);
        if (sessao != null) {
            sessao.invalidate();
        }
        request.setAttribute("mensagem", "Sua sessão foi encerrada. Até a próxima, parceiro!");
        request.setAttribute("tipoMensagem", "sucesso");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
