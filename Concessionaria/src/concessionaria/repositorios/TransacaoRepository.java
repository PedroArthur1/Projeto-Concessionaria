package concessionaria.repositorios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import transacoes.Transacao;

public class TransacaoRepository {
    private final List<Transacao> historicoTransacoes;

    public TransacaoRepository() {
        this.historicoTransacoes = new ArrayList<>();
    }
    
    // Método para adicionar uma transação
    public void adicionar(Transacao transacao) {
        this.historicoTransacoes.add(transacao);
    }
    
    // Método para listar transações por data
    public List<Transacao> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return historicoTransacoes.stream()
                .filter(t -> !t.getDataTransacao().isBefore(inicio) && !t.getDataTransacao().isAfter(fim))
                .collect(Collectors.toList());
    }
    
    // Método para listar todas as transações
    public List<Transacao> listarTodas() {
        return new ArrayList<>(historicoTransacoes);
    }
}