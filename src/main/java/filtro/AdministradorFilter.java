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
 * identificação da sessão de usuário do tipo administrador (área /admin/*)
 */
public class AdministradorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession sessao = req.getSession(false);
        Object usuario = (sessao == null) ? null : sessao.getAttribute("usuario");
        boolean sucesso = usuario instanceof Usuario u && Boolean.TRUE.equals(u.getAdministrador());
        if (sucesso) {
            chain.doFilter(request, response);
        } else {
            request.setAttribute("mensagem", "Você não tem permissão para acessar este recurso");
            request.setAttribute("tipoMensagem", "erro");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}
