package sistema.negocio.excessoes;

/**
 * @author Pedro Arthur
 * @description Classe que define a exceção para uma data de devolução inválida.
 */

public class DataDevolucaoInvalidaException extends Exception {
    public DataDevolucaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
