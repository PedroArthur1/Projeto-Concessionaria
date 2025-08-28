package concessionaria.negocio.transacoes;
import java.time.LocalDate;

import concessionaria.negocio.entidades.Cliente;
import concessionaria.negocio.entidades.Veiculo;

public class Aluguel extends Transacao {
    private final LocalDate dataDevolucao;
    private final int diasAlugados;
    private double valorTotal;
    private double valorDano;
    private static final double VALOR_DIARIA = 150.0;
    private static final double MULTA_DIARIA = 50.0;

    public Aluguel(Cliente cliente, Veiculo veiculo, String metodoPagamento, int diasAlugados) {
        super(cliente, veiculo, metodoPagamento);
        this.diasAlugados = diasAlugados;
        this.dataDevolucao = dataTransacao.plusDays(diasAlugados);
        this.valorTotal = diasAlugados * VALOR_DIARIA;
    }

    public static double simularTotal(int dias) {
        return dias * VALOR_DIARIA;
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

    public void setValorDano(double valorDano) {
        this.valorDano = valorDano;
    }

    @Override
    public double getValorTransacao() {
        return valorTotal + valorDano;
    }

    @Override
    public String getTipo() {
        return "Aluguel";
    }

    @Override
    public String toString() {
        return "Tipo: Aluguel | " + super.toString() + " | Dias: " + diasAlugados + " | Devolução Prevista: " + dataDevolucao + " | Valor Total: R$" + String.format("%.2f", valorTotal);
    }
}