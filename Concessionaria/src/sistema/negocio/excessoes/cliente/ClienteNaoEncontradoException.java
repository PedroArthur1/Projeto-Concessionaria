package sistema.negocio.excessoes.cliente;
public class ClienteNaoEncontradoException extends Exception {
    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}