package sistema.repositorios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sistema.negocio.transacoes.Transacao;

/**
 * @author Pedro Arthur
 * @description Repositório para gerenciar as operações de persistência de transações.
 */

public class TransacaoRepository {
    private final List<Transacao> historicoTransacoes;

    public TransacaoRepository() {
        this.historicoTransacoes = new ArrayList<>();
    }
    
    public void adicionar(Transacao transacao) {
        this.historicoTransacoes.add(transacao);
    }

    /**
     * Lista todas as transações que ocorreram em um período de tempo.
     * @param inicio A data de início do período.
     * @param fim A data de fim do período.
     * @return Uma lista de transações no período especificado.
     */
    public List<Transacao> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return historicoTransacoes.stream()
                .filter(t -> !t.getDataTransacao().isBefore(inicio) && !t.getDataTransacao().isAfter(fim))
                .collect(Collectors.toList());
    }
    
    public List<Transacao> listarTodas() {
        return new ArrayList<>(historicoTransacoes);
    }
}