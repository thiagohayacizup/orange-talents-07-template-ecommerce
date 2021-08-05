package br.com.projeto.ecommerce.pergunta.produto.controlador;

import br.com.projeto.ecommerce.pergunta.produto.modelo.PerguntaProduto;
import br.com.projeto.ecommerce.pergunta.produto.repositorio.PerguntaProdutoRepositorio;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.modelo.Usuario;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;

import javax.validation.constraints.NotNull;

class PerguntaProdutoRequisicao {

    private final String titulo;

    @NotNull
    private final Long usuarioId;

    @NotNull
    private final Long produtoId;

    PerguntaProdutoRequisicao(final String titulo, final Long usuarioId, final Long produtoId) {
        this.titulo = titulo;
        this.usuarioId = usuarioId;
        this.produtoId = produtoId;
    }

    PerguntaProdutoResposta cadastrar(final PerguntaProdutoRepositorio perguntaProdutoRepositorio, final ProdutoRepositorio produtoRepositorio, final UsuarioRepositorio usuarioRepositorio) {
        return new PerguntaProdutoResposta(
                new PerguntaProduto(
                        titulo,
                        Usuario.buscarPorId( usuarioId, usuarioRepositorio ),
                        Produto.buscarPorId( produtoId, produtoRepositorio )
                ).cadastrar( perguntaProdutoRepositorio )
        );
    }

}
