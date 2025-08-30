package sistema.negocio.excessoes.cliente;

/**
 * @author Emanuel Bezerra
 * @description Classe que define a exceção para quando o CPF do cliente não contém 11 números.
 */

public class CPFClienteDeveConterOnzeNumeros extends Exception{
    public CPFClienteDeveConterOnzeNumeros(String mensagem){
        super(mensagem);
    }
}
