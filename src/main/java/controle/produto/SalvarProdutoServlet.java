package controle.produto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modelo.produto.Produto;
import modelo.produto.ProdutoDAO;

@WebServlet("/admin/SalvarProduto")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class SalvarProdutoServlet extends HttpServlet {

    private static final String UPLOAD_DIR = System.getProperty("user.home") + File.separator + "uploads_ecommerce";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        String descricao = request.getParameter("descricao");
        String detalhes = request.getParameter("detalhes");
        if (detalhes == null) {
            detalhes = "";
        }
        double preco = Double.parseDouble(request.getParameter("preco").replace(",", "."));
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));
        int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));
        String removerFoto = request.getParameter("removerFoto");

        ProdutoDAO produtoDAO = new ProdutoDAO();
        String nomeFoto = null;

        // Se for edição, recupera a foto atual caso não tenha marcado para remover
        if (idParam != null && !idParam.trim().isEmpty() && !idParam.equals("0")) {
            Produto existente = produtoDAO.obter(Integer.parseInt(idParam));
            if (existente != null && !"true".equals(removerFoto)) {
                nomeFoto = existente.getFoto();
            }
        }

        // Processa upload de nova foto se enviada pelo formulário
        Part fotoPart = request.getPart("foto");
        if (fotoPart != null && fotoPart.getSize() > 0 && fotoPart.getSubmittedFileName() != null && !fotoPart.getSubmittedFileName().isEmpty()) {
            File pastaUpload = new File(UPLOAD_DIR);
            if (!pastaUpload.exists()) {
                pastaUpload.mkdirs();
            }
            nomeFoto = System.currentTimeMillis() + "_" + new File(fotoPart.getSubmittedFileName()).getName();
            File arquivoDestino = new File(pastaUpload, nomeFoto);
            try (InputStream input = fotoPart.getInputStream()) {
                Files.copy(input, arquivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }

        boolean sucesso;
        if (idParam == null || idParam.trim().isEmpty() || idParam.equals("0")) {
            sucesso = produtoDAO.inserir(descricao, detalhes, preco, nomeFoto, quantidade, categoriaId);
        } else {
            int id = Integer.parseInt(idParam);
            sucesso = produtoDAO.atualizar(id, descricao, detalhes, preco, nomeFoto, quantidade, categoriaId);
        }

        request.setAttribute("mensagem", sucesso ? "Produto salvo com sucesso!" : "Erro ao salvar produto.");
        request.getRequestDispatcher("/admin/ListarProdutos").forward(request, response);
    }
}