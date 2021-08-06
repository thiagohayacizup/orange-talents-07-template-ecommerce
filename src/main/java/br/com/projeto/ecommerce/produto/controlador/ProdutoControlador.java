package br.com.projeto.ecommerce.produto.controlador;

import br.com.projeto.ecommerce.categoria.repositorio.CategoriaRepositorio;
import br.com.projeto.ecommerce.produto.controlador.detalhado.ProdutoDetalhadoResposta;
import br.com.projeto.ecommerce.produto.controlador.imagens.ProdutoImagensRequisicao;
import br.com.projeto.ecommerce.produto.controlador.imagens.ProdutoImagensResposta;
import br.com.projeto.ecommerce.produto.modelo.Produto;
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

    @PostMapping("/produto/{id}/imagens")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody ProdutoImagensResposta cadastrarImagens(@PathVariable("id") final Long id, @RequestParam final String dono, @Valid final ProdutoImagensRequisicao produtoImagensRequisicao ){
        return produtoImagensRequisicao.associaImagens(id, produtoRepositorio, dono, usuarioRepositorio);
    }

    @GetMapping("/produto/{id}")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody
    ProdutoDetalhadoResposta obterInformacoes(@PathVariable("id") final Long id ){
        return new ProdutoDetalhadoResposta( Produto.buscarPorId( id, produtoRepositorio ) );
    }

}
