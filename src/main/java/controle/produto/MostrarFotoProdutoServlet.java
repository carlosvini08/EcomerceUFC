package controle.produto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MostrarFotoProduto")
public class MostrarFotoProdutoServlet extends HttpServlet {

    private static final String UPLOAD_DIR = System.getProperty("user.home") + File.separator + "uploads_ecommerce";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nomeFoto = request.getParameter("foto");
        if (nomeFoto == null || nomeFoto.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File arquivoFoto = new File(UPLOAD_DIR, nomeFoto);
        if (!arquivoFoto.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = getServletContext().getMimeType(arquivoFoto.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setContentLengthLong(arquivoFoto.length());
        Files.copy(arquivoFoto.toPath(), response.getOutputStream());
    }
}