package concessionaria.negocio.transacoes;

import concessionaria.negocio.entidades.Cliente;
import concessionaria.negocio.entidades.Veiculo;

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
        return "Tipo: Multa | " + super.toString()
             + " | Motivo: " + motivo
             + " | Valor: R$" + String.format("%.2f", valor);
    }
}
