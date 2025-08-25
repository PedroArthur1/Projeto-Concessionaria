package concessionaria.fachada;
import java.time.LocalDate;
import java.util.List;

import concessionaria.negocio.entidades.Cliente;
import concessionaria.negocio.excessoes.VeiculoNaoEncontradoException;
import concessionaria.negocio.excessoes.cliente.ClienteNaoEncontradoException;
import concessionaria.negocio.excessoes.cliente.NomeDoClienteContemNumerosException;
import concessionaria.negocio.transacoes.Transacao;

public class Gerente extends Vendedor {
    public Gerente(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, dataNascimento);
    }

    public void adicionarVeiculo(Concessionaria concessionaria, String modelo, String marca, int ano, double preco) {
        concessionaria.adicionarVeiculo(modelo, marca, ano, preco);
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

    public void editarDadosCliente(Concessionaria concessionaria, String cpf, String novoNome, LocalDate novaDataNascimento) throws ClienteNaoEncontradoException, NomeDoClienteContemNumerosException {
        if (novoNome.matches(".*\\d.*")) {
            throw new NomeDoClienteContemNumerosException("O nome do cliente não pode conter números.");
        }
        Cliente clienteParaEditar = concessionaria.buscarCliente(cpf);
        clienteParaEditar.setNome(novoNome);
        clienteParaEditar.setDataNascimento(novaDataNascimento);
        System.out.println("Dados do cliente com CPF " + cpf + " editados.");
    }

    public void removerCliente(Concessionaria concessionaria, String cpf) throws ClienteNaoEncontradoException {
        concessionaria.removerCliente(cpf);
    }

    public List<Transacao> gerarRelatorio(Concessionaria concessionaria, LocalDate dataInicio, LocalDate dataFim) {
        return concessionaria.gerarRelatorio(dataInicio, dataFim);
    }
}