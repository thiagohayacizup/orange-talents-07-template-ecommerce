package br.com.projeto.ecommerce.categoria.modelo.excessao;

public class CategoriaNaoEncontradaException extends RuntimeException{

    public CategoriaNaoEncontradaException( final String mensagem ){
        super( mensagem );
    }

}
