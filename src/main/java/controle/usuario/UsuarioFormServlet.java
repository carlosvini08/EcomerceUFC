package controle.usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import modelo.usuario.Usuario;
import modelo.usuario.UsuarioDAO;

/**
 * Classe de controle utilizada para preparar o formulário de edição dos dados
 * pessoais do usuário logado
 *
 * O id vem da sessão (e não da URL), assim um usuário não consegue abrir o
 * cadastro de outra pessoa trocando o parâmetro
 */
public class UsuarioFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // entrada
        Usuario logado = (Usuario) request.getSession().getAttribute("usuario");
        // processamento
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obterPorId(logado.getId());
        // saída
        if (usuario == null) {
            request.setAttribute("mensagem", "Não foi possível carregar seus dados");
            request.setAttribute("tipoMensagem", "erro");
            request.getRequestDispatcher("/secure/principal.jsp").forward(request, response);
            return;
        }
        request.setAttribute("dados", usuario);
        request.getRequestDispatcher("/secure/alterarUsuario.jsp").forward(request, response);
    }

}
