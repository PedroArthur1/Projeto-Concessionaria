package sistema.negocio.transacoes;
import java.time.LocalDate;

import sistema.negocio.entidades.Cliente;
import sistema.negocio.entidades.Veiculo;

/**
 * Serve como base para Venda, Aluguel e Multa, definindo atributos e comportamentos comuns.
 *
 * @author Pedro Arthur
 * @description Classe que define a transação do sistema.
 */

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
        return "Cliente: " + cliente.getNome() +
               ", Placa: " + veiculo.getPlaca() +
               ", Veículo: " + veiculo.getModelo() +
               ", KM: " + String.format("%.0f", veiculo.getQuilometragem()) +
               ", Data: " + dataTransacao +
               ", Pagamento: " + metodoPagamento;
    }


}