package concessionaria;
import entidades.Cliente;
import entidades.Veiculo;
import excessoes.VeiculoNaoEncontradoException;
import excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import excessoes.cliente.ClienteNaoEncontradoException;
import excessoes.cliente.NomeDoClienteContemNumerosException;
import java.time.LocalDate;
import java.util.List;
import concessionaria.repositorios.ClienteRepository;
import concessionaria.repositorios.TransacaoRepository;
import concessionaria.repositorios.VeiculoRepository;
import transacoes.Aluguel;
import transacoes.Transacao;
import transacoes.Venda;

public class Concessionaria {
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final TransacaoRepository transacaoRepository;

    public Concessionaria() {
        this.clienteRepository = new ClienteRepository();
        this.veiculoRepository = new VeiculoRepository();
        this.transacaoRepository = new TransacaoRepository();
    }
    
    // Métodos para delegar a responsabilidade ao VeiculoRepository
    public void adicionarVeiculo(Veiculo veiculo) {
        veiculoRepository.adicionar(veiculo);
        System.out.println("Veículo " + veiculo.getModelo() + " adicionado ao estoque.");
    }

    public void removerVeiculo(String modelo) throws VeiculoNaoEncontradoException {
        veiculoRepository.remover(modelo);
        System.out.println("Veículo " + modelo + " removido do estoque.");
    }

    public void editarDadosVeiculo(String modelo, double novoPreco) throws VeiculoNaoEncontradoException {
        // Busca o veículo pelo modelo e o edita
        Veiculo veiculoParaEditar = veiculoRepository.buscarPorModelo(modelo).stream()
            .findFirst()
            .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado."));
        veiculoParaEditar.setPreco(novoPreco);
        System.out.println("Dados do veículo " + modelo + " editados. Novo preço: R$" + String.format("%.2f", novoPreco));
    }

    // Métodos para delegar a responsabilidade ao ClienteRepository
    public void adicionarCliente(Cliente cliente) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros {
        clienteRepository.adicionar(cliente);
    }

    public Cliente buscarCliente(String cpf) throws ClienteNaoEncontradoException {
        return clienteRepository.buscar(cpf);
    }

    public void removerCliente(String cpf) throws ClienteNaoEncontradoException {
        clienteRepository.remover(cpf);
        System.out.println("Cliente com CPF " + cpf + " removido do sistema.");
    }

    public void editarDadosCliente(String cpf, String novoNome, LocalDate novaDataNascimento) throws ClienteNaoEncontradoException, NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros {
        if (novoNome.matches(".*\\d.*")) {
            throw new NomeDoClienteContemNumerosException("O nome do cliente não pode conter números.");
        }
        
        Cliente clienteParaEditar = buscarCliente(cpf);
        clienteParaEditar.setNome(novoNome);
        clienteParaEditar.setDataNascimento(novaDataNascimento);
        System.out.println("Dados do cliente com CPF " + cpf + " editados.");
    }
    
    // Métodos para delegar a responsabilidade ao TransacaoRepository
    public List<Transacao> gerarRelatorio(LocalDate dataInicio, LocalDate dataFim) {
        return transacaoRepository.listarPorPeriodo(dataInicio, dataFim);
    }

    public void registrarVenda(Cliente cliente, Veiculo veiculo, String metodoPagamento) throws VeiculoNaoEncontradoException {
        Veiculo veiculoEmEstoque = veiculoRepository.buscar(veiculo.getModelo(), veiculo.getAno());
        
        Venda novaVenda = new Venda(cliente, veiculoEmEstoque, metodoPagamento);
        transacaoRepository.adicionar(novaVenda);
        veiculoRepository.remover(veiculoEmEstoque.getModelo());
        System.out.println("Venda registrada com sucesso para " + cliente.getNome() + " do veículo " + veiculo.getModelo());
    }

    public void registrarAluguel(Cliente cliente, Veiculo veiculo, String metodoPagamento, int dias) throws VeiculoNaoEncontradoException {
        Veiculo veiculoEmEstoque = veiculoRepository.buscar(veiculo.getModelo(), veiculo.getAno());

        Aluguel novoAluguel = new Aluguel(cliente, veiculoEmEstoque, metodoPagamento, dias);
        transacaoRepository.adicionar(novoAluguel);
        System.out.println("Aluguel registrado com sucesso para " + cliente.getNome() + " do veículo " + veiculo.getModelo());
    }
    
    // Métodos utilitários que agora chamam os repositórios
    public List<Cliente> getClientes() {
        return clienteRepository.listarTodos();
    }

    public List<Veiculo> getEstoqueVeiculos() {
        return veiculoRepository.listarTodos();
    }

    public List<Transacao> getHistoricoTransacoes() {
        return transacaoRepository.listarTodas();
    }
}