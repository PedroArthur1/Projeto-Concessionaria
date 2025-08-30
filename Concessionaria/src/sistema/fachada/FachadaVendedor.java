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

/**
 * Fachada para as funcionalidades do perfil de vendedor.
 * @author Emanuel Bezerra
 * @description Classe que atua como uma fachada de acesso para o perfil de Vendedor.
 */
public class FachadaVendedor {
    public FachadaVendedor(){}

    public void cadastrarCliente(Concessionaria concessionaria, Cliente cliente) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros, CPFDeveSerUnicoException {
        concessionaria.adicionarCliente(cliente);
        System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso.");
    }

    public List<Veiculo> consultarModelosDisponiveis(Concessionaria concessionaria) {
        return concessionaria.getEstoqueVeiculos();
    }

    public void registrarVenda(Concessionaria concessionaria, Cliente cliente, Veiculo veiculo, String metodoPagamento) throws VeiculoNaoEncontradoException {
        concessionaria.registrarVenda(cliente, veiculo.getPlaca(), metodoPagamento);
    }

    public void registrarAluguel(Concessionaria concessionaria, Cliente cliente, String placaAluguel, int diasAluguel, String metodoPagamentoAluguel) throws VeiculoNaoEncontradoException {
        concessionaria.registrarAluguel(cliente, placaAluguel, diasAluguel, metodoPagamentoAluguel);
    }
    
    public void devolverVeiculo(Concessionaria concessionaria, String placa, LocalDate dataDevolucaoReal, double valorDano, double novaQuilometragem) throws VeiculoNaoEncontradoException, DataDevolucaoInvalidaException{
        concessionaria.devolverVeiculo(placa, dataDevolucaoReal, valorDano, novaQuilometragem);
    }
    
    /**
     * Recomenda veículos para um cliente com base na sua faixa etária.
     * @param concessionaria A fachada da concessionária.
     * @param cliente O cliente para o qual a recomendação será feita.
     * @return Uma lista de veículos recomendados.
     */
    public List<Veiculo> recomendarVeiculos(Concessionaria concessionaria, Cliente cliente) {
        
        int idade = cliente.getIdade();
        System.out.println("Recomendando veículos para cliente com " + idade + " anos...");

        List<Veiculo> recomendados;
        if (idade <= 25) {
            recomendados = concessionaria.getEstoqueVeiculos().stream()
                    .filter(v -> v.getPreco() < 60000) //veiculos ate 60 mil.
                    .collect(Collectors.toList());
        } else if (idade <= 40) {
            recomendados = concessionaria.getEstoqueVeiculos().stream()
                    .filter(v -> v.getPreco() >= 60000 && v.getPreco() <= 150000)
                    .collect(Collectors.toList());//entre 60 e 150 mil.
        } else {
            recomendados = concessionaria.getEstoqueVeiculos().stream()
                    .filter(v -> v.getPreco() > 150000)//acima de 150 mil.
                    .collect(Collectors.toList());
        }
        return recomendados;
    }
}