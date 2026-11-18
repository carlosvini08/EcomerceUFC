package controle.produto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MostrarFotoProduto")
public class MostrarFotoProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String foto = request.getParameter("foto");
        if (foto == null || foto.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Procura na pasta persistente de uploads no computador
        String pastaUploads = System.getProperty("user.home") + File.separator + "uploads_ecommerce";
        File arquivo = new File(pastaUploads, foto);

        if (!arquivo.exists()) {
            // Fallback caso a foto esteja na pasta antiga do webapp
            String pastaWeb = getServletContext().getRealPath("/imagens");
            if (pastaWeb != null) {
                arquivo = new File(pastaWeb, foto);
            }
        }

        if (arquivo.exists()) {
            String mimeType = getServletContext().getMimeType(arquivo.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setContentLengthLong(arquivo.length());
            Files.copy(arquivo.toPath(), response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}