package usuarios;
import concessionaria.Concessionaria;
import entidades.Cliente;
import entidades.Veiculo;
import excessoes.VeiculoNaoEncontradoException;
import excessoes.cliente.ClienteNaoEncontradoException;
import excessoes.cliente.NomeDoClienteContemNumerosException;

import java.time.LocalDate;
import java.util.List;
import transacoes.Transacao;

public class Gerente extends Vendedor {
    public Gerente(String nome, String cpf, int idade) {
        super(nome, cpf, idade);
    }

    public void adicionarVeiculo(Concessionaria concessionaria, Veiculo veiculo) { // a fachada gerente tem como atributo conssecionaria
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

    public void editarDadosCliente(Concessionaria concessionaria, String cpf, String novoNome, int novaIdade) throws ClienteNaoEncontradoException, NomeDoClienteContemNumerosException {
        if (novoNome.matches(".*\\d.*")) {
            throw new NomeDoClienteContemNumerosException("O nome do cliente não pode conter números.");
        }
        Cliente clienteParaEditar = concessionaria.buscarCliente(cpf);
        clienteParaEditar.setNome(novoNome);
        clienteParaEditar.setIdade(novaIdade);
        System.out.println("Dados do cliente com CPF " + cpf + " editados.");
    }

    public void removerCliente(Concessionaria concessionaria, String cpf) throws ClienteNaoEncontradoException {
        concessionaria.removerCliente(cpf);
    }

    public List<Transacao> gerarRelatorio(Concessionaria concessionaria, LocalDate dataInicio, LocalDate dataFim) {
        return concessionaria.gerarRelatorio(dataInicio, dataFim);
    }
}