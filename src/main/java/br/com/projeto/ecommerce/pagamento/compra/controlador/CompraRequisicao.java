package br.com.projeto.ecommerce.pagamento.compra.controlador;

import br.com.projeto.ecommerce.pagamento.compra.GatewayPagamento;
import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;
import br.com.projeto.ecommerce.pagamento.compra.repositorio.CompraRepositorio;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.modelo.Usuario;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

class CompraRequisicao {

    private final Compra.Builder builder;

    CompraRequisicao() {
        builder = Compra.construtor();
    }

    @NotNull
    private GatewayPagamento gatewayPagamento;

    public void setGatewayPagamento( final GatewayPagamento gatewayPagamento ){
        this.gatewayPagamento = gatewayPagamento;
    }

    @NotNull
    private Long produtoId;

    public void setProdutoId( final Long produtoId ){
        this.produtoId = produtoId;
    }

    @NotNull
    private Long compradorId;

    public void setCompradorId( final Long compradorId ){
        this.compradorId = compradorId;
    }

    @Positive
    @NotNull
    private int quantidade;

    public void setQuantidade( final int quantidade ){
        this.quantidade = quantidade;
    }

    CompraResposta realizarCompra(final CompraRepositorio compraRepositorio, final ProdutoRepositorio produtoRepositorio, final UsuarioRepositorio usuarioRepositorio) {
        return new CompraResposta(
                builder
                        .comComprador( Usuario.buscarPorId( compradorId, usuarioRepositorio ) )
                        .comProduto( Produto.buscarPorId(produtoId, produtoRepositorio) )
                        .comGatewayPagamento( gatewayPagamento )
                        .comQuantidade( quantidade )
                        .construir()
                        .registrar( compraRepositorio, produtoRepositorio )
        );
    }

}
