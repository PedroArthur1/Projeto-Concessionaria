package usuarios;
import java.util.List;

import concessionaria.Concessionaria;
import entidades.Cliente;
import entidades.Veiculo;
import excessoes.VeiculoNaoEncontradoException;
import excessoes.cliente.ClienteNaoEncontradoException;
import transacoes.Transacao;

import java.time.LocalDate;

public class Gerente extends Vendedor {
    public Gerente(String nome, String cpf, int idade) {
        super(nome, cpf, idade);
    }

    // Métodos herdados do Vendedor (não precisam ser reescritos, a menos que a lógica mude)

    // Funcionalidades de Gerente
    public void adicionarVeiculo(Concessionaria concessionaria, Veiculo veiculo) {
        concessionaria.adicionarVeiculo(veiculo);
    }

    public void removerVeiculo(Concessionaria concessionaria, String modelo) throws VeiculoNaoEncontradoException {
        concessionaria.removerVeiculo(modelo);
    }

    public void editarDadosVeiculo(Concessionaria concessionaria, String modelo, double novoPreco) throws VeiculoNaoEncontradoException {
        concessionaria.editarDadosVeiculo(modelo, novoPreco);
    }

    public Cliente consultarCliente(Concessionaria concessionaria, String cpf) throws ClienteNaoEncontradoException {
        return concessionaria.buscarCliente(cpf);
    }

    public void editarDadosCliente(Concessionaria concessionaria, String cpf, String novoNome, int novaIdade) throws ClienteNaoEncontradoException {
        concessionaria.editarDadosCliente(cpf, novoNome, novaIdade);
    }

    public void removerCliente(Concessionaria concessionaria, String cpf) throws ClienteNaoEncontradoException {
        concessionaria.removerCliente(cpf);
    }

    public List<Transacao> gerarRelatorio(Concessionaria concessionaria, LocalDate dataInicio, LocalDate dataFim) {
        return concessionaria.gerarRelatorio(dataInicio, dataFim);
    }
}