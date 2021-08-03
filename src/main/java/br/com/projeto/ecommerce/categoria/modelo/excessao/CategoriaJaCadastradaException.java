package br.com.projeto.ecommerce.categoria.modelo.excessao;

public class CategoriaJaCadastradaException extends RuntimeException {

    public CategoriaJaCadastradaException( final String mensagem ){
        super( mensagem );
    }

}
