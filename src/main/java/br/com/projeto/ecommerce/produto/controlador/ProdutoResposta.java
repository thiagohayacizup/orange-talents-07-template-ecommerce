package br.com.projeto.ecommerce.produto.controlador;

import br.com.projeto.ecommerce.produto.modelo.Produto;

class ProdutoResposta {

    private final Produto produto;

    ProdutoResposta(final Produto produto) {
        this.produto = produto;
    }

    public Long getId(){
        return produto.getId();
    }

    public String getNome(){
        return produto.getNome();
    }

}
