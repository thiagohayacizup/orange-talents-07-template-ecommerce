package br.com.projeto.ecommerce.pagamento.compra.modelo;

import br.com.projeto.ecommerce.pagamento.compra.GatewayPagamento;
import br.com.projeto.ecommerce.pagamento.compra.StatusCompra;
import br.com.projeto.ecommerce.pagamento.compra.modelo.excessao.CompraNaoEncontradaException;
import br.com.projeto.ecommerce.pagamento.compra.modelo.excessao.TransacaoJaProcessadaException;
import br.com.projeto.ecommerce.pagamento.compra.repositorio.CompraRepositorio;
import br.com.projeto.ecommerce.pagamento.processamento.modelo.Transacao;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.modelo.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Compra {

    public static Builder construtor(){
        return new Builder();
    }

    public static Compra buscarPorId( final Long id, final CompraRepositorio compraRepositorio ){
        return compraRepositorio.findById( id )
                .orElseThrow(() -> new CompraNaoEncontradaException(
                        String.format("Compra { %d } nao encontrada.", id)
                ));
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
    @OneToMany( cascade = CascadeType.MERGE )
    private final Set<Transacao> transacoes = new HashSet<>();

    @NotNull
    @Enumerated( EnumType.STRING )
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

    public Compra adicionarTransacao(final Transacao transacao, final CompraRepositorio compraRepositorio) {
        if( transacaoCadastrada( transacao ) ) throw new TransacaoJaProcessadaException("Transacao ja foi processada");
        if( transacaoConcluidaCadastrada() ) throw new TransacaoJaProcessadaException("Transacao ja foi concluida com sucesso");
        transacoes.add( transacao );
        return compraRepositorio.save( this );
    }

    public boolean transacaoConcluidaCadastrada(){
        return !transacoes
                .stream()
                .filter(Transacao::concluidaComSucesso)
                .collect(Collectors.toSet())
                .isEmpty();
    }

    private boolean transacaoCadastrada( final Transacao transacao ){
        return !transacoes
                .stream()
                .filter( t -> t.transacaoIgual( transacao ) )
                .collect(Collectors.toSet())
                .isEmpty();

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
