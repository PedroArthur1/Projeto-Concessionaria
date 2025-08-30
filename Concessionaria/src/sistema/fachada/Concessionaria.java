package sistema.fachada;
import java.time.LocalDate;
import java.util.List;

import sistema.negocio.entidades.Cliente;
import sistema.negocio.entidades.Veiculo;
import sistema.negocio.excessoes.DataDevolucaoInvalidaException;
import sistema.negocio.excessoes.PlacaDeveSerUnicaException;
import sistema.negocio.excessoes.VeiculoNaoEncontradoException;
import sistema.negocio.excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import sistema.negocio.excessoes.cliente.CPFDeveSerUnicoException;
import sistema.negocio.excessoes.cliente.ClienteNaoEncontradoException;
import sistema.negocio.excessoes.cliente.NomeDoClienteContemNumerosException;
import sistema.negocio.transacoes.Aluguel;
import sistema.negocio.transacoes.Multa;
import sistema.negocio.transacoes.Transacao;
import sistema.negocio.transacoes.Venda;
import sistema.repositorios.ClienteRepository;
import sistema.repositorios.TransacaoRepository;
import sistema.repositorios.VeiculoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Concessionaria {
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final TransacaoRepository transacaoRepository;

    public Concessionaria() {
        this.clienteRepository = new ClienteRepository();
        this.veiculoRepository = new VeiculoRepository();
        this.transacaoRepository = new TransacaoRepository();
    }

public void carregarClientes(String arquivo) {
    try {
        List<String> linhas = Files.readAllLines(Paths.get(arquivo));
        for (String linha : linhas) {
            String[] dados = linha.split(",");
            String nome = dados[0].trim();
            String cpf = dados[1].trim();
            LocalDate dataNascimento = LocalDate.parse(dados[2].trim());
            adicionarCliente(new Cliente(nome, cpf, dataNascimento));
        }
        System.out.println("Clientes carregados do arquivo " + arquivo);
    } catch (IOException e) {
        System.err.println("Erro ao ler o arquivo de clientes: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("Erro ao processar dados do cliente: " + e.getMessage());
    }
}

public void carregarVeiculos(String arquivo) {
    try {
        List<String> linhas = Files.readAllLines(Paths.get(arquivo));
        for (String linha : linhas) {
            String[] dados = linha.split(",");
            String placa = dados[0].trim();
            String modelo = dados[1].trim();
            String marca = dados[2].trim();
            int ano = Integer.parseInt(dados[3].trim());
            double preco = Double.parseDouble(dados[4].trim());
            double quilometragem = Double.parseDouble(dados[5].trim());
            adicionarVeiculo(placa, modelo, marca, ano, preco, quilometragem);
        }
        System.out.println("Veículos carregados do arquivo " + arquivo);
    } catch (IOException e) {
        System.err.println("Erro ao ler o arquivo de veículos: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("Erro ao processar dados do veículo: " + e.getMessage());
    }
}

    public void adicionarVeiculo(String placa, String modelo, String marca, int ano, double preco, double quilometragem) throws PlacaDeveSerUnicaException {
        Veiculo novoVeiculo = new Veiculo(placa, modelo, marca, ano, preco, quilometragem);
        veiculoRepository.adicionar(novoVeiculo);
        // System.out.println("Veículo " + novoVeiculo.getModelo() + " adicionado ao estoque.");
    }

    public Veiculo buscarVeiculo(String placa) throws VeiculoNaoEncontradoException {
        return veiculoRepository.buscarPorPlaca(placa);
    }

    public void removerVeiculo(String placa) throws VeiculoNaoEncontradoException {
        veiculoRepository.remover(placa);
        System.out.println("Veículo com placa " + placa + " removido do estoque.");
    }

    public void editarDadosVeiculo(String placa, double novoPreco, String novaDisponibilidade) throws VeiculoNaoEncontradoException {
        Veiculo veiculoParaEditar = veiculoRepository.buscarPorPlaca(placa);
        veiculoParaEditar.setPreco(novoPreco);
        veiculoParaEditar.setStatusDisponibilidade(novaDisponibilidade);
        System.out.println("Dados do veículo " + veiculoParaEditar.getModelo() + " editados. Novo preço: R$" + String.format("%.2f", novoPreco) + "Disponibilidade: " + novaDisponibilidade);
    }

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
    
    // TransacaoRepository
    public List<Transacao> gerarRelatorio(LocalDate dataInicio, LocalDate dataFim) {
        List<Transacao> transacoes = transacaoRepository.listarPorPeriodo(dataInicio, dataFim);
        
        double totalTransacoes = 0;
        
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
            throw new VeiculoNaoEncontradoException("Veículo " + veiculoEmEstoque.getModelo() + " está temporariamente indisponível.");
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

    public Cliente buscarClienteDoAluguelAtivo(String placa)
            throws VeiculoNaoEncontradoException, ClienteNaoEncontradoException {

        Veiculo v = veiculoRepository.buscarPorPlaca(placa);

        for (Transacao t : transacaoRepository.listarTodas()) {
            if (t instanceof Aluguel a && !a.isConcluida() && a.getVeiculo().equals(v)) {
                return a.getCliente();
            }
        }
        throw new ClienteNaoEncontradoException("Não há aluguel ativo para a placa: " + placa);
    }

    // Calcula a multa de ATRASO do aluguel ativo dessa placa (sem danos)
    public double calcularMultaAtraso(String placa, LocalDate dataDevolucaoReal)
            throws VeiculoNaoEncontradoException {

        Veiculo v = veiculoRepository.buscarPorPlaca(placa);

        for (Transacao t : transacaoRepository.listarTodas()) {
            if (t instanceof Aluguel a && !a.isConcluida() && a.getVeiculo().equals(v)) {
                return a.calcularMulta(dataDevolucaoReal);
            }
        }
        return 0.0;
    }

    public void registrarMulta(Cliente cliente,
                            Veiculo veiculo,
                            String metodoPagamento,
                            double valor,
                            String motivo) {
        Multa m = new Multa(cliente, veiculo, metodoPagamento, valor, motivo);
        transacaoRepository.adicionar(m);
    }

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
        System.out.println("Quilometragem Atual: " + veiculoAlugado.getQuilometragem() + " Km.");
    }
    
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