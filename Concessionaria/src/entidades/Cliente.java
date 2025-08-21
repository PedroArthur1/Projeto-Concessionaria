package entidades;
import java.util.ArrayList;
import java.util.List;

import transacoes.Transacao;

public class Cliente extends Pessoa {
    private List<Transacao> historicoTransacoes;

    public Cliente(String nome, String cpf, int idade) {
        super(nome, cpf, idade);
        this.historicoTransacoes = new ArrayList<>();
    }

    public List<Transacao> getHistoricoTransacoes() {
        return historicoTransacoes;
    }

    public void adicionarTransacao(Transacao transacao) {
        this.historicoTransacoes.add(transacao);
    }
}