package br.com.projeto.ecommerce.pagamento.compra.modelo;

import br.com.projeto.ecommerce.pagamento.compra.GatewayPagamento;
import br.com.projeto.ecommerce.pagamento.compra.StatusCompra;
import br.com.projeto.ecommerce.pagamento.compra.repositorio.CompraRepositorio;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.modelo.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Compra {

    public static Builder construtor(){
        return new Builder();
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    @Enumerated( EnumType.STRING )
    private GatewayPagamento gatewayPagamento;

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @ManyToOne
    private Usuario comprador;

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    private final StatusCompra statusCompra = StatusCompra.INICIADA;

    private Compra(){}

    private Compra( final Builder builder ){
        this.produto = builder.produto;
        this.comprador = builder.comprador;
        this.quantidade = builder.quantidade;
        this.gatewayPagamento = builder.gatewayPagamento;
    }

    public Compra registrar(final CompraRepositorio compraRepositorio, final ProdutoRepositorio produtoRepositorio){
        produto.abaterEstoque( produtoRepositorio, quantidade );
        return compraRepositorio.save( this );
    }

    public static class Builder{

        private GatewayPagamento gatewayPagamento;
        private Produto produto;
        private Usuario comprador;
        private Integer quantidade;

        public Builder comGatewayPagamento(final GatewayPagamento gatewayPagamento) {
            this.gatewayPagamento = gatewayPagamento;
            return this;
        }

        public Builder comProduto(final Produto produto) {
            this.produto = produto;
            return this;
        }

        public Builder comComprador(final Usuario comprador) {
            this.comprador = comprador;
            return this;
        }

        public Builder comQuantidade(final Integer quantidade) {
            this.quantidade = quantidade;
            return this;
        }

        public Compra construir(){
            return new Compra( this );
        }

    }

    public String getPagamento() {
        return gatewayPagamento.redirect( id );
    }

    public Long getId(){
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Usuario getComprador() {
        return comprador;
    }

}
