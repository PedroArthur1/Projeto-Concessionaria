package concessionaria;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entidades.Cliente;
import entidades.Veiculo;
import excessoes.VeiculoNaoEncontradoException;
import excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import excessoes.cliente.ClienteNaoEncontradoException;
import excessoes.cliente.NomeDoClienteContemNumerosException;
import transacoes.Aluguel;
import transacoes.Transacao;
import transacoes.Venda;

public class Concessionaria {
    private List<Cliente> clientes;
    private List<Veiculo> estoqueVeiculos;
    private List<Transacao> historicoTransacoes;

    public Concessionaria() {
        this.clientes = new ArrayList<>();
        this.estoqueVeiculos = new ArrayList<>();
        this.historicoTransacoes = new ArrayList<>();
    }

    // Métodos para o Gerente

    public void adicionarVeiculo(Veiculo veiculo) {
        estoqueVeiculos.add(veiculo);
        System.out.println("Veículo " + veiculo.getModelo() + " adicionado ao estoque.");
    }

    public void removerVeiculo(String modelo) throws VeiculoNaoEncontradoException {
        Veiculo veiculoParaRemover = estoqueVeiculos.stream()
                .filter(v -> v.getModelo().equalsIgnoreCase(modelo))
                .findFirst()
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado."));
        estoqueVeiculos.remove(veiculoParaRemover);
        System.out.println("Veículo " + modelo + " removido do estoque.");
    }

    public void editarDadosVeiculo(String modelo, double novoPreco) throws VeiculoNaoEncontradoException {
        Veiculo veiculoParaEditar = estoqueVeiculos.stream()
                .filter(v -> v.getModelo().equalsIgnoreCase(modelo))
                .findFirst()
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado."));
        veiculoParaEditar.setPreco(novoPreco);
        System.out.println("Dados do veículo " + modelo + " editados. Novo preço: R$" + String.format("%.2f", novoPreco));
    }

    public void adicionarCliente(Cliente cliente) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros {

        if (cliente.getNome().matches(".*\\d.*")) {
            throw new NomeDoClienteContemNumerosException("Nome do cliente não pode conter números: " + cliente.getNome() );
        };

        if (!cliente.getCpf().matches("\\d{11}")) {
            throw new CPFClienteDeveConterOnzeNumeros("O CPF deve conter exatamente 11 dígitos numéricos: " + cliente.getCpf());
        }  clientes.add(cliente);
    }

    public Cliente buscarCliente(String cpf) throws ClienteNaoEncontradoException {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));
    }

    public void removerCliente(String cpf) throws ClienteNaoEncontradoException {
        Cliente clienteParaRemover = buscarCliente(cpf);
        clientes.remove(clienteParaRemover);
        System.out.println("Cliente com CPF " + cpf + " removido do sistema.");
    }

    public void editarDadosCliente(String cpf, String novoNome, int novaIdade) throws ClienteNaoEncontradoException {
        Cliente clienteParaEditar = buscarCliente(cpf);
        clienteParaEditar.setNome(novoNome);
        clienteParaEditar.setIdade(novaIdade);
        System.out.println("Dados do cliente com CPF " + cpf + " editados.");
    }

    public List<Transacao> gerarRelatorio(LocalDate dataInicio, LocalDate dataFim) {
        return historicoTransacoes.stream()
                .filter(t -> t.getData().isAfter(dataInicio.minusDays(1)) && t.getData().isBefore(dataFim.plusDays(1)))
                .collect(Collectors.toList());
    }

    // Métodos para o Vendedor

    public void registrarVenda(Cliente cliente, Veiculo veiculo, String metodoPagamento) throws VeiculoNaoEncontradoException {
        // Verificar se o veículo existe no estoque
        Veiculo veiculoEmEstoque = estoqueVeiculos.stream()
            .filter(v -> v.getModelo().equalsIgnoreCase(veiculo.getModelo()) && v.getAno() == veiculo.getAno())
            .findFirst()
            .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não disponível para venda."));

        Venda novaVenda = new Venda(cliente, veiculoEmEstoque, metodoPagamento);
        historicoTransacoes.add(novaVenda);
        cliente.adicionarTransacao(novaVenda);
        estoqueVeiculos.remove(veiculoEmEstoque); // Remove o veículo do estoque
        System.out.println("Venda registrada com sucesso para " + cliente.getNome() + " do veículo " + veiculo.getModelo());
    }

    public void registrarAluguel(Cliente cliente, Veiculo veiculo, String metodoPagamento, int dias) throws VeiculoNaoEncontradoException {
        // Verificar se o veículo existe no estoque
        Veiculo veiculoEmEstoque = estoqueVeiculos.stream()
            .filter(v -> v.getModelo().equalsIgnoreCase(veiculo.getModelo()) && v.getAno() == veiculo.getAno())
            .findFirst()
            .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não disponível para aluguel."));

        Aluguel novoAluguel = new Aluguel(cliente, veiculoEmEstoque, metodoPagamento, dias);
        historicoTransacoes.add(novoAluguel);
        cliente.adicionarTransacao(novoAluguel);
        // Não remove do estoque, mas poderia marcar como "alugado" em um projeto mais complexo
        System.out.println("Aluguel registrado com sucesso para " + cliente.getNome() + " do veículo " + veiculo.getModelo());
    }

    // Getters
    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Veiculo> getEstoqueVeiculos() {
        return estoqueVeiculos;
    }

    public List<Transacao> getHistoricoTransacoes() {
        return historicoTransacoes;
    }
}