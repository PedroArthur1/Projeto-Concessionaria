package concessionaria.negocio.excessoes;

public class ParcelasInvalidasException extends Exception {
    public ParcelasInvalidasException(String mensagem) {
        super(mensagem);
    }
}
