package entidades;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import transacoes.Transacao;

public class Cliente extends Pessoa {
    private final List<Transacao> historicoTransacoes;

    public Cliente(String nome, String cpf, LocalDate dataNascimento) { // calcular a idade a patir da data de nascimento
        super(nome, cpf, dataNascimento);
        this.historicoTransacoes = new ArrayList<>(); // repositorio de transacoes
    }

    public List<Transacao> getHistoricoTransacoes() {
        return historicoTransacoes;
    }

    public void adicionarTransacao(Transacao transacao) {
        this.historicoTransacoes.add(transacao);
    }
}