package sistema.ui;

public class EscolhaPagamento {
    private final String metodo;
    private final Integer parcelas; 
    private final Double valorParcela;

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
