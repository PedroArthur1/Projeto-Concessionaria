package sistema.negocio.excessoes;

/**
 * @author Cleita Emanuela
 * @description Classe que define a exceção para estoque insuficiente.
 */
public class EstoqueInsuficienteException extends Exception {
    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}