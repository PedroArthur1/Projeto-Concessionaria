package transacoes;
import entidades.Cliente;
import entidades.Veiculo;
import java.time.LocalDate;

public abstract class Transacao {
    protected Cliente cliente;
    protected Veiculo veiculo;
    // Alteração: o nome do atributo era 'data' nos códigos anteriores
    protected LocalDate dataTransacao; 
    protected String metodoPagamento;

    public Transacao(Cliente cliente, Veiculo veiculo, String metodoPagamento) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        // Alteração: o atributo 'dataTransacao' é inicializado aqui
        this.dataTransacao = LocalDate.now(); 
        this.metodoPagamento = metodoPagamento;
    }

    // Adicione este método getter
    public LocalDate getDataTransacao() {
        return dataTransacao;
    }


    public Cliente getCliente() { return cliente; }
    public Veiculo getVeiculo() { return veiculo; }
    public String getMetodoPagamento() { return metodoPagamento; }

    public abstract String getTipo();

    @Override
    public String toString() {
        return "Cliente: " + cliente.getNome() + ", Veículo: " + veiculo.getModelo() + ", Data: " + dataTransacao + ", Pagamento: " + metodoPagamento;
    }


}