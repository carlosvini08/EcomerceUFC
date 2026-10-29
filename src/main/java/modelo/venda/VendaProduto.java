package modelo.venda;

/**
 * Classe que representa a entidade Venda_Produto (item de uma venda)
 *
 * O preço é gravado no momento da compra, assim mudanças futuras no preço do
 * produto não alteram as vendas já efetivadas
 */
public class VendaProduto {

    private Integer vendaId;
    private Integer produtoId;
    private Double preco;
    private Integer quantidade;

    public Integer getVendaId() {
        return vendaId;
    }

    public void setVendaId(Integer vendaId) {
        this.vendaId = vendaId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return preco * quantidade;
    }

}
