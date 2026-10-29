package controle.usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import modelo.usuario.UsuarioDAO;

/**
 * Classe de controle utilizada para inserir um novo usuário do tipo cliente
 */
public class InserirClienteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // entrada
        String nome = ValidacaoUsuario.limpar(request.getParameter("nome"));
        String endereco = ValidacaoUsuario.limpar(request.getParameter("endereco"));
        String email = ValidacaoUsuario.limpar(request.getParameter("email"));
        String login = ValidacaoUsuario.limpar(request.getParameter("login"));
        String senha = request.getParameter("senha");
        String confirmacao = request.getParameter("confirmacaoSenha");
        // processamento
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String erro = ValidacaoUsuario.validar(nome, endereco, email, login, senha, true);
        if (erro == null && !senha.equals(confirmacao)) {
            erro = "A confirmação não confere com a senha";
        }
        if (erro == null && usuarioDAO.obterPorLogin(login) != null) {
            erro = "Este login já está em uso, escolha outro";
        }
        if (erro == null && !usuarioDAO.inserir(nome, endereco, email, login, senha, false)) {
            erro = "Não foi possível realizar o cadastro, tente novamente";
        }
        // saída
        if (erro == null) {
            request.setAttribute("mensagem", "Cadastro realizado com sucesso! Identifique-se para continuar");
            request.setAttribute("tipoMensagem", "sucesso");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.setAttribute("mensagem", erro);
            request.setAttribute("tipoMensagem", "erro");
            request.getRequestDispatcher("/novoCliente.jsp").forward(request, response);
        }
    }

}
