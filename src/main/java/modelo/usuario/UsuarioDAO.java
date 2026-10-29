package modelo.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexaoBD;

/**
 * Classe que implementa o padrão DAO para a entidade Usuario
 */
public class UsuarioDAO {

    private static final String COLUNAS = "id, nome, endereco, email, login, senha, administrador";

    private Usuario montar(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getInt("id"));
        usuario.setNome(resultSet.getString("nome"));
        usuario.setEndereco(resultSet.getString("endereco"));
        usuario.setEmail(resultSet.getString("email"));
        usuario.setLogin(resultSet.getString("login"));
        usuario.setSenha(resultSet.getString("senha"));
        usuario.setAdministrador(resultSet.getBoolean("administrador"));
        return usuario;
    }

    /**
     * Método utilizado para listar todos os usuários
     *
     * @return
     */
    public List<Usuario> listar() {
        List<Usuario> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + COLUNAS + " FROM usuario ORDER BY nome");
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                resultado.add(montar(resultSet));
            }
        } catch (SQLException ex) {
            return resultado;
        }
        return resultado;
    }

    /**
     * Método utilizado para obter um usuário existente pelo id
     *
     * @param id
     * @return
     */
    public Usuario obterPorId(int id) {
        Usuario usuario = null;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + COLUNAS + " FROM usuario WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = montar(resultSet);
                }
            }
        } catch (SQLException ex) {
            return null;
        }
        return usuario;
    }

    /**
     * Método utilizado para obter um usuário existente pelo login
     *
     * @param login
     * @return
     */
    public Usuario obterPorLogin(String login) {
        Usuario usuario = null;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + COLUNAS + " FROM usuario WHERE login = ?")) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = montar(resultSet);
                }
            }
        } catch (SQLException ex) {
            return null;
        }
        return usuario;
    }

    /**
     * Método utilizado para inserir um novo usuário
     *
     * @param nome
     * @param endereco
     * @param email
     * @param login
     * @param senha
     * @param administrador
     * @return
     */
    public boolean inserir(String nome, String endereco, String email, String login, String senha, boolean administrador) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO usuario (nome, endereco, email, login, senha, administrador) VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, endereco);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, login);
            preparedStatement.setString(5, senha);
            preparedStatement.setBoolean(6, administrador);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

    /**
     * Método utilizado para atualizar os dados pessoais de um usuário
     * existente (não altera o perfil de administrador)
     *
     * @param nome
     * @param endereco
     * @param email
     * @param login
     * @param senha
     * @param id
     * @return
     */
    public boolean atualizar(String nome, String endereco, String email, String login, String senha, int id) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE usuario SET nome = ?, endereco = ?, email = ?, login = ?, senha = ? WHERE id = ?")) {
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, endereco);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, login);
            preparedStatement.setString(5, senha);
            preparedStatement.setInt(6, id);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

    /**
     * Método utilizado para excluir um usuário existente
     *
     * @param id
     * @return
     */
    public boolean excluir(int id) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM usuario WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

}
