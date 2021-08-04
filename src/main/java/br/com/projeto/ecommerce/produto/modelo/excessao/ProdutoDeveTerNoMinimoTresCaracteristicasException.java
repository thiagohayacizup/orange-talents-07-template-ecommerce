package br.com.projeto.ecommerce.produto.modelo.excessao;

public class ProdutoDeveTerNoMinimoTresCaracteristicasException extends RuntimeException{

    public ProdutoDeveTerNoMinimoTresCaracteristicasException( final String mensagem ){
        super( mensagem );
    }

}
