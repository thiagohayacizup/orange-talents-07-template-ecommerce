package br.com.projeto.ecommerce.pagamento.compra.controlador;

import br.com.projeto.ecommerce.pagamento.compra.repositorio.CompraRepositorio;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class CompraControlador {

    private final CompraRepositorio compraRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ProdutoRepositorio produtoRepositorio;

    CompraControlador(final CompraRepositorio compraRepositorio, final UsuarioRepositorio usuarioRepositorio, final ProdutoRepositorio produtoRepositorio) {
        this.compraRepositorio = compraRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.produtoRepositorio = produtoRepositorio;
    }

    @PostMapping("/compra")
    @ResponseStatus( HttpStatus.FOUND )
    public @ResponseBody CompraResposta realizarCompra( @RequestBody @Valid final CompraRequisicao compraRequisicao ){
        return compraRequisicao.realizarCompra( compraRepositorio, produtoRepositorio, usuarioRepositorio );
    }

}
