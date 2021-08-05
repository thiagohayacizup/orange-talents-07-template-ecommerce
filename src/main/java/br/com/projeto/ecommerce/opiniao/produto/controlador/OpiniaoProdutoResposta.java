package br.com.projeto.ecommerce.opiniao.produto.controlador;

import br.com.projeto.ecommerce.opiniao.produto.modelo.OpiniaoProduto;

class OpiniaoProdutoResposta {

    private final OpiniaoProduto opiniaoProduto;

    OpiniaoProdutoResposta(final OpiniaoProduto opiniaoProduto) {
        this.opiniaoProduto = opiniaoProduto;
    }

    public Long getId(){
        return opiniaoProduto.getId();
    }

}
