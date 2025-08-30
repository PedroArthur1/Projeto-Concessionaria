package sistema.negocio.entidades;

import java.time.LocalDate;
import java.time.Period;

/**
 * @author Emanuel Bezerra
 * @description Classe que define a entidade Cliente do sistema.
 */

public class Cliente {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    public Cliente(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    /**
     * Calcula a idade do cliente com base na data de nascimento usando a biblioteca localdate.
     * @return A idade do cliente em anos.
     */
    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
               ", CPF: " + cpf +
               ", Idade: " + getIdade();
    }
}