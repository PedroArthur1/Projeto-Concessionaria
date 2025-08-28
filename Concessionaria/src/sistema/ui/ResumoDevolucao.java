package sistema.ui;

import sistema.negocio.entidades.Cliente;
import sistema.negocio.entidades.Veiculo;

public record ResumoDevolucao(
    Cliente cliente,
    Veiculo veiculo,
    double multaAtraso,
    double valorDano,
    String motivo // "ATRASO", "DANO" ou "ATRASO + DANO"
) {
    public double totalMulta() {
        return multaAtraso + valorDano;
    }
}
