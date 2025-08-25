package concessionaria.negocio.entidades;
import java.time.LocalDate;

import concessionaria.fachada.Vendedor;

public abstract class Funcionario extends Pessoa {
    public Funcionario(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, dataNascimento);
    }
}