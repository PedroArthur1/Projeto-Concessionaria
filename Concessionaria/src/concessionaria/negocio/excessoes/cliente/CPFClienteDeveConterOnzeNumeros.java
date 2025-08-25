package excessoes.cliente;

public class CPFClienteDeveConterOnzeNumeros extends Exception{
    public CPFClienteDeveConterOnzeNumeros(String mensagem){
        super(mensagem);
    }
}
