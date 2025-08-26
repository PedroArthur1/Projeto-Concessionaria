package concessionaria.fachada;
import concessionaria.negocio.entidades.Cliente;
import concessionaria.negocio.excessoes.PlacaDeveSerUnicaException;
import concessionaria.negocio.excessoes.VeiculoNaoEncontradoException;
import concessionaria.negocio.excessoes.cliente.ClienteNaoEncontradoException;
import concessionaria.negocio.excessoes.cliente.NomeDoClienteContemNumerosException;
import concessionaria.negocio.transacoes.Transacao;

import java.time.LocalDate;
import java.util.List;

public class FachadaGerente extends FachadaVendedor {
    public FachadaGerente(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, dataNascimento);
    }    

    public void adicionarVeiculo(Concessionaria concessionaria, String placa, String modelo, String marca, int ano, double preco, double quilometragem) throws PlacaDeveSerUnicaException {
        concessionaria.adicionarVeiculo(placa, modelo, marca, ano, preco, quilometragem);
    }

    public void removerVeiculo(Concessionaria concessionaria, String modelo) throws VeiculoNaoEncontradoException {
        concessionaria.removerVeiculo(modelo);
    }

    public void registrarAluguel(Concessionaria concessionaria, Cliente cliente, String placa, int dias, String metodoPagamento) throws VeiculoNaoEncontradoException {
        concessionaria.registrarAluguel(cliente, placa, dias, metodoPagamento);
    }

    public void editarDadosVeiculo(Concessionaria concessionaria, String placa, double novoPreco, String novaDisponibilidade) 
            throws VeiculoNaoEncontradoException {
        concessionaria.editarDadosVeiculo(placa, novoPreco, novaDisponibilidade);
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