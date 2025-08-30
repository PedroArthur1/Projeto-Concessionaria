package sistema.negocio.excessoes;

/**
 * @author Pedro Arthur
 * @description Classe que define a exceção para placas de veículo duplicadas.
 */
public class PlacaDeveSerUnicaException extends Exception{
    public PlacaDeveSerUnicaException(String mensagem) {
        super(mensagem);
    }
}
