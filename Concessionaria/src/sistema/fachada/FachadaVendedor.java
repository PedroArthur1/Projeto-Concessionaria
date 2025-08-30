package sistema.fachada;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import sistema.negocio.entidades.Cliente;
import sistema.negocio.entidades.Veiculo;
import sistema.negocio.excessoes.DataDevolucaoInvalidaException;
import sistema.negocio.excessoes.VeiculoNaoEncontradoException;
import sistema.negocio.excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import sistema.negocio.excessoes.cliente.CPFDeveSerUnicoException;
import sistema.negocio.excessoes.cliente.NomeDoClienteContemNumerosException;

public class FachadaVendedor {
    public FachadaVendedor(){}

    public void cadastrarCliente(Concessionaria concessionaria, Cliente cliente) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros, CPFDeveSerUnicoException {
        concessionaria.adicionarCliente(cliente);
        System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso.");
    }

    public List<Veiculo> consultarModelosDisponiveis(Concessionaria concessionaria) {
        return concessionaria.getEstoqueVeiculos();
    }

    // Método ajustado para receber a placa e o objeto Veiculo
    public void registrarVenda(Concessionaria concessionaria, Cliente cliente, Veiculo veiculo, String metodoPagamento) throws VeiculoNaoEncontradoException {
        concessionaria.registrarVenda(cliente, veiculo.getPlaca(), metodoPagamento);
    }

    // Método ajustado para receber a placa e os outros parâmetros
    public void registrarAluguel(Concessionaria concessionaria, Cliente cliente, String placaAluguel, int diasAluguel, String metodoPagamentoAluguel) throws VeiculoNaoEncontradoException {
        concessionaria.registrarAluguel(cliente, placaAluguel, diasAluguel, metodoPagamentoAluguel);
    }
    
    // Método ajustado para receber a placa e os outros parâmetros
    public void devolverVeiculo(Concessionaria concessionaria, String placa, LocalDate dataDevolucaoReal, double valorDano, double novaQuilometragem) throws VeiculoNaoEncontradoException, DataDevolucaoInvalidaException{
        concessionaria.devolverVeiculo(placa, dataDevolucaoReal, valorDano, novaQuilometragem);
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