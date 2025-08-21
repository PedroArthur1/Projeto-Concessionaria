package usuarios;
import entidades.Pessoa;

abstract class Funcionario extends Pessoa {
    public Funcionario(String nome, String cpf, int idade) {
        super(nome, cpf, idade);
    }
}