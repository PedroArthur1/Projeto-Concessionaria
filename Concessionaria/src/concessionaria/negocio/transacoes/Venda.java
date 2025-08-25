package concessionaria.negocio.transacoes;
import concessionaria.negocio.entidades.Cliente;
import concessionaria.negocio.entidades.Veiculo;

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