package sistema.negocio.excessoes.cliente;

public class CPFDeveSerUnicoException extends Exception {
    public CPFDeveSerUnicoException(String mensagem) {
        super(mensagem);
    }

}
