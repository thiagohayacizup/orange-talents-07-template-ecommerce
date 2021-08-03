package br.com.projeto.ecommerce.categoria.controlador;

import br.com.projeto.ecommerce.categoria.modelo.Categoria;
import br.com.projeto.ecommerce.categoria.repositorio.CategoriaRepositorio;

class CategoriaRequisicao {

    private final String nome;

    private final String categoriaMae;

    CategoriaRequisicao(final String nome, final String categoriaMae) {
        this.nome = nome;
        this.categoriaMae = categoriaMae;
    }

    CategoriaResposta cadastrar( final CategoriaRepositorio categoriaRepositorio ){
        return new CategoriaResposta(
                new Categoria(
                        nome,
                        Categoria
                                .buscarPorNome( categoriaMae, categoriaRepositorio )
                                .orElse( null )
                ).cadastrar( categoriaRepositorio )
        );
    }

}
