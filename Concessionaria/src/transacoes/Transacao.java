package transacoes;
import java.time.LocalDate;

import entidades.Cliente;
import entidades.Veiculo;

public abstract class Transacao {
    protected Cliente cliente;
    protected Veiculo veiculo;
    protected LocalDate data;
    protected String metodoPagamento;

    public Transacao(Cliente cliente, Veiculo veiculo, String metodoPagamento) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.data = LocalDate.now();
        this.metodoPagamento = metodoPagamento;
    }

    // Getters
    public Cliente getCliente() { return cliente; }
    public Veiculo getVeiculo() { return veiculo; }
    public LocalDate getData() { return data; }
    public String getMetodoPagamento() { return metodoPagamento; }

    public abstract String getTipo();

    @Override
    public String toString() {
        return "Cliente: " + cliente.getNome() + ", Veículo: " + veiculo.getModelo() + ", Data: " + data + ", Pagamento: " + metodoPagamento;
    }
}