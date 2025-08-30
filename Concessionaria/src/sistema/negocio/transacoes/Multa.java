package sistema.negocio.transacoes;

import sistema.negocio.entidades.Cliente;
import sistema.negocio.entidades.Veiculo;

/**
 * Estende a classe Transacao e define o valor e o motivo da multa.
 * @author Pedro Arthur
 * @description Classe que define uma transação do tipo Multa.
 */

public class Multa extends Transacao {
    private final double valor;
    private final String motivo; // "ATRASO", "DANO" ou "ATRASO + DANO"

    public Multa(Cliente cliente, Veiculo veiculo, String metodoPagamento, double valor, String motivo) {
        super(cliente, veiculo, metodoPagamento);
        this.valor = valor;
        this.motivo = motivo;
        this.concluida = true; // multa quitada no momento do registro
    }

    @Override
    public String getTipo() {
        return "Multa";
    }

    @Override
    public double getValorTransacao() {
        return valor;
    }

    public String getMotivo() {
        return motivo;
    }

    @Override
    public String toString() {
        return "Tipo: Multa | Cliente: " + cliente.getNome() +
             ", Placa: " + veiculo.getPlaca() +
             ", Veículo: " + veiculo.getModelo() +
             ", Data: " + dataTransacao +
             ", Pagamento: " + metodoPagamento
             + " | Motivo: " + motivo
             + " | Valor: R$" + String.format("%.2f", valor);
    }
}
