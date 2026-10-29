package filtro;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import modelo.usuario.Usuario;

/**
 * Classe que implementa o filtro de segurança da aplicação por meio da
 * identificação da sessão de usuário (área /secure/*)
 */
public class SegurancaFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession sessao = req.getSession(false);
        boolean sucesso = sessao != null && sessao.getAttribute("usuario") instanceof Usuario;
        if (sucesso) {
            chain.doFilter(request, response);
        } else {
            request.setAttribute("mensagem", "Identifique-se para acessar esta área");
            request.setAttribute("tipoMensagem", "erro");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}
