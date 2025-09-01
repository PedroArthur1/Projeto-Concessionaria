package sistema.negocio.transacoes;

import sistema.negocio.entidades.Cliente;
import sistema.negocio.entidades.Veiculo;

/**
 * Representa um resumo da transação de devolução de um veículo, encapsulando
 * informações essenciais em um objeto imutável.
 * 
 * O uso de um {@code record} simplifica a criação de uma classe para
 * transporte de dados, eliminando a necessidade de getters e construtores
 * verbosos.
 *
 * @param cliente O cliente que devolveu o veículo.
 * @param veiculo O veículo que foi devolvido.
 * @param multaAtraso O valor da multa por atraso na devolução.
 * @param valorDano O valor dos danos causados ao veículo.
 * @param motivo O motivo da multa, se houver (ex: "ATRASO", "DANO" ou "ATRASO + DANO").
 * @author Emanuel Bezerra e Pedro Arthur
 * @description Record que encapsula os dados de uma devolução de veículo.
 */

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
