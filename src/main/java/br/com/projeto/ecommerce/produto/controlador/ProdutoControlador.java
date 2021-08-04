package br.com.projeto.ecommerce.produto.controlador;

import br.com.projeto.ecommerce.categoria.repositorio.CategoriaRepositorio;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class ProdutoControlador {

    private final ProdutoRepositorio produtoRepositorio;

    private final CategoriaRepositorio categoriaRepositorio;

    private final UsuarioRepositorio usuarioRepositorio;

    ProdutoControlador(final ProdutoRepositorio produtoRepositorio, final CategoriaRepositorio categoriaRepositorio, final UsuarioRepositorio usuarioRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
        this.categoriaRepositorio = categoriaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @PostMapping("/produto")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody ProdutoResposta cadastrar( @RequestBody @Valid final ProdutoRequisicao produtoRequisicao ){
        return produtoRequisicao.cadastrar( produtoRepositorio, categoriaRepositorio, usuarioRepositorio );
    }

}
