package br.com.projeto.ecommerce.opiniao.produto.controlador;

import br.com.projeto.ecommerce.opiniao.produto.modelo.OpiniaoProduto;
import br.com.projeto.ecommerce.opiniao.produto.repositorio.OpiniaoProdutoRepositorio;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.modelo.Usuario;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

class OpiniaoProdutoRequisicao {

    private final OpiniaoProduto.Builder builder;

    OpiniaoProdutoRequisicao() {
        builder = OpiniaoProduto.construtor();
    }

    public void setNota( final Integer nota ){
        builder.comNota( nota );
    }

    public void setTitulo( final String titulo ){
        builder.comTitulo( titulo );
    }

    public void setDescricao( final String descricao ){
        builder.comDescricao( descricao );
    }

    @NotNull
    private Long usuarioId;

    void setUsuarioId(final Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @NotNull
    private Long produtoId;

    void setProdutoId(final Long produtoId) {
        this.produtoId = produtoId;
    }

    OpiniaoProdutoResposta cadastrar(final OpiniaoProdutoRepositorio opiniaoProdutoRepositorio, final ProdutoRepositorio produtoRepositorio, final UsuarioRepositorio usuarioRepositorio){
        return new OpiniaoProdutoResposta(
                builder
                        .comUsuario( Usuario.buscarPorId( usuarioId, usuarioRepositorio ) )
                        .comProduto( Produto.buscarPorId( produtoId, produtoRepositorio ) )
                        .construir()
                        .cadastrar( opiniaoProdutoRepositorio )
        );
    }

}
