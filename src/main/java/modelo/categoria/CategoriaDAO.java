package modelo.categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexaoBD;

/**
 * Classe que implementa o padrão DAO para a entidade Categoria
 */
public class CategoriaDAO {

    /**
     * Método utilizado para listar todas as categorias
     *
     * @return
     */
    public List<Categoria> listar() {
        List<Categoria> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, descricao FROM categoria ORDER BY descricao");
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getInt("id"));
                categoria.setDescricao(resultSet.getString("descricao"));
                resultado.add(categoria);
            }
        } catch (SQLException ex) {
            return resultado;
        }
        return resultado;
    }

    /**
     * Método utilizado para obter uma categoria existente pelo id
     *
     * @param id
     * @return
     */
    public Categoria obter(int id) {
        Categoria categoria = null;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, descricao FROM categoria WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    categoria = new Categoria();
                    categoria.setId(resultSet.getInt("id"));
                    categoria.setDescricao(resultSet.getString("descricao"));
                }
            }
        } catch (SQLException ex) {
            return null;
        }
        return categoria;
    }

    /**
     * Método utilizado para inserir uma nova categoria
     *
     * @param descricao
     * @return
     */
    public boolean inserir(String descricao) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO categoria (descricao) VALUES (?)")) {
            preparedStatement.setString(1, descricao);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

    /**
     * Método utilizado para atualizar uma categoria existente
     *
     * @param id
     * @param descricao
     * @return
     */
    public boolean atualizar(int id, String descricao) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE categoria SET descricao = ? WHERE id = ?")) {
            preparedStatement.setString(1, descricao);
            preparedStatement.setInt(2, id);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

    /**
     * Método utilizado para excluir uma categoria existente
     *
     * @param id
     * @return
     */
    public boolean excluir(int id) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM categoria WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

}
