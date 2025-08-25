package concessionaria.negocio.entidades;

import java.time.LocalDate;

public class Cliente extends Pessoa {

    public Cliente(String nome, String cpf, LocalDate dataNascimento) { // calcular a idade a patir da data de nascimento
        super(nome, cpf, dataNascimento);

    }

}