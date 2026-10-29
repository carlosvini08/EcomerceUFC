package util;

/**
 * Funções auxiliares usadas pelas páginas JSP
 */
public class Html {

    private Html() {
    }

    /**
     * Escapa caracteres especiais do HTML para evitar que dados digitados pelo
     * usuário sejam interpretados como código (XSS)
     *
     * @param texto
     * @return
     */
    public static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        StringBuilder resultado = new StringBuilder(texto.length());
        for (char c : texto.toCharArray()) {
            switch (c) {
                case '<' -> resultado.append("&lt;");
                case '>' -> resultado.append("&gt;");
                case '&' -> resultado.append("&amp;");
                case '"' -> resultado.append("&quot;");
                case '\'' -> resultado.append("&#39;");
                default -> resultado.append(c);
            }
        }
        return resultado.toString();
    }

    /**
     * Formata um valor monetário no padrão do catálogo ($ 65.00)
     *
     * @param valor
     * @return
     */
    public static String preco(double valor) {
        return String.format(java.util.Locale.US, "$ %.2f", valor);
    }

}
