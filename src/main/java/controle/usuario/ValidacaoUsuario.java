package controle.usuario;

import java.util.regex.Pattern;

/**
 * Validação dos dados do usuário no servidor. A validação em JavaScript
 * melhora a experiência, mas pode ser desligada no navegador, por isso os
 * mesmos critérios são conferidos aqui
 */
public class ValidacaoUsuario {

    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern LOGIN = Pattern.compile("^[A-Za-z0-9_.]{3,50}$");
    public static final int SENHA_MINIMA = 6;

    private ValidacaoUsuario() {
    }

    private static boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }

    /**
     * Retorna uma mensagem de erro ou null se os dados estiverem corretos
     *
     * @param nome
     * @param endereco
     * @param email
     * @param login
     * @param senha
     * @param senhaObrigatoria
     * @return
     */
    public static String validar(String nome, String endereco, String email, String login, String senha, boolean senhaObrigatoria) {
        if (vazio(nome) || vazio(endereco) || vazio(email) || vazio(login)) {
            return "Preencha todos os campos obrigatórios";
        }
        if (nome.length() > 100 || endereco.length() > 200 || email.length() > 100) {
            return "Algum campo ultrapassou o tamanho máximo permitido";
        }
        if (!EMAIL.matcher(email).matches()) {
            return "Informe um e-mail válido";
        }
        if (!LOGIN.matcher(login).matches()) {
            return "O login deve ter de 3 a 50 caracteres (letras, números, ponto ou _)";
        }
        if (senhaObrigatoria && vazio(senha)) {
            return "Informe uma senha";
        }
        if (!vazio(senha) && (senha.length() < SENHA_MINIMA || senha.length() > 100)) {
            return "A senha deve ter pelo menos " + SENHA_MINIMA + " caracteres";
        }
        return null;
    }

    public static String limpar(String valor) {
        return valor == null ? null : valor.trim();
    }

}
