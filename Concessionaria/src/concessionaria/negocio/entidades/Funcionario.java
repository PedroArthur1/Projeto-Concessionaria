package usuarios;
import java.time.LocalDate;

import entidades.Pessoa;

abstract class Funcionario extends Pessoa {
    public Funcionario(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, dataNascimento);
    }
}