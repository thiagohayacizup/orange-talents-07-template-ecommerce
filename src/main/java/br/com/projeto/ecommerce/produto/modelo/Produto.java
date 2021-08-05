package br.com.projeto.ecommerce.produto.modelo;

import br.com.projeto.ecommerce.categoria.modelo.Categoria;
import br.com.projeto.ecommerce.produto.modelo.excessao.ProdutoDeveTerNoMinimoTresCaracteristicasException;
import br.com.projeto.ecommerce.produto.modelo.excessao.ProdutoNaoEncontradoException;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import br.com.projeto.ecommerce.usuario.modelo.Usuario;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Produto {

    public static Builder construtor(){
        return new Builder();
    }

    public static Produto buscarPorId( final Long id, final ProdutoRepositorio produtoRepositorio ){
        return produtoRepositorio.findById( id )
                .orElseThrow( () -> new ProdutoNaoEncontradoException(
                        String.format("Produto { %d } nao encontrado.", id)
                ));
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @DecimalMin( value = "0.0" )
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    private Integer quantidadeDisponivel;

    @NotNull
    @OneToMany( cascade = CascadeType.PERSIST )
    private List<Caracteristica> caracteristicas;

    @NotBlank
    @Size( max = 1000 )
    private String descricao;

    @NotNull
    @OneToOne
    private Categoria categoria;

    @NotNull
    @ManyToOne( cascade = CascadeType.PERSIST )
    private Usuario dono;

    @NotNull
    private final Instant dataCadastro = Instant.now();

    @OneToMany( cascade = CascadeType.MERGE )
    @NotNull
    private final Set<Imagem> linksImagens = new HashSet<>();

    private Produto(){}

    public Produto( final Builder builder ){
        this.nome = builder.nome;
        this.valor = builder.valor;
        this.quantidadeDisponivel = builder.quantidadeDisponivel;
        this.descricao = builder.descricao;
        this.categoria = builder.categoria;
        if( builder.caracteristicas.size() < 3 )
            throw new ProdutoDeveTerNoMinimoTresCaracteristicasException(
                    String.format("Produto { %s } deve ter no minimo 3 caracteristicas", nome)
            );
        this.caracteristicas = builder.caracteristicas;
        this.dono = builder.usuario;
    }

    public static class Builder {

        private String nome;
        private BigDecimal valor;
        private Integer quantidadeDisponivel;
        private List<Caracteristica> caracteristicas;
        private String descricao;
        private Categoria categoria;
        private Usuario usuario;

        public Builder comUsuario( final Usuario usuario ){
            this.usuario = usuario;
            return this;
        }

        public Builder comNome(final String nome) {
            this.nome = nome;
            return this;
        }

        public Builder comValor(final BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        public Builder comQuantidadeDisponivel(final Integer quantidadeDisponivel) {
            this.quantidadeDisponivel = quantidadeDisponivel;
            return this;
        }

        public Builder comCaracteristicas(final List<Caracteristica> caracteristicas) {
            this.caracteristicas = caracteristicas;
            return this;
        }

        public Builder comDescricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder comCategoria(final Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public Produto construir(){
            return new Produto( this );
        }


    }
    public Produto cadastrar( final ProdutoRepositorio produtoRepositorio ){
        return produtoRepositorio.save( this );
    }

    public Produto associaImagens(final Set<String> imagens, final Usuario dono, final ProdutoRepositorio produtoRepositorio){
        this.dono.seNaoDonoThrow( dono );
        linksImagens.addAll(
                imagens
                        .stream()
                        .map(Imagem::new)
                        .collect(Collectors.toSet() )
        );
        return produtoRepositorio.save( this );
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Set<String> getImagens(){
        return linksImagens
                .stream()
                .map(Imagem::getLink)
                .collect(Collectors.toSet() );
    }

    public Usuario getDono() {
        return dono;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

}
