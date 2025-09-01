package sistema.negocio.excessoes.cliente;

/**
 * @author Cleita Emanuela
 * @description Classe que define a exceção para quando o CPF do cliente já existe.
 */

public class CPFDeveSerUnicoException extends Exception {
    public CPFDeveSerUnicoException(String mensagem) {
        super(mensagem);
    }

}
