package sistema.negocio.transacoes;
import sistema.negocio.entidades.Cliente;
import sistema.negocio.entidades.Veiculo;

/**
 * Estende a classe Transacao e define o valor como o preço do veículo.
 * @author Emanuel e Pedro
 * @description Classe que define uma transação do tipo Venda.
 */

public class Venda extends Transacao {
    public Venda(Cliente cliente, Veiculo veiculo, String metodoPagamento) {
        super(cliente, veiculo, metodoPagamento);
    }

    @Override
    public String getTipo() {
        return "Venda";
    }
    
    @Override
    public double getValorTransacao() {
        return veiculo.getPreco();
    }

    @Override
    public String toString() {
        return "Tipo: Venda | " + super.toString() + " | Valor: R$" + String.format("%.2f", veiculo.getPreco());
    }
}