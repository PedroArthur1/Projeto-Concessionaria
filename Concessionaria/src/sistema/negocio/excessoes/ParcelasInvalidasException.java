package sistema.negocio.excessoes;
/**
 * @author Cleita Emanuela
 * @description Classe que define a exceção para parcelas inválidas.
 */

public class ParcelasInvalidasException extends Exception {
    public ParcelasInvalidasException(String mensagem) {
        super(mensagem);
    }
}
