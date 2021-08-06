package br.com.projeto.ecommerce.produto.modelo.excessao;

public class NaoPodeAbaterQuantidadeException extends RuntimeException {

    public NaoPodeAbaterQuantidadeException( final String mensagem ){
        super( mensagem );
    }

}
