package br.com.projeto.ecommerce.produto.controlador.detalhado;

import br.com.projeto.ecommerce.opiniao.produto.modelo.OpiniaoProduto;
import br.com.projeto.ecommerce.opiniao.produto.repositorio.OpiniaoProdutoRepositorio;
import br.com.projeto.ecommerce.pergunta.produto.modelo.PerguntaProduto;
import br.com.projeto.ecommerce.pergunta.produto.repositorio.PerguntaProdutoRepositorio;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class ProdutoDetalhadoControlador {

    private final ProdutoRepositorio produtoRepositorio;
    private final PerguntaProdutoRepositorio perguntaProdutoRepositorio;
    private final OpiniaoProdutoRepositorio opiniaoProdutoRepositorio;

    ProdutoDetalhadoControlador(final ProdutoRepositorio produtoRepositorio, final PerguntaProdutoRepositorio perguntaProdutoRepositorio, final OpiniaoProdutoRepositorio opiniaoProdutoRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
        this.perguntaProdutoRepositorio = perguntaProdutoRepositorio;
        this.opiniaoProdutoRepositorio = opiniaoProdutoRepositorio;
    }

    @GetMapping("/produto/{id}")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody
    ProdutoDetalhadoResposta obterInformacoes(@PathVariable("id") final Long id ){
        return new ProdutoDetalhadoResposta(
                Produto.buscarPorId( id, produtoRepositorio ),
                PerguntaProduto.buscarPorIdProduto(id, perguntaProdutoRepositorio),
                OpiniaoProduto.buscarPorIdProduto( id, opiniaoProdutoRepositorio )
        );
    }

}
