package br.com.projeto.ecommerce.pergunta.produto.controlador;

import br.com.projeto.ecommerce.pergunta.produto.repositorio.PerguntaProdutoRepositorio;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class PerguntaProdutoControlador {

    private final PerguntaProdutoRepositorio perguntaProdutoRepositorio;
    private final ProdutoRepositorio produtoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    PerguntaProdutoControlador(final PerguntaProdutoRepositorio perguntaProdutoRepositorio, final ProdutoRepositorio produtoRepositorio, final UsuarioRepositorio usuarioRepositorio) {
        this.perguntaProdutoRepositorio = perguntaProdutoRepositorio;
        this.produtoRepositorio = produtoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @PostMapping("/pergunta-produto")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody PerguntaProdutoResposta cadastrar(@RequestBody @Valid final PerguntaProdutoRequisicao perguntaProdutoRequisicao){
        final PerguntaProdutoResposta cadastrar = perguntaProdutoRequisicao.cadastrar(perguntaProdutoRepositorio, produtoRepositorio, usuarioRepositorio);
        EmailFake.send( cadastrar.emailVendedor(), cadastrar.pergunta(), cadastrar.emailInteressado() );
        return cadastrar;
    }

}
