package concessionaria;
import entidades.Cliente;
import entidades.Veiculo;
import excessoes.VeiculoNaoEncontradoException;
import excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import excessoes.cliente.CPFDeveSerUnicoException;
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
    public void adicionarVeiculo(String modelo, String marca, int ano, double preco) {
        Veiculo novoVeiculo = new Veiculo(modelo, marca, ano, preco);
        veiculoRepository.adicionar(novoVeiculo);
        System.out.println("Veículo " + novoVeiculo.getModelo() + " adicionado ao estoque.");
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
    public void adicionarCliente(Cliente cliente) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros, CPFDeveSerUnicoException {
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
        
        if (!veiculoEmEstoque.isDisponivelParaAluguel()) {
            throw new VeiculoNaoEncontradoException("Veículo " + veiculo.getModelo() + " não está disponível para venda pois está temporariamente alugado.");
        }

        Venda novaVenda = new Venda(cliente, veiculoEmEstoque, metodoPagamento);
        transacaoRepository.adicionar(novaVenda);
        veiculoRepository.remover(veiculoEmEstoque.getModelo());
        System.out.println("Venda registrada com sucesso para " + cliente.getNome() + " do veículo " + veiculo.getModelo());
    }

    public void registrarAluguel(Cliente cliente, Veiculo veiculo, String metodoPagamento, int dias) throws VeiculoNaoEncontradoException {
        Veiculo veiculoEmEstoque = veiculoRepository.buscar(veiculo.getModelo(), veiculo.getAno());

        if (!veiculoEmEstoque.isDisponivelParaAluguel()) {
            throw new VeiculoNaoEncontradoException("Veículo " + veiculo.getModelo() + " não está disponível para aluguel.");
        }
    
        // Instancia o novo Aluguel com o número de dias.
        // O valor total será calculado internamente na classe Aluguel.
        Aluguel novoAluguel = new Aluguel(cliente, veiculoEmEstoque, metodoPagamento, dias);
        transacaoRepository.adicionar(novoAluguel);
    
        // Atualize o estado do veículo para ficar indisponível
        veiculoEmEstoque.setDisponivelParaAluguel(false);
        veiculoEmEstoque.setDisponivelApos(LocalDate.now().plusDays(dias));
    
        // Apenas ajuste a mensagem para incluir o valor total
        System.out.println("Aluguel registrado com sucesso para " + cliente.getNome() + " do veículo " + veiculo.getModelo() + " | Valor Total: R$" + String.format("%.2f", novoAluguel.getValorTotal()));
    }
    public void devolverVeiculo(String modelo, int ano, LocalDate dataDevolucaoReal) throws VeiculoNaoEncontradoException {
    
        Veiculo veiculoAlugado = veiculoRepository.buscar(modelo, ano);

        // 1. Verifique se o veículo está indisponível para devolução
        if (veiculoAlugado.isDisponivelParaAluguel()) {
            System.out.println("O veículo " + modelo + " não está alugado. Nenhuma ação necessária.");
            return;
        }
    
        // 2. Encontre a transação de aluguel correspondente para calcular a multa
        Aluguel aluguelEmQuestao = null;
        for (Transacao t : transacaoRepository.listarTodas()) {
            if (t instanceof Aluguel && t.getVeiculo().equals(veiculoAlugado) && !t.isConcluida()) {
                aluguelEmQuestao = (Aluguel) t;
                break;
            }
        }

        if (aluguelEmQuestao == null) {
            throw new VeiculoNaoEncontradoException("Não foi possível encontrar uma transação de aluguel ativa para o veículo " + modelo);
        }
    
        // 3. Calcule a multa por atraso
        double multaPorAtraso = aluguelEmQuestao.calcularMulta(dataDevolucaoReal);
    
        // 4. (Opcional) Adicione um valor de dano
        double valorDano = 0.0;
        // Para simplificar, vamos pedir o valor de dano ao usuário no menu
        // ... no menu, você pode pedir para o gerente inserir um valor para danos
    
        double valorTotalDevolucao = multaPorAtraso + valorDano;
    
        aluguelEmQuestao.marcarComoConcluida(); // Adicionar um novo método marcarComoConcluida na classe Transacao
    
        // 5. Atualize o status do veículo
        veiculoAlugado.setDisponivelParaAluguel(true);
        veiculoAlugado.setDisponivelApos(null);
    
        System.out.println("\n--- Devolução do Veículo ---");
        System.out.println("Veículo " + veiculoAlugado.getModelo() + " devolvido com sucesso.");
        System.out.println("Multa por atraso: R$" + String.format("%.2f", multaPorAtraso));
        if (valorDano > 0) {
            System.out.println("Valor de danos: R$" + String.format("%.2f", valorDano));
        }
        System.out.println("Valor total a ser pago na devolução: R$" + String.format("%.2f", valorTotalDevolucao));
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

    public void adicionarVeiculo(Veiculo veiculo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'adicionarVeiculo'");
    }
}