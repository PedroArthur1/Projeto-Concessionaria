package sistema.ui;

import java.time.LocalDate;
import java.util.List;

import sistema.fachada.Concessionaria;
import sistema.fachada.FachadaGerente;
import sistema.fachada.FachadaVendedor;
import sistema.main.Terminal;
import sistema.negocio.entidades.Cliente;
import sistema.negocio.entidades.Veiculo;
import sistema.negocio.excessoes.DataDevolucaoInvalidaException;
import sistema.negocio.excessoes.ParcelasInvalidasException;
import sistema.negocio.excessoes.PlacaDeveSerUnicaException;
import sistema.negocio.excessoes.QuilometragemMenorQueOriginalException;
import sistema.negocio.excessoes.VeiculoNaoEncontradoException;
import sistema.negocio.excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import sistema.negocio.excessoes.cliente.CPFDeveSerUnicoException;
import sistema.negocio.excessoes.cliente.ClienteNaoEncontradoException;
import sistema.negocio.excessoes.cliente.NomeDoClienteContemNumerosException;
import sistema.negocio.transacoes.Aluguel;
import sistema.negocio.transacoes.Transacao;

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

                        String placaVenda = Terminal.lerString("Placa do veículo para venda: ");
                        Veiculo veiculo = concessionaria.buscarVeiculo(placaVenda);

                        System.out.println("Veículo: " + veiculo.getModelo() + " " + veiculo.getAno() + "| Valor: " + veiculo.getPreco());

                        EscolhaPagamento pg = PagamentoUI.escolherPagamento(veiculo.getPreco());

                        vendedor.registrarVenda(concessionaria, clienteVenda, veiculo, pg.getMetodo());

                        if (pg.isCredito()) {
                            System.out.printf("Pagamento em %d parcelas de R$ %.2f%n", pg.getParcelas(), pg.getValorParcela());
                        }

                    } catch (ParcelasInvalidasException | ClienteNaoEncontradoException | VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }

                case 4 -> {
                    try {
                        
                        String cpfClienteAluguel = Terminal.lerString("CPF do cliente para aluguel: ");
                        Cliente clienteAluguel = concessionaria.buscarCliente(cpfClienteAluguel);

                        String placaAluguel = Terminal.lerString("Placa do veículo para aluguel: ");

                        int diasAluguel = Terminal.lerInt("Dias de aluguel: ");

                        double totalAluguel = Aluguel.simularTotal(diasAluguel);

                        System.out.printf("Total do aluguel: R$ %.2f%n", totalAluguel);

                        EscolhaPagamento pg = PagamentoUI.escolherPagamento(totalAluguel);

                        gerente.registrarAluguel(concessionaria, clienteAluguel, placaAluguel, diasAluguel, pg.getMetodo());

                        if (pg.isCredito()) {
                            System.out.printf("Pagamento em %d parcelas de R$ %.2f%n", pg.getParcelas(), pg.getValorParcela());
                        }

                    } catch (ParcelasInvalidasException | ClienteNaoEncontradoException | VeiculoNaoEncontradoException e) {
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
                        String placaDevolver = Terminal.lerString("Placa do veículo para devolver: ");
                        LocalDate dataDevolucaoReal = Terminal.lerData("Data de devolução (AAAA-MM-DD): ");
                        Veiculo veiculo = concessionaria.buscarVeiculo(placaDevolver);

                        boolean houveDano = Terminal.lerSimNao("Ocorreram danos ao veículo?");
                        double valorDano = houveDano ? 2500.00 : 0.0;

                        double novaQuilometragem = Terminal.lerDouble("Quilometragem atual: ");
                        if (novaQuilometragem <= veiculo.getQuilometragem()) {
                            throw new QuilometragemMenorQueOriginalException(
                                "Quilometragem atual menor ou igual à quilometragem anterior"
                            );
                        }

                        Cliente clienteDoAluguel = concessionaria.buscarClienteDoAluguelAtivo(placaDevolver);
                        double multaAtraso = concessionaria.calcularMultaAtraso(placaDevolver, dataDevolucaoReal);
                        double totalMulta = multaAtraso + valorDano;

                        if (totalMulta > 0) {
                            System.out.println("Multa por atraso: R$ " + String.format("%.2f", multaAtraso));
                            if (valorDano > 0) {
                               System.out.println("Valor de danos: R$ " + String.format("%.2f", valorDano));
                            }
                            System.out.println("Total de multa/danos: R$ " + String.format("%.2f", totalMulta));

                            EscolhaPagamento pg = PagamentoUI.escolherPagamento(totalMulta);

                            String motivo = (multaAtraso > 0 && valorDano > 0) ? "ATRASO + DANO"
                                        : (multaAtraso > 0) ? "ATRASO" : "DANO";

                            gerente.registrarMulta(concessionaria, clienteDoAluguel, veiculo, pg.getMetodo(), totalMulta, motivo);

                            if (pg.isCredito()) {
                                System.out.printf("Multa paga em %d parcelas de R$ %.2f%n",
                                    pg.getParcelas(), pg.getValorParcela());
                            }
                        } else {
                            System.out.println("Devolução sem multas/danos.");
                        }

                        vendedor.devolverVeiculo(
                            concessionaria, placaDevolver, dataDevolucaoReal, valorDano, novaQuilometragem
                        );

                    } catch (QuilometragemMenorQueOriginalException
                        | VeiculoNaoEncontradoException
                        | DataDevolucaoInvalidaException
                        | ParcelasInvalidasException
                        | ClienteNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
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
                String placaAdd = Terminal.lerString("Placa do veículo: ");
                String modeloAdd = Terminal.lerString("Modelo do veículo: ");
                String marcaAdd = Terminal.lerString("Marca do veículo: ");
                int anoAdd = Terminal.lerInt("Ano do veículo: ");
                double precoAdd = Terminal.lerDouble("Preço do veículo: ");
                double quilometragemAdd = Terminal.lerDouble("Quilometragem do veículo: ");
                
                try {
                    gerente.adicionarVeiculo(concessionaria, placaAdd, modeloAdd, marcaAdd, anoAdd, precoAdd, quilometragemAdd);
                } catch (PlacaDeveSerUnicaException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
                break;
                case 2:
                    try {
                        String placaRemover = Terminal.lerString("Placa do veículo para remover: ");
                        gerente.removerVeiculo(concessionaria, placaRemover);
                    } catch (VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                    case 3:
                        try {
                            String placaEditar = Terminal.lerString("Placa do veículo para editar: ");
                            double novoPreco = Terminal.lerDouble("Novo preço: ");

                            System.out.println("Escolha a nova disponibilidade:");
                            System.out.println("1 - DISPONÍVEL");
                            System.out.println("2 - INDISPONÍVEL");
                            System.out.println("3 - MANUTENÇÃO");

                            int opcaoDisp = Terminal.lerInt("Digite a opção: ");
                            String novaDisponibilidade = switch (opcaoDisp) {
                                case 1 -> "Disponivel";
                                case 2 -> "Indisponivel";
                                case 3 -> "Manuntecao";
                                default -> "Disponivel";
                            };

                            gerente.editarDadosVeiculo(concessionaria, placaEditar, novoPreco, novaDisponibilidade);

                            System.out.println("Veículo atualizado com sucesso!");
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

                        String placaVenda = Terminal.lerString("Placa do veículo para venda: ");
                        Veiculo veiculo = concessionaria.buscarVeiculo(placaVenda);
                        System.out.println("Veículo: " + veiculo.getModelo() + " " + veiculo.getAno() + " | Valor: " + veiculo.getPreco());

                        EscolhaPagamento pg = PagamentoUI.escolherPagamento(veiculo.getPreco());

                        gerente.registrarVenda(concessionaria, clienteVenda, veiculo, pg.getMetodo());

                        if (pg.isCredito()) {
                            System.out.printf("Pagamento em %d parcelas de R$ %.2f%n", pg.getParcelas(), pg.getValorParcela());
                        }

                    } catch (ParcelasInvalidasException | ClienteNaoEncontradoException | VeiculoNaoEncontradoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 11:
                    try {
                        String cpfClienteAluguel = Terminal.lerString("CPF do cliente para aluguel: ");
                        Cliente clienteAluguel = concessionaria.buscarCliente(cpfClienteAluguel);
                        String placaAluguel = Terminal.lerString("Placa do veículo para aluguel: ");
                        int diasAluguel = Terminal.lerInt("Dias de aluguel: ");
                        double totalAluguel = Aluguel.simularTotal(diasAluguel);
                        System.out.printf("Total do aluguel: R$ %.2f%n", totalAluguel);

                        EscolhaPagamento pg = PagamentoUI.escolherPagamento(totalAluguel);

                        gerente.registrarAluguel(concessionaria, clienteAluguel, placaAluguel, diasAluguel, pg.getMetodo());

                        if (pg.isCredito()) {
                            System.out.printf("Pagamento em %d parcelas de R$ %.2f%n", pg.getParcelas(), pg.getValorParcela());
                        }

                    } catch (ParcelasInvalidasException | ClienteNaoEncontradoException | VeiculoNaoEncontradoException e) {
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
                        String placaDevolver = Terminal.lerString("Placa do veículo para devolver: ");
                        LocalDate dataDevolucaoReal = Terminal.lerData("Data de devolução (AAAA-MM-DD): ");
                        Veiculo veiculo = concessionaria.buscarVeiculo(placaDevolver);

                        boolean houveDano = Terminal.lerSimNao("Ocorreram danos ao veículo?");
                        double valorDano = houveDano ? 2500.00 : 0.0;

                        double novaQuilometragem = Terminal.lerDouble("Quilometragem atual: ");
                        if (novaQuilometragem <= veiculo.getQuilometragem()) {
                            throw new QuilometragemMenorQueOriginalException(
                                "Quilometragem atual menor ou igual à quilometragem anterior"
                            );
                        }


                        Cliente clienteDoAluguel = concessionaria.buscarClienteDoAluguelAtivo(placaDevolver);
                        double multaAtraso = concessionaria.calcularMultaAtraso(placaDevolver, dataDevolucaoReal);
                        double totalMulta = multaAtraso + valorDano;

                        if (totalMulta > 0) {
                            System.out.println("Multa por atraso: R$ " + String.format("%.2f", multaAtraso));
                            if (valorDano > 0) {
                               System.out.println("Valor de danos: R$ " + String.format("%.2f", valorDano));
                            }
                            System.out.println("Total de multa/danos: R$ " + String.format("%.2f", totalMulta));

                            EscolhaPagamento pg = PagamentoUI.escolherPagamento(totalMulta);

                            String motivo = (multaAtraso > 0 && valorDano > 0) ? "ATRASO + DANO"
                                        : (multaAtraso > 0) ? "ATRASO" : "DANO";

                            gerente.registrarMulta(concessionaria, clienteDoAluguel, veiculo, pg.getMetodo(), totalMulta, motivo);

                            if (pg.isCredito()) {
                                System.out.printf("Multa paga em %d parcelas de R$ %.2f%n",
                                    pg.getParcelas(), pg.getValorParcela());
                            }
                        } else {
                            System.out.println("Devolução sem multas/danos.");
                        }

                        vendedor.devolverVeiculo(
                            concessionaria, placaDevolver, dataDevolucaoReal, valorDano, novaQuilometragem
                        );

                    } catch (QuilometragemMenorQueOriginalException
                        | VeiculoNaoEncontradoException
                        | DataDevolucaoInvalidaException
                        | ParcelasInvalidasException
                        | ClienteNaoEncontradoException e) {
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