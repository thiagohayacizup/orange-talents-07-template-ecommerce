package br.com.projeto.ecommerce.pagamento.compra.modelo.excessao;

public class CompraNaoEncontradaException extends RuntimeException{

    public CompraNaoEncontradaException( final String mensagem ){
        super( mensagem );
    }

}
