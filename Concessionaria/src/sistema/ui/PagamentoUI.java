package sistema.ui;

import sistema.main.Terminal;
import sistema.negocio.excessoes.ParcelasInvalidasException;

/**
 * Classe utilitária responsável por interagir com o usuário para a escolha da
 * forma de pagamento.
 * 
 * Esta classe apresenta as opções de pagamento disponíveis e coleta a
 * entrada do usuário, validando-a e retornando um objeto {@code EscolhaPagamento}.
 *
 * @author Emanuel Bezerra e Pedro Arthur
 * @description Classe que gerencia a interface para a escolha de pagamento.
 */

public class PagamentoUI {

    /**
     * Exibe um menu de opções de pagamento e solicita a escolha do usuário.
     *
     * @param totalAPagar O valor total da transação a ser paga.
     * @return Um objeto {@code EscolhaPagamento} contendo os detalhes da
     * opção selecionada.
     * @throws ParcelasInvalidasException se o usuário escolher um número de
     * parcelas fora do intervalo permitido (1 a 24).
     */

    public static EscolhaPagamento escolherPagamento(double totalAPagar) throws ParcelasInvalidasException {
        System.out.printf("\n---Forma de pagamento---\n");
        System.out.printf("1. Dinheiro\n");
        System.out.printf("2. Cartão de Débito\n");
        System.out.printf("3. Cartão de Crédito\n");
        System.out.printf("4. PIX\n");
        System.out.printf("5. Boleto\n");

        int opcao = Terminal.lerInt("Escolha o ID da forma de pagamento (1-5): ");

        switch (opcao) {
            case 1: return new EscolhaPagamento("DINHEIRO", null, null);
            case 2: return new EscolhaPagamento("DEBITO",   null, null);
            case 3: {
                int p = Terminal.lerInt("Em quantas vezes? (1 a 24): ");
                if (p < 1 || p > 24) {
                    throw new ParcelasInvalidasException("Número de parcelas inválido: " + p + " (permitido: 1 a 24)");
                }
                double valorParcela = totalAPagar / p;
                String metodo = "CREDITO " + p + "x";
                return new EscolhaPagamento(metodo, p, valorParcela);
            }
            case 4: return new EscolhaPagamento("PIX",     null, null);
            case 5: return new EscolhaPagamento("BOLETO",  null, null);
            default:
                System.out.println("Opção inválida.");
                return escolherPagamento(totalAPagar);
        }
    }
}
