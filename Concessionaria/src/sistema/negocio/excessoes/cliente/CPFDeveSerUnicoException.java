package sistema.negocio.excessoes.cliente;

/**
 * @author Emanuel Bezerra
 * @description Classe que define a exceção para quando o CPF do cliente já existe.
 */

public class CPFDeveSerUnicoException extends Exception {
    public CPFDeveSerUnicoException(String mensagem) {
        super(mensagem);
    }

}
