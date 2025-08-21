package usuarios;
import java.util.List;
import java.util.stream.Collectors;

import concessionaria.Concessionaria;
import entidades.Cliente;
import entidades.Veiculo;
import excessoes.ParcelasInvalidasException;
import excessoes.VeiculoNaoEncontradoException;
import excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import excessoes.cliente.NomeDoClienteContemNumerosException;

public class Vendedor extends Funcionario {
    public Vendedor(String nome, String cpf, int idade) {
        super(nome, cpf, idade);
    }

    // Funcionalidades do Vendedor
    public void cadastrarCliente(Concessionaria concessionaria, Cliente cliente) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros {
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
    
    // Método de recomendação de veículo (simplificado)
    public List<Veiculo> recomendarVeiculos(Concessionaria concessionaria, Cliente cliente) {
        // Lógica de recomendação: baseada em idade
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