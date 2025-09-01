package sistema.negocio.excessoes;
/**
 * @author Cleita Emanuela
 * @description Classe que define a exceção para quando um veículo não é encontrado.
 */
public class VeiculoNaoEncontradoException extends Exception {
    public VeiculoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    
}