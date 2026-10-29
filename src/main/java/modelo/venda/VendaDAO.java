package modelo.venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import modelo.ConexaoBD;

/**
 * Classe que implementa o padrão DAO para as entidades Venda e Venda_Produto
 */
public class VendaDAO {

    /**
     * Método utilizado para efetivar uma venda. Tudo acontece em uma única
     * transação: se algum produto não tiver estoque suficiente, nada é gravado
     *
     * @param usuarioId
     * @param itens
     * @return
     */
    public boolean inserir(int usuarioId, List<VendaProduto> itens) {
        if (itens == null || itens.isEmpty()) {
            return false;
        }
        try (Connection connection = ConexaoBD.getConexao()) {
            connection.setAutoCommit(false);
            try {
                int vendaId;
                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO venda (data_hora, usuario_id) VALUES (CURRENT_TIMESTAMP, ?)", Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setInt(1, usuarioId);
                    preparedStatement.executeUpdate();
                    try (ResultSet chaves = preparedStatement.getGeneratedKeys()) {
                        chaves.next();
                        vendaId = chaves.getInt(1);
                    }
                }
                for (VendaProduto item : itens) {
                    // só baixa o estoque se houver quantidade suficiente
                    try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE produto SET quantidade = quantidade - ? WHERE id = ? AND quantidade >= ?")) {
                        preparedStatement.setInt(1, item.getQuantidade());
                        preparedStatement.setInt(2, item.getProdutoId());
                        preparedStatement.setInt(3, item.getQuantidade());
                        if (preparedStatement.executeUpdate() != 1) {
                            connection.rollback();
                            return false;
                        }
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO venda_produto (venda_id, produto_id, preco, quantidade) VALUES (?, ?, ?, ?)")) {
                        preparedStatement.setInt(1, vendaId);
                        preparedStatement.setInt(2, item.getProdutoId());
                        preparedStatement.setDouble(3, item.getPreco());
                        preparedStatement.setInt(4, item.getQuantidade());
                        preparedStatement.executeUpdate();
                    }
                }
                connection.commit();
                return true;
            } catch (SQLException ex) {
                connection.rollback();
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Método utilizado para listar as compras de um cliente
     *
     * @param usuarioId
     * @return
     */
    public List<Venda> listarPorUsuario(int usuarioId) {
        return consultar("WHERE v.usuario_id = ?", usuarioId);
    }

    /**
     * Método utilizado para listar as compras de todos os clientes
     *
     * @return
     */
    public List<Venda> listar() {
        return consultar("", null);
    }

    private List<Venda> consultar(String filtro, Integer usuarioId) {
        Map<Integer, Venda> vendas = new LinkedHashMap<>();
        String sql = "SELECT v.id, v.data_hora, v.usuario_id, vp.produto_id, vp.preco, vp.quantidade "
                + "FROM venda v LEFT JOIN venda_produto vp ON vp.venda_id = v.id "
                + filtro + " ORDER BY v.data_hora DESC, v.id DESC";
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (usuarioId != null) {
                preparedStatement.setInt(1, usuarioId);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Venda venda = vendas.get(id);
                    if (venda == null) {
                        venda = new Venda();
                        venda.setId(id);
                        venda.setDataHora(resultSet.getTimestamp("data_hora").toLocalDateTime());
                        venda.setUsuarioId(resultSet.getInt("usuario_id"));
                        vendas.put(id, venda);
                    }
                    int produtoId = resultSet.getInt("produto_id");
                    if (!resultSet.wasNull()) {
                        VendaProduto item = new VendaProduto();
                        item.setVendaId(id);
                        item.setProdutoId(produtoId);
                        item.setPreco(resultSet.getDouble("preco"));
                        item.setQuantidade(resultSet.getInt("quantidade"));
                        venda.getItens().add(item);
                    }
                }
            }
        } catch (SQLException ex) {
            return new ArrayList<>(vendas.values());
        }
        return new ArrayList<>(vendas.values());
    }

    /**
     * Método utilizado para remover uma venda (os itens são removidos em
     * cascata)
     *
     * @param id
     * @return
     */
    public boolean excluir(int id) {
        boolean sucesso = false;
        try (Connection connection = ConexaoBD.getConexao();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM venda WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            sucesso = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException ex) {
            return false;
        }
        return sucesso;
    }

}
