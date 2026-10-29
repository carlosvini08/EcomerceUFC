package modelo.produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexaoBD;

/**
 * Classe que implementa o padrão DAO para a entidade Produto
 */
public class ProdutoDAO {

    private static final String COLUNAS = "id, descricao, detalhes, preco, foto, quantidade, categoria_id";

    private Produto montar(ResultSet resultSet) throws SQLException {
        Produto produto = new Produto();
        produto.setId(resultSet.getInt("id"));
        produto.setDescricao(resultSet.getString("descricao"));
        produto.setDetalhes(resultSet.getString("detalhes"));
        produto.setPreco(resultSet.getDouble("preco"));
        produto.setFoto(resultSet.getString("foto"));
        produto.setQuantidade(resultSet.getInt("quantidade"));
        produto.setCategoriaId(resultSet.getInt("categoria_id"));
        return produto;
    }

    private List<Produto> consultar(String sql) {
        List<Produto> resultado = new ArrayList<>();
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
     * Método utilizado para listar todos os produtos
     *
     * @return
     */
    public List<Produto> listar() {
        return consultar("SELECT " + COLUNAS + " FROM produto ORDER BY descricao");
    }

    /**
     * Método utilizado para listar os produtos que podem ser comprados
     * (quantidade em estoque maior que zero)
     *
     * @return
     */
    public List<Produto> listarEmEstoque() {
        return consultar("SELECT " + COLUNAS + " FROM produto WHERE quantidade > 0 ORDER BY id");
    }

    /**
     * Método utilizado para listar os produtos que estão faltando em estoque
     * (ordenados pela descrição)
     *
     * @return
     */
    public List<Produto> listarEmFalta() {
        return consultar("SELECT " + COLUNAS + " FROM produto WHERE quantidade = 0 ORDER BY descricao");
    }

    /**
     * Método utilizado para obter um produto existente pelo id
     *
     * @param id
     * @return
     */
    public Produto obter(int id) {
        Produto produto = null;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + COLUNAS + " FROM produto WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    produto = montar(resultSet);
                }
            }
        } catch (SQLException ex) {
            return null;
        }
        return produto;
    }

    /**
     * Método utilizado para inserir um novo produto
     *
     * @param descricao
     * @param detalhes
     * @param preco
     * @param foto
     * @param quantidade
     * @param categoriaId
     * @return
     */
    public boolean inserir(String descricao, String detalhes, double preco, String foto, int quantidade, int categoriaId) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO produto (descricao, detalhes, preco, foto, quantidade, categoria_id) VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, descricao);
            preparedStatement.setString(2, detalhes);
            preparedStatement.setDouble(3, preco);
            if (foto == null) {
                preparedStatement.setNull(4, Types.VARCHAR);
            } else {
                preparedStatement.setString(4, foto);
            }
            preparedStatement.setInt(5, quantidade);
            preparedStatement.setInt(6, categoriaId);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

    /**
     * Método utilizado para atualizar um produto existente (também usado para
     * manutenção do estoque)
     *
     * @param id
     * @param descricao
     * @param detalhes
     * @param preco
     * @param foto
     * @param quantidade
     * @param categoriaId
     * @return
     */
    public boolean atualizar(int id, String descricao, String detalhes, double preco, String foto, int quantidade, int categoriaId) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE produto SET descricao = ?, detalhes = ?, preco = ?, foto = ?, quantidade = ?, categoria_id = ? WHERE id = ?")) {
            preparedStatement.setString(1, descricao);
            preparedStatement.setString(2, detalhes);
            preparedStatement.setDouble(3, preco);
            if (foto == null) {
                preparedStatement.setNull(4, Types.VARCHAR);
            } else {
                preparedStatement.setString(4, foto);
            }
            preparedStatement.setInt(5, quantidade);
            preparedStatement.setInt(6, categoriaId);
            preparedStatement.setInt(7, id);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

    /**
     * Método utilizado para excluir um produto existente
     *
     * @param id
     * @return
     */
    public boolean excluir(int id) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM produto WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

}
