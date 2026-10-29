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
 * Classe de controle utilizada para atualizar os dados pessoais do usuário
 * logado
 */
public class AtualizarUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // entrada
        HttpSession sessao = request.getSession();
        Usuario logado = (Usuario) sessao.getAttribute("usuario");
        int id = logado.getId();
        String nome = ValidacaoUsuario.limpar(request.getParameter("nome"));
        String endereco = ValidacaoUsuario.limpar(request.getParameter("endereco"));
        String email = ValidacaoUsuario.limpar(request.getParameter("email"));
        String login = ValidacaoUsuario.limpar(request.getParameter("login"));
        String senha = request.getParameter("senha");
        String confirmacao = request.getParameter("confirmacaoSenha");
        // processamento
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario atual = usuarioDAO.obterPorId(id);
        String erro = (atual == null) ? "Seu cadastro não foi encontrado" : null;
        if (erro == null) {
            // senha em branco = manter a senha atual
            erro = ValidacaoUsuario.validar(nome, endereco, email, login, senha, false);
        }
        boolean trocarSenha = senha != null && !senha.isEmpty();
        if (erro == null && trocarSenha && !senha.equals(confirmacao)) {
            erro = "A confirmação não confere com a nova senha";
        }
        if (erro == null) {
            Usuario outro = usuarioDAO.obterPorLogin(login);
            if (outro != null && !outro.getId().equals(id)) {
                erro = "Este login já está em uso, escolha outro";
            }
        }
        if (erro == null && !usuarioDAO.atualizar(nome, endereco, email, login, trocarSenha ? senha : atual.getSenha(), id)) {
            erro = "Não foi possível atualizar seus dados";
        }
        // saída
        if (erro == null) {
            sessao.setAttribute("usuario", usuarioDAO.obterPorId(id));
            request.setAttribute("mensagem", "Dados atualizados com sucesso");
            request.setAttribute("tipoMensagem", "sucesso");
            request.setAttribute("dados", usuarioDAO.obterPorId(id));
        } else {
            // devolve o que foi digitado para o usuário corrigir
            Usuario digitado = new Usuario();
            digitado.setNome(nome);
            digitado.setEndereco(endereco);
            digitado.setEmail(email);
            digitado.setLogin(login);
            request.setAttribute("mensagem", erro);
            request.setAttribute("tipoMensagem", "erro");
            request.setAttribute("dados", digitado);
        }
        request.getRequestDispatcher("/secure/alterarUsuario.jsp").forward(request, response);
    }

}
