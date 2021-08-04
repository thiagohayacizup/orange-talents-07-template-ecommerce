package br.com.projeto.ecommerce.produto.controlador;

import br.com.projeto.ecommerce.categoria.repositorio.CategoriaRepositorio;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class ProdutoControlador {

    private final ProdutoRepositorio produtoRepositorio;

    private final CategoriaRepositorio categoriaRepositorio;

    ProdutoControlador(final ProdutoRepositorio produtoRepositorio, final CategoriaRepositorio categoriaRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
        this.categoriaRepositorio = categoriaRepositorio;
    }

    @PostMapping("/produto")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody ProdutoResposta cadastrar( @RequestBody @Valid final ProdutoRequisicao produtoRequisicao ){
        return produtoRequisicao.cadastrar( produtoRepositorio, categoriaRepositorio );
    }

}
