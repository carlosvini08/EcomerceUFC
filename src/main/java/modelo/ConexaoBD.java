package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária que centraliza a configuração de acesso ao banco de dados.
 *
 * Os valores podem ser sobrescritos pelas variáveis de ambiente DB_URL,
 * DB_USUARIO e DB_SENHA, assim cada integrante da equipe usa a sua senha local
 * sem precisar alterar o código.
 */
public class ConexaoBD {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String JDBC_URL = configuracao("DB_URL", "jdbc:postgresql://localhost:5432/wheelerrawson");
    private static final String JDBC_USUARIO = configuracao("DB_USUARIO", "postgres");
    private static final String JDBC_SENHA = configuracao("DB_SENHA", "postgres");

    private ConexaoBD() {
    }

    private static String configuracao(String nome, String padrao) {
        String valor = System.getenv(nome);
        return (valor == null || valor.isBlank()) ? padrao : valor;
    }

    /**
     * Método utilizado para abrir uma nova conexão com o banco de dados
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConexao() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver do PostgreSQL não encontrado", ex);
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USUARIO, JDBC_SENHA);
    }

}
