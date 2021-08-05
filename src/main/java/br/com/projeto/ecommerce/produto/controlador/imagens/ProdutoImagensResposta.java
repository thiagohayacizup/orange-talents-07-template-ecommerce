package br.com.projeto.ecommerce.produto.controlador.imagens;

import br.com.projeto.ecommerce.produto.modelo.Produto;

import java.util.Set;

public class ProdutoImagensResposta {

     private final Produto produto;

     ProdutoImagensResposta(final Produto produto) {
          this.produto = produto;
     }

     public Set<String> getLinks(){
          return produto.getImagens();
     }

}
