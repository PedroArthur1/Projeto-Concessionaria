package sistema.repositorios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sistema.negocio.entidades.Cliente;
import sistema.negocio.transacoes.Transacao;

public class TransacaoRepository {
    private final List<Transacao> historicoTransacoes;

    public TransacaoRepository() {
        this.historicoTransacoes = new ArrayList<>();
    }
    
    public void adicionar(Transacao transacao) {
        this.historicoTransacoes.add(transacao);
    }
    
    public List<Transacao> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return historicoTransacoes.stream()
                .filter(t -> !t.getDataTransacao().isBefore(inicio) && !t.getDataTransacao().isAfter(fim))
                .collect(Collectors.toList());
    }

    public List<Transacao> listarPorCliente(Cliente cliente) {
        return historicoTransacoes.stream()
                .filter(t -> t.getCliente().getCpf().equals(cliente.getCpf()))
                .collect(Collectors.toList());
    }
    
    // Método para listar todas as transações
    public List<Transacao> listarTodas() {
        return new ArrayList<>(historicoTransacoes);
    }
}