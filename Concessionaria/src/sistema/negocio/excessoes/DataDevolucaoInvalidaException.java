package concessionaria.negocio.excessoes;

public class DataDevolucaoInvalidaException extends Exception {
    public DataDevolucaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
