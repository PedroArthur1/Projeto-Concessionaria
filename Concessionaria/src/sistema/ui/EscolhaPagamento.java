package sistema.ui;

/**
 * Classe que atua como um objeto de valor para encapsular os detalhes de uma
 * escolha de pagamento feita pelo usuário.
 * 
 * Contém o método de pagamento, o número de parcelas (se aplicável) e o valor
 * de cada parcela.
 *
 * @author Pedro Arthur
 * @description Classe que representa a escolha de pagamento do usuário.
 */

public class EscolhaPagamento {
    private final String metodo;
    private final Integer parcelas; 
    private final Double valorParcela;

    /**
     * Construtor para uma nova escolha de pagamento.
     *
     * @param metodo O método de pagamento escolhido (ex: DINHEIRO, DEBITO, CREDITO).
     * @param parcelas O número de parcelas, se o pagamento for a crédito. Nulo caso contrário.
     * @param valorParcela O valor de cada parcela, se o pagamento for a crédito. Nulo caso contrário.
     */

    public EscolhaPagamento(String metodo, Integer parcelas, Double valorParcela) {
        this.metodo = metodo;
        this.parcelas = parcelas;
        this.valorParcela = valorParcela;
    }

    public String getMetodo()      { return metodo; }
    public Integer getParcelas()   { return parcelas; }
    public Double getValorParcela(){ return valorParcela; }

    public boolean isCredito() { return metodo != null && metodo.startsWith("CREDITO"); }
}
