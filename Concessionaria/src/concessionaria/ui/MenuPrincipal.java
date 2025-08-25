package concessionaria.ui;

// import concessionaria.dados.repositorios.VeiculoRepository;
import concessionaria.fachada.Concessionaria;
import concessionaria.main.Terminal;
import concessionaria.negocio.entidades.Cliente;
import concessionaria.negocio.entidades.Veiculo;
import concessionaria.negocio.excessoes.DataDevolucaoInvalidaException;
import concessionaria.negocio.excessoes.ParcelasInvalidasException;
import concessionaria.negocio.excessoes.VeiculoNaoEncontradoException;
import concessionaria.negocio.excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import concessionaria.negocio.excessoes.cliente.CPFDeveSerUnicoException;
import concessionaria.negocio.excessoes.cliente.ClienteNaoEncontradoException;
import concessionaria.negocio.excessoes.cliente.NomeDoClienteContemNumerosException;
import concessionaria.negocio.transacoes.Transacao;
import concessionaria.fachada.FachadaGerente;
import concessionaria.fachada.FachadaVendedor;

import java.time.LocalDate;
import java.util.List;

public class MenuPrincipal {

    private Concessionaria concessionaria;
    private FachadaGerente gerente;
    private FachadaVendedor vendedor;
    

    public MenuPrincipal(Concessionaria concessionaria, FachadaGerente gerente, FachadaVendedor vendedor) {
        this.concessionaria = concessionaria;
        this.gerente = gerente;
        this.vendedor = vendedor;
    }

    public void iniciar() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Sistema da Concessionária ---");
            System.out.println("1. Ações do Vendedor");
            System.out.println("2. Ações do Gerente");
            System.out.println("0. Sair");

            opcao = Terminal.lerInt("Escolha o seu perfil: ");

            try {
                switch (opcao) {
                    case 1:
                        menuVendedor();
                        break;
                    case 2:
                        menuGerente();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        }
    }

    private void menuVendedor() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Menu do Vendedor ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Consultar Veículos Disponíveis");
            System.out.println("3. Registrar Venda");
            System.out.println("4. Registrar Aluguel");
            System.out.println("5. Recomendar Veículo");
            System.out.println("6. Devolver Veículo");
            System.out.println("0. Voltar");

            opcao = Terminal.lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> {
                    String nome = Terminal.lerString("Nome do cliente: ");
                    String cpf = Terminal.lerString("CPF do cliente: ");
                    LocalDate dataNascimento = Terminal.lerData("Data de nascimento do cliente ");
                    try {
                        vendedor.cadastrarCliente(concessionaria, new Cliente(nome, cpf, dataNascimento));
                    } catch (NomeDoClienteContemNumerosException | CPFClienteDeveConterOnzeNumeros | CPFDeveSerUnicoException e) {
                        System.err.println("ERRO: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("\n--- Veículos Disponíveis ---");
                    vendedor.consultarModelosDisponiveis(concessionaria).forEach(System.out::println);
                }
                case 3 -> {
                    

                    try {
                        String cpfClienteVenda = Terminal.lerString("CPF do cliente para venda: ");
                        Cliente clienteVenda = concessionaria.buscarCliente(cpfClienteVenda);

                        String modeloVenda = Terminal.lerString("Modelo do veículo para venda: ");
                        int anoVenda = Terminal.lerInt("Ano do veículo: ");

                        Veiculo veiculo = concessionaria.buscarVeiculo(modeloVenda, anoVenda);

                        System.out.println("Veículo: " + veiculo.getModelo() + " " + veiculo.getAno() + "| Valor: " + veiculo.getPreco());

                        System.out.printf("\n---Forma de pagamento---\n");
                        System.out.printf("1. Dinheiro\n");
                        System.out.printf("2. Cartão de Débito\n");
                        System.out.printf("3. Cartão de Crédito\n");
                        System.out.printf("4. PIX\n");
                        System.out.printf("5. Boleto\n");

                        int opcaoPagamento = Terminal.lerInt("Escolha o ID da forma de pagamento (1-5): ");

                        String metodoPagamentoVenda = "";
                        Integer parcelas = null;
                        double valorParcelas = 0.0;
                        boolean isCredito = false;

                        switch (opcaoPagamento) {
                            case 1 -> metodoPagamentoVenda = "DINHEIRO";
                            case 2 -> metodoPagamentoVenda = "DEBITO";
                            case 3 -> {
                                metodoPagamentoVenda = "CREDITO";
                                isCredito=true;
                                int p = Terminal.lerInt("Em quantas vezes? (1 a 24): ");
                                if (p < 1 || p > 24) {
                                    throw new ParcelasInvalidasException(
                                            "Número de parcelas inválido: " + p + " (permitido: 1 a 24)"
                                    );
                                }
                                parcelas = p;
                                valorParcelas = veiculo.getPreco()/p;
                            }
                            case 4 -> metodoPagamentoVenda = "PIX";
                            case 5 -> metodoPagamentoVenda = "BOLETO";
                            default -> {
                                System.out.println("Opção inválida.");
                                break;
                            }
                        }

                        if ("CREDITO".equals(metodoPagamentoVenda) && parcelas != null) {
                            metodoPagamentoVenda = metodoPagamentoVenda + " " + parcelas + "x";
                        }

                        vendedor.registrarVenda(
                                concessionaria,
                                clienteVenda,
                                veiculo,
                                metodoPagamentoVenda
                        );

                        if (isCredito && parcelas != null) {
                            System.out.printf("%d parcelas de R$ %.2f%n", parcelas, valorParcelas);
                        }

                    } catch (ParcelasInvalidasException | ClienteNaoEncontradoException | VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        String cpfClienteAluguel = Terminal.lerString("CPF do cliente para aluguel: ");
                        Cliente clienteAluguel = concessionaria.buscarCliente(cpfClienteAluguel);

                        String modeloAluguel = Terminal.lerString("Modelo do veículo para aluguel: ");
                        int anoAluguel = Terminal.lerInt("Ano do veículo: ");
                        int diasAluguel = Terminal.lerInt("Dias de aluguel: ");
                        String metodoPagamentoAluguel = Terminal.lerString("Método de pagamento: ");

                        vendedor.registrarAluguel(concessionaria, clienteAluguel, new Veiculo(modeloAluguel, "", anoAluguel, 0.0), metodoPagamentoAluguel, diasAluguel);
                    } catch (ClienteNaoEncontradoException | VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 5 -> {
                    try {
                        String cpfClienteRecomendacao = Terminal.lerString("CPF do cliente para recomendação: ");
                        Cliente clienteRecomendacao = concessionaria.buscarCliente(cpfClienteRecomendacao);
                        List<Veiculo> recomendados = vendedor.recomendarVeiculos(concessionaria, clienteRecomendacao);
                        System.out.println("Veículos recomendados:");
                        if (recomendados.isEmpty()) {
                            System.out.println("Nenhum veículo recomendado para este perfil.");
                        } else {
                            recomendados.forEach(System.out::println);
                        }
                    } catch (ClienteNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 6 -> {
                    try {
                        String modeloDevolver = Terminal.lerString("Modelo do veículo para devolver: ");
                        int anoDevolver = Terminal.lerInt("Ano do veículo: ");
                        LocalDate dataDevolucaoReal = Terminal.lerData("Data de devolução (YYYY-MM-DD): ");

                        boolean houveDano = Terminal.lerSimNao("Ocorreram danos ao veículo?");
                        double valorDano = 0.0;
                        if (houveDano) {
                            valorDano = 2500.00;
                        }

                        vendedor.devolverVeiculo(concessionaria, modeloDevolver, anoDevolver, dataDevolucaoReal, valorDano);
                    } catch (VeiculoNaoEncontradoException | DataDevolucaoInvalidaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                }
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    private void menuGerente() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Menu do Gerente ---");
            System.out.println("1. Adicionar Veículo");
            System.out.println("2. Remover Veículo");
            System.out.println("3. Editar Dados do Veículo");
            System.out.println("4. Consultar Cliente");
            System.out.println("5. Editar Dados do Cliente");
            System.out.println("6. Remover Cliente");
            System.out.println("7. Gerar Relatório de Vendas e Aluguéis");
            System.out.println("\n--- Ações de Vendas ---");
            System.out.println("8. Cadastrar Cliente");
            System.out.println("9. Consultar Veículos Disponíveis");
            System.out.println("10. Registrar Venda");
            System.out.println("11. Registrar Aluguel");
            System.out.println("12. Recomendar Veículo");
            System.out.println("13. Devolver Veículo");
            System.out.println("0. Voltar");

            opcao = Terminal.lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    String modeloAdd = Terminal.lerString("Modelo do veículo: ");
                    String marcaAdd = Terminal.lerString("Marca do veículo: ");
                    int anoAdd = Terminal.lerInt("Ano do veículo: ");
                    double precoAdd = Terminal.lerDouble("Preço do veículo: ");
                    gerente.adicionarVeiculo(concessionaria, modeloAdd, marcaAdd, anoAdd, precoAdd);
                    break;
                case 2:
                    try {
                        String modeloRemover = Terminal.lerString("Modelo do veículo para remover: ");
                        gerente.removerVeiculo(concessionaria, modeloRemover);
                    } catch (VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        String modeloEditar = Terminal.lerString("Modelo do veículo para editar: ");
                        double novoPreco = Terminal.lerDouble("Novo preço: ");
                        gerente.editarDadosVeiculo(concessionaria, modeloEditar, novoPreco);
                    } catch (VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        String cpfConsultar = Terminal.lerString("CPF do cliente para consultar: ");
                        Cliente clienteConsultado = gerente.consultarCliente(concessionaria, cpfConsultar);
                        System.out.println("Dados do cliente:\n" + clienteConsultado);
                    } catch (ClienteNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        String cpfEditar = Terminal.lerString("CPF do cliente para editar: ");
                        String novoNome = Terminal.lerString("Novo nome: ");
                        LocalDate novaDataNascimento = Terminal.lerData("Nova data de nascimento: ");
                        gerente.editarDadosCliente(concessionaria, cpfEditar, novoNome, novaDataNascimento);
                    } catch (ClienteNaoEncontradoException | NomeDoClienteContemNumerosException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        String cpfRemover = Terminal.lerString("CPF do cliente para remover: ");
                        gerente.removerCliente(concessionaria, cpfRemover);
                    } catch (ClienteNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 7:
                    LocalDate dataInicio = Terminal.lerData("Data de início");
                    LocalDate dataFim = Terminal.lerData("Data de fim");
                    List<Transacao> relatorio = gerente.gerarRelatorio(concessionaria, dataInicio, dataFim);
                    relatorio.forEach(System.out::println);
                    if (relatorio.isEmpty()) {
                        System.out.println("Nenhuma transação encontrada no período.");
                    }
                    break;
                case 8:
                    String nome = Terminal.lerString("Nome do cliente: ");
                    String cpf = Terminal.lerString("CPF do cliente: ");
                    LocalDate dataNascimento = Terminal.lerData("Data de nascimento do cliente: ");
                    try {
                        gerente.cadastrarCliente(concessionaria, new Cliente(nome, cpf, dataNascimento));
                    } catch (NomeDoClienteContemNumerosException | CPFClienteDeveConterOnzeNumeros | CPFDeveSerUnicoException e) {
                        System.err.println("ERRO: " + e.getMessage());
                    }
                    break;
                case 9:
                    System.out.println("\n--- Veículos Disponíveis ---");
                    gerente.consultarModelosDisponiveis(concessionaria).forEach(System.out::println);
                    break;
                case 10:

                    try {
                        String cpfClienteVenda = Terminal.lerString("CPF do cliente para venda: ");
                        Cliente clienteVenda = concessionaria.buscarCliente(cpfClienteVenda);

                        String modeloVenda = Terminal.lerString("Modelo do veículo para venda: ");
                        int anoVenda = Terminal.lerInt("Ano do veículo: ");

                        Veiculo veiculo = concessionaria.buscarVeiculo(modeloVenda, anoVenda);
                        System.out.println("Veículo: " + veiculo.getModelo() + " " + veiculo.getAno() + "| Valor: " + veiculo.getPreco());

                        System.out.printf("\n---Forma de pagamento---\n");
                        System.out.printf("1. Dinheiro\n");
                        System.out.printf("2. Cartão de Débito\n");
                        System.out.printf("3. Cartão de Crédito\n");
                        System.out.printf("4. PIX\n");
                        System.out.printf("5. Boleto\n");

                        int opcaoPagamento = Terminal.lerInt("Escolha o ID da forma de pagamento (1-5): ");

                        String metodoPagamentoVenda = "";
                        Integer parcelas = null;
                        double valorParcelas = 0.0;
                        boolean isCredito = false; 

                        switch (opcaoPagamento) {
                            case 1 -> metodoPagamentoVenda = "DINHEIRO";
                            case 2 -> metodoPagamentoVenda = "DEBITO";
                            case 3 -> {
                                metodoPagamentoVenda = "CREDITO";
                                isCredito = true;
                                int p = Terminal.lerInt("Em quantas vezes? (1 a 24): ");
                                if (p < 1 || p > 24) {
                                    throw new ParcelasInvalidasException("Número de parcelas inválido: " + p + " (permitido: 1 a 24)");
                                }
                                parcelas = p;
                                valorParcelas = veiculo.getPreco()/parcelas;
                            }
                            case 4 -> metodoPagamentoVenda = "PIX";
                            case 5 -> metodoPagamentoVenda = "BOLETO";
                            default -> {
                                System.out.println("Opção inválida.");
                                break;
                            }
                        }

                        if ("CREDITO".equals(metodoPagamentoVenda) && parcelas != null) {
                            metodoPagamentoVenda = metodoPagamentoVenda + " " + parcelas + "x";
                        }

                        gerente.registrarVenda(concessionaria, clienteVenda, new Veiculo(modeloVenda, "", anoVenda, 0.0), metodoPagamentoVenda);

                        if (isCredito && parcelas != null) {
                            System.out.printf("Pagamento em %d parcelas de R$ %.2f%n", parcelas, valorParcelas);
                        }

                    } catch (ParcelasInvalidasException | ClienteNaoEncontradoException | VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 11:
                    try {
                        String cpfClienteAluguel = Terminal.lerString("CPF do cliente para aluguel: ");
                        Cliente clienteAluguel = concessionaria.buscarCliente(cpfClienteAluguel);

                        String modeloAluguel = Terminal.lerString("Modelo do veículo para aluguel: ");
                        int anoAluguel = Terminal.lerInt("Ano do veículo: ");
                        int diasAluguel = Terminal.lerInt("Dias de aluguel: ");
                        String metodoPagamentoAluguel = Terminal.lerString("Método de pagamento: ");

                        gerente.registrarAluguel(concessionaria, clienteAluguel, new Veiculo(modeloAluguel, "", anoAluguel, 0.0), metodoPagamentoAluguel, diasAluguel);
                    } catch (ClienteNaoEncontradoException | VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 12:
                    try {
                        String cpfClienteRecomendacao = Terminal.lerString("CPF do cliente para recomendação: ");
                        Cliente clienteRecomendacao = concessionaria.buscarCliente(cpfClienteRecomendacao);
                        List<Veiculo> recomendados = gerente.recomendarVeiculos(concessionaria, clienteRecomendacao);
                        System.out.println("Veículos recomendados:");
                        if (recomendados.isEmpty()) {
                            System.out.println("Nenhum veículo recomendado para este perfil.");
                        } else {
                            recomendados.forEach(System.out::println);
                        }
                    } catch (ClienteNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 13:
                    try {
                        String modeloDevolver = Terminal.lerString("Modelo do veículo para devolver: ");
                        int anoDevolver = Terminal.lerInt("Ano do veículo: ");
                        LocalDate dataDevolucaoReal = Terminal.lerData("Data de devolução (YYYY-MM-DD): ");

                        boolean houveDano = Terminal.lerSimNao("Ocorreram danos ao veículo?");
                        double valorDano = 0.0;
                        if (houveDano) {
                            valorDano = 2500.00;
                        }

                        gerente.devolverVeiculo(concessionaria, modeloDevolver, anoDevolver, dataDevolucaoReal, valorDano);
                    } catch (VeiculoNaoEncontradoException | DataDevolucaoInvalidaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}