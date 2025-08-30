package sistema.negocio.excessoes;
/**
 * @author Pedro Arthur
 * @description Classe que define a exceção para parcelas inválidas.
 */

public class ParcelasInvalidasException extends Exception {
    public ParcelasInvalidasException(String mensagem) {
        super(mensagem);
    }
}
