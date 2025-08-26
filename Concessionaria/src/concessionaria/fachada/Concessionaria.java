package concessionaria.fachada;
import concessionaria.dados.repositorios.ClienteRepository;
import concessionaria.dados.repositorios.TransacaoRepository;
import concessionaria.dados.repositorios.VeiculoRepository;
import concessionaria.negocio.entidades.Cliente;
import concessionaria.negocio.entidades.Veiculo;
import concessionaria.negocio.excessoes.DataDevolucaoInvalidaException;
import concessionaria.negocio.excessoes.PlacaDeveSerUnicaException;
import concessionaria.negocio.excessoes.VeiculoNaoEncontradoException;
import concessionaria.negocio.excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import concessionaria.negocio.excessoes.cliente.CPFDeveSerUnicoException;
import concessionaria.negocio.excessoes.cliente.ClienteNaoEncontradoException;
import concessionaria.negocio.excessoes.cliente.NomeDoClienteContemNumerosException;
import concessionaria.negocio.transacoes.Aluguel;
import concessionaria.negocio.transacoes.Transacao;
import concessionaria.negocio.transacoes.Venda;
import java.time.LocalDate;
import java.util.List;

public class Concessionaria {
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final TransacaoRepository transacaoRepository;

    public Concessionaria() {
        this.clienteRepository = new ClienteRepository();
        this.veiculoRepository = new VeiculoRepository();
        this.transacaoRepository = new TransacaoRepository();
    }
    
    // Adicionar veículo (com placa e quilometragem)
    public void adicionarVeiculo(String placa, String modelo, String marca, int ano, double preco, double quilometragem) throws PlacaDeveSerUnicaException {
        Veiculo novoVeiculo = new Veiculo(placa, modelo, marca, ano, preco, quilometragem);
        veiculoRepository.adicionar(novoVeiculo);
        System.out.println("Veículo " + novoVeiculo.getModelo() + " adicionado ao estoque.");
    }

    // Método para buscar veículo por placa
    public Veiculo buscarVeiculo(String placa) throws VeiculoNaoEncontradoException {
        return veiculoRepository.buscarPorPlaca(placa);
    }

    // Remover veículo por placa
    public void removerVeiculo(String placa) throws VeiculoNaoEncontradoException {
        veiculoRepository.remover(placa);
        System.out.println("Veículo com placa " + placa + " removido do estoque.");
    }

    public void editarDadosVeiculo(String placa, double novoPreco) throws VeiculoNaoEncontradoException {
        Veiculo veiculoParaEditar = veiculoRepository.buscarPorPlaca(placa);
        veiculoParaEditar.setPreco(novoPreco);
        System.out.println("Dados do veículo " + veiculoParaEditar.getModelo() + " editados. Novo preço: R$" + String.format("%.2f", novoPreco));
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
        List<Transacao> transacoes = transacaoRepository.listarPorPeriodo(dataInicio, dataFim);
        
        // Crie uma variável para somar o total
        double totalTransacoes = 0;
        
        // Itere sobre as transações e some os valores
        for(Transacao t : transacoes) {
            totalTransacoes += t.getValorTransacao();
        }
        
        // Exiba o valor total
        System.out.println("-------------------------------------");
        System.out.println("Valor total das transações no período: R$" + String.format("%.2f", totalTransacoes));
        System.out.println("-------------------------------------");
        
        return transacoes;
    }

    public void registrarVenda(Cliente cliente, String placa, String metodoPagamento) throws VeiculoNaoEncontradoException {
        Veiculo veiculoEmEstoque = veiculoRepository.buscarPorPlaca(placa);
        
        if (!veiculoEmEstoque.getStatusDisponibilidade().equals("Disponivel")) {
            throw new VeiculoNaoEncontradoException("Veículo " + veiculoEmEstoque.getModelo() + " não está disponível para venda pois está temporariamente alugado.");
        }

        Venda novaVenda = new Venda(cliente, veiculoEmEstoque, metodoPagamento);
        transacaoRepository.adicionar(novaVenda);
        veiculoRepository.remover(placa);
        System.out.println("Venda registrada com sucesso para " + cliente.getNome() + " do veículo " + veiculoEmEstoque.getModelo());
    }

    public void registrarAluguel(Cliente cliente, String placa, int dias, String metodoPagamento) throws VeiculoNaoEncontradoException {
        Veiculo veiculoEmEstoque = veiculoRepository.buscarPorPlaca(placa);

        if (!veiculoEmEstoque.getStatusDisponibilidade().equals("Disponivel")) {
            throw new VeiculoNaoEncontradoException("Veículo " + veiculoEmEstoque.getModelo() + " não está disponível para aluguel.");
        }
    
        Aluguel novoAluguel = new Aluguel(cliente, veiculoEmEstoque, metodoPagamento, dias);
        transacaoRepository.adicionar(novoAluguel);
        
        veiculoEmEstoque.setStatusDisponibilidade("Indisponivel");
        veiculoEmEstoque.setDisponivelApos(LocalDate.now().plusDays(dias));
    
        System.out.println("Aluguel registrado com sucesso para " + cliente.getNome() + " do veículo " + veiculoEmEstoque.getModelo() + " | Valor Total: R$" + String.format("%.2f", novoAluguel.getValorTotal()));
    }
    // Método de devolução, ajustado para placa e novos status
    public void devolverVeiculo(String placa, LocalDate dataDevolucaoReal, double valorDano, double novaQuilometragem) throws VeiculoNaoEncontradoException, DataDevolucaoInvalidaException {
    
        if (dataDevolucaoReal.isBefore(LocalDate.now())) {
            throw new DataDevolucaoInvalidaException("A data de devolução não pode ser anterior à data atual.");
        }

        Veiculo veiculoAlugado = veiculoRepository.buscarPorPlaca(placa);
        
        if (!veiculoAlugado.getStatusDisponibilidade().equals("Indisponivel")) {
            System.out.println("O veículo " + veiculoAlugado.getModelo() + " não está alugado. Nenhuma ação necessária.");
            return;
        }

        Aluguel aluguelEmQuestao = null;
        for (Transacao t : transacaoRepository.listarTodas()) {
            if (t instanceof Aluguel && t.getVeiculo().equals(veiculoAlugado) && !t.isConcluida()) {
                aluguelEmQuestao = (Aluguel) t;
                break;
            }
        }

        if (aluguelEmQuestao == null) {
            throw new VeiculoNaoEncontradoException("Não foi possível encontrar uma transação de aluguel ativa para o veículo com placa " + placa);
        }
    
        double multaPorAtraso = aluguelEmQuestao.calcularMulta(dataDevolucaoReal);
        aluguelEmQuestao.setValorDano(valorDano);
        double valorTotalDevolucao = multaPorAtraso + valorDano;
        aluguelEmQuestao.marcarComoConcluida();
        
        veiculoAlugado.setQuilometragem(novaQuilometragem);

        if (valorDano > 0) {
            veiculoAlugado.setStatusDisponibilidade("Manutencao");
        } else {
            veiculoAlugado.setStatusDisponibilidade("Disponivel");
        }
        
        veiculoAlugado.setDisponivelApos(null);
        
        System.out.println("\n--- Devolução do Veículo ---");
        System.out.println("Veículo " + veiculoAlugado.getModelo() + " devolvido com sucesso.");
        System.out.println("Multa por atraso: R$" + String.format("%.2f", multaPorAtraso));
        if (valorDano > 0) {
            System.out.println("Valor de danos: R$" + String.format("%.2f", valorDano));
        }
        System.out.println("Valor total a ser pago na devolução: R$" + String.format("%.2f", valorTotalDevolucao));
    }
    
    // Métodos para delegar a responsabilidade aos outros repositórios...
    
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