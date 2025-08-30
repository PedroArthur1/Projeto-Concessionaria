package sistema.negocio.excessoes;

/**
 * @author Emanuel Bezerra
 * @description Classe que define a exceção para uma quilometragem menor que a original na devolucao do veiculo e na edicao.
 */

public class QuilometragemMenorQueOriginalException extends Exception{
    public QuilometragemMenorQueOriginalException (String mensagem){
        super(mensagem);
    }
}
