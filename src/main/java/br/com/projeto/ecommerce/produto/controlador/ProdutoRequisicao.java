package br.com.projeto.ecommerce.produto.controlador;

import br.com.projeto.ecommerce.categoria.modelo.Categoria;
import br.com.projeto.ecommerce.categoria.repositorio.CategoriaRepositorio;
import br.com.projeto.ecommerce.produto.modelo.Caracteristica;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class ProdutoRequisicao {

    private final Produto.Builder builder;

    public ProdutoRequisicao(){
        builder = Produto.construtor();
    }

    public void setNome( final String nome ){
        builder.comNome( nome );
    }

    public void setValor( final BigDecimal valor ){
        builder.comValor( valor );
    }

    public void setQuantidadeDisponivel( final Integer quantidadeDisponivel ){
        builder.comQuantidadeDisponivel( quantidadeDisponivel );
    }

    @NotNull
    private List<CaracteristicaRequisicao> caracteristicas;

    public void setCaracteristicas( final List<CaracteristicaRequisicao> caracteristicas ){
        this.caracteristicas = caracteristicas;
    }

    public void setDescricao( final String descricao ){
        builder.comDescricao( descricao );
    }

    @NotNull
    private Long categoriaId;

    public void setCategoriaId( final Long categoriaId ){
        this.categoriaId = categoriaId;
    }

    ProdutoResposta cadastrar(final ProdutoRepositorio produtoRepositorio, final CategoriaRepositorio categoriaRepositorio){
        return new ProdutoResposta(
                builder.comCaracteristicas(
                        caracteristicas
                                .stream()
                                .map( caracteristicas -> new Caracteristica(caracteristicas.getNome(), caracteristicas.getDescricao()) )
                                .collect(Collectors.toList())
                ).comCategoria( Categoria.buscarPorId( categoriaId, categoriaRepositorio ) )
                .construir()
                .cadastrar( produtoRepositorio )
        );
    }

}
