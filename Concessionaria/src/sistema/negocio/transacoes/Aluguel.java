package sistema.negocio.transacoes;
import java.time.LocalDate;

import sistema.negocio.entidades.Cliente;
import sistema.negocio.entidades.Veiculo;

/**
 * Estende a classe Transacao e adiciona a lógica específica para aluguéis.
 *
 * @author Emanuel e Pedro.
 * @description Classe que define uma transação do tipo Aluguel.
 */

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

    /**
     * Simula o valor total de um aluguel com base no número de dias.
     * @param dias totais do aluguek.
     * @return O valor total dos dias X o valor da diaria.
     */
    public static double simularTotal(int dias) { //metodo estatico para auxiliar na simulacao do valor total no menu, para evitar a criacao de um objeto novo somente para obter o valro total
        return dias * VALOR_DIARIA;
    } 

    /**
     * Calcula o valor da multa por atraso na devolução do veículo.
     * Cada dia equivale a 50 reais.
     * @param dataDevolucaoReal A data real de devolução (a data que o cliente devolveu).
     * @return O valor da multa por atraso, ou 0 se não houver atraso.
     */
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