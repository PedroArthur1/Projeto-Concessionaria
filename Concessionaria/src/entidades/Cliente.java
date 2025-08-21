package entidades;
import java.util.ArrayList;
import java.util.List;
import transacoes.Transacao;

public class Cliente extends Pessoa {
    private final List<Transacao> historicoTransacoes;

    public Cliente(String nome, String cpf, int idade) { // calcular a idade a patir da data de nascimento
        super(nome, cpf, idade);
        this.historicoTransacoes = new ArrayList<>(); // repositorio de transacoes
    }

    public List<Transacao> getHistoricoTransacoes() {
        return historicoTransacoes;
    }

    public void adicionarTransacao(Transacao transacao) {
        this.historicoTransacoes.add(transacao);
    }
}