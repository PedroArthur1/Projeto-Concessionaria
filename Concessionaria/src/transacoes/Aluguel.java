package transacoes;
import entidades.Cliente;
import entidades.Veiculo;
import java.time.LocalDate;

public class Aluguel extends Transacao {
    private final LocalDate dataDevolucao;
    private final int diasAlugados;
    private double valorTotal;
    private static final double VALOR_DIARIA = 150.0;
    private static final double MULTA_DIARIA = 50.0;

    public Aluguel(Cliente cliente, Veiculo veiculo, String metodoPagamento, int diasAlugados) {
        super(cliente, veiculo, metodoPagamento);
        this.diasAlugados = diasAlugados;
        this.dataDevolucao = dataTransacao.plusDays(diasAlugados);
        this.valorTotal = diasAlugados * VALOR_DIARIA;
    }

    public double calcularMulta(LocalDate dataDevolucaoReal) {
        long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(dataDevolucao, dataDevolucaoReal);
        if (diasAtraso > 0) {
            return diasAtraso * MULTA_DIARIA;
        }
        return 0;
    }

    public void marcarComoConcluida() {
        this.concluida = true;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    @Override
    public String getTipo() {
        return "Aluguel";
    }

    @Override
    public String toString() {
        return "Tipo: Aluguel | " + super.toString() + " | Dias: " + diasAlugados + " | Devolução Prevista: " + dataDevolucao + " | Valor Total: " + valorTotal;
    }
}