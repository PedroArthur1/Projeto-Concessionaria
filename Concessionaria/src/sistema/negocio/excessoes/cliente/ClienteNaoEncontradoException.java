package sistema.negocio.excessoes.cliente;
/**
 * @author Emanuel Bezerra
 * @description Classe que define a exceção para quando um cliente não é encontrado.
 */
public class ClienteNaoEncontradoException extends Exception {
    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}