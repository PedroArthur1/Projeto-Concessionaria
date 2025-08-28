package sistema.ui;

import sistema.main.Terminal;
import sistema.negocio.excessoes.ParcelasInvalidasException;

public class PagamentoUI {

    // preço é necessário para calcular valor da parcela (se crédito)
    public static EscolhaPagamento escolherPagamento(double preco) throws ParcelasInvalidasException {
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
                double valorParcela = preco / p;
                String metodo = "CREDITO " + p + "x";
                return new EscolhaPagamento(metodo, p, valorParcela);
            }
            case 4: return new EscolhaPagamento("PIX",     null, null);
            case 5: return new EscolhaPagamento("BOLETO",  null, null);
            default:
                System.out.println("Opção inválida.");
                // você pode repetir a pergunta ou lançar uma exceção; aqui vou repetir:
                return escolherPagamento(preco);
        }
    }
}
