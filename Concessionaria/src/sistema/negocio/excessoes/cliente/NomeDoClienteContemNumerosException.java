package sistema.negocio.excessoes.cliente;

public class NomeDoClienteContemNumerosException extends Exception {
    public NomeDoClienteContemNumerosException(String mensagem) {
        super(mensagem);
    }
}
