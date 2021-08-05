package br.com.projeto.ecommerce.opiniao.produto.controlador;

import br.com.projeto.ecommerce.opiniao.produto.repositorio.OpiniaoProdutoRepositorio;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class OpiniaoProdutoControlador {

    private final OpiniaoProdutoRepositorio opiniaoProdutoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ProdutoRepositorio produtoRepositorio;

    OpiniaoProdutoControlador(final OpiniaoProdutoRepositorio opiniaoProdutoRepositorio, final UsuarioRepositorio usuarioRepositorio, final ProdutoRepositorio produtoRepositorio) {
        this.opiniaoProdutoRepositorio = opiniaoProdutoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.produtoRepositorio = produtoRepositorio;
    }

    @PostMapping("/opiniao-produto")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody OpiniaoProdutoResposta cadastrar(@RequestBody @Valid final OpiniaoProdutoRequisicao opiniaoProdutoRequisicao){
        return opiniaoProdutoRequisicao.cadastrar( opiniaoProdutoRepositorio, produtoRepositorio, usuarioRepositorio );
    }

}
