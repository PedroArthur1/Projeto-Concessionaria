package sistema.negocio.excessoes;

/**
 * @author Cleita Emanuela
 * @description Classe que define a exceção para uma data de devolução inválida.
 */

public class DataDevolucaoInvalidaException extends Exception {
    public DataDevolucaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
