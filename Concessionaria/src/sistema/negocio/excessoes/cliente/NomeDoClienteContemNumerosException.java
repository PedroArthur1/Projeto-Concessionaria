package sistema.negocio.excessoes.cliente;

/**
 * @author Emanuel Bezerra
 * @description Classe que define a exceção para quando o nome do cliente contém números.
 */

public class NomeDoClienteContemNumerosException extends Exception {
    public NomeDoClienteContemNumerosException(String mensagem) {
        super(mensagem);
    }
}
