package transacoes;
import entidades.Cliente;
import entidades.Veiculo;
import java.time.LocalDate;

public abstract class Transacao {
    protected Cliente cliente;
    protected Veiculo veiculo;
    protected LocalDate dataTransacao; 
    protected String metodoPagamento;
    protected boolean concluida;

    public Transacao(Cliente cliente, Veiculo veiculo, String metodoPagamento) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataTransacao = LocalDate.now(); 
        this.metodoPagamento = metodoPagamento;
        this.concluida = false;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void marcarComoConcluida() {
        this.concluida = true;
    }

    public LocalDate getDataTransacao() {
        return dataTransacao;
    }


    public Cliente getCliente() { return cliente; }
    public Veiculo getVeiculo() { return veiculo; }
    public String getMetodoPagamento() { return metodoPagamento; }

    public abstract String getTipo();

    public abstract double getValorTransacao();

    @Override
    public String toString() {
        return "Cliente: " + cliente.getNome() + ", Veículo: " + veiculo.getModelo() + ", Data: " + dataTransacao + ", Pagamento: " + metodoPagamento;
    }


}