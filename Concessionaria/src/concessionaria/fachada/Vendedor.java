package usuarios;
import concessionaria.Concessionaria;
import entidades.Cliente;
import entidades.Veiculo;
import excessoes.DataDevolucaoInvalidaException;
import excessoes.VeiculoNaoEncontradoException;
import excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import excessoes.cliente.CPFDeveSerUnicoException;
import excessoes.cliente.NomeDoClienteContemNumerosException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Vendedor extends Funcionario {
    public Vendedor(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, dataNascimento);
    }

    public void cadastrarCliente(Concessionaria concessionaria, Cliente cliente) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros, CPFDeveSerUnicoException {
        concessionaria.adicionarCliente(cliente);
        System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso.");
    }

    public List<Veiculo> consultarModelosDisponiveis(Concessionaria concessionaria) {
        return concessionaria.getEstoqueVeiculos();
    }

    public void registrarVenda(Concessionaria concessionaria, Cliente cliente, Veiculo veiculo, String metodoPagamento) throws VeiculoNaoEncontradoException {
        concessionaria.registrarVenda(cliente, veiculo, metodoPagamento);
    }

    public void registrarAluguel(Concessionaria concessionaria, Cliente cliente, Veiculo veiculo, String metodoPagamento, int dias) throws VeiculoNaoEncontradoException {
        concessionaria.registrarAluguel(cliente, veiculo, metodoPagamento, dias);
    }

    public void devolverVeiculo(Concessionaria concessionaria, String modelo, int ano, LocalDate dataDevolucaoReal, double valorDano) throws VeiculoNaoEncontradoException, DataDevolucaoInvalidaException{
        concessionaria.devolverVeiculo(modelo, ano, dataDevolucaoReal, valorDano);
    }
    
    public List<Veiculo> recomendarVeiculos(Concessionaria concessionaria, Cliente cliente) {
        
        int idade = cliente.getIdade();
        System.out.println("Recomendando veículos para cliente com " + idade + " anos...");

        List<Veiculo> recomendados;
        if (idade <= 25) {
            recomendados = concessionaria.getEstoqueVeiculos().stream()
                    .filter(v -> v.getPreco() < 60000)
                    .collect(Collectors.toList());
        } else if (idade <= 40) {
            recomendados = concessionaria.getEstoqueVeiculos().stream()
                    .filter(v -> v.getPreco() >= 60000 && v.getPreco() <= 150000)
                    .collect(Collectors.toList());
        } else {
            recomendados = concessionaria.getEstoqueVeiculos().stream()
                    .filter(v -> v.getPreco() > 150000)
                    .collect(Collectors.toList());
        }
        return recomendados;
    }
}