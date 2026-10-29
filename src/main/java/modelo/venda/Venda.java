package modelo.venda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a entidade Venda
 */
public class Venda {

    private Integer id;
    private LocalDateTime dataHora;
    private Integer usuarioId;
    private List<VendaProduto> itens = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<VendaProduto> getItens() {
        return itens;
    }

    public void setItens(List<VendaProduto> itens) {
        this.itens = itens;
    }

    /**
     * Valor total da venda (soma de preço x quantidade de cada item)
     *
     * @return
     */
    public double getTotal() {
        double total = 0;
        for (VendaProduto item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

}
