package br.com.projeto.ecommerce.produto.modelo.excessao;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException( final String mensagem ){
        super( mensagem );
    }

}
