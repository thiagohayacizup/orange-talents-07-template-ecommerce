package br.com.projeto.ecommerce.opiniao.produto.modelo;

import br.com.projeto.ecommerce.opiniao.produto.repositorio.OpiniaoProdutoRepositorio;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.usuario.modelo.Usuario;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
public class OpiniaoProduto {

    public static Integer totalNotas(final List<OpiniaoProduto> opiniaoProdutoList) {
        return opiniaoProdutoList.size();
    }

    public static Double calcularMedia(final List<OpiniaoProduto> opiniaoProdutoList) {
        return opiniaoProdutoList
                .stream()
                .mapToDouble(OpiniaoProduto::getNota)
                .reduce(Double::sum)
                .getAsDouble() / opiniaoProdutoList.size();
    }

    public static List<OpiniaoProduto> buscarPorIdProduto(final Long id, final OpiniaoProdutoRepositorio opiniaoProdutoRepositorio) {
        return opiniaoProdutoRepositorio.findByProduto_id( id );
    }

    public static Builder construtor(){
        return new Builder();
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Min(1)
    @Max(5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank
    @Size( max = 500 )
    private String descricao;

    @NotNull
    @ManyToOne( cascade = CascadeType.PERSIST )
    private Usuario usuario;

    @NotNull
    @ManyToOne( cascade = CascadeType.PERSIST )
    private Produto produto;

    private OpiniaoProduto(){}

    private OpiniaoProduto( final Builder builder ){
        this.nota = builder.nota;
        this.titulo = builder.titulo;
        this.descricao = builder.descricao;
        this.usuario = builder.usuario;
        this.produto = builder.produto;
    }

    public OpiniaoProduto cadastrar(final OpiniaoProdutoRepositorio opiniaoProdutoRepositorio){
        return opiniaoProdutoRepositorio.save( this );
    }

    public static class Builder{

        private Integer nota;
        private String titulo;
        private String descricao;
        private Usuario usuario;
        private Produto produto;

        public Builder comNota(final Integer nota) {
            this.nota = nota;
            return this;
        }

        public Builder comTitulo(final String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder comDescricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder comUsuario(final Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder comProduto(final Produto produto) {
            this.produto = produto;
            return this;
        }

        public OpiniaoProduto construir(){
            return new OpiniaoProduto( this );
        }

    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getNota() {
        return nota;
    }

}
