package br.com.projeto.ecommerce.pagamento.compra.modelo.excessao;

public class TransacaoJaProcessadaException extends RuntimeException{

    public TransacaoJaProcessadaException( final String mensagem ){
        super(mensagem);
    }

}
