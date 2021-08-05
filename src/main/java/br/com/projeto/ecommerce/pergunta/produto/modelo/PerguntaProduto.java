package br.com.projeto.ecommerce.pergunta.produto.modelo;

import br.com.projeto.ecommerce.pergunta.produto.repositorio.PerguntaProdutoRepositorio;
import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.usuario.modelo.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
public class PerguntaProduto {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    private String titulo;

    @NotNull
    @ManyToOne( cascade = CascadeType.PERSIST )
    private Usuario usuario;

    @NotNull
    @ManyToOne( cascade = CascadeType.PERSIST )
    private Produto produto;

    @NotNull
    private final Instant instantePergunta = Instant.now();

    private PerguntaProduto(){}

    public PerguntaProduto(final String titulo, final Usuario usuario, final Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
    }

    public PerguntaProduto cadastrar(final PerguntaProdutoRepositorio perguntaProdutoRepositorio ){
        return perguntaProdutoRepositorio.save( this );
    }

    public Long getId() {
        return id;
    }

    public String getEmailInteressado(){
        return usuario.getUsername();
    }

    public String getEmailVendedor(){
        return produto.getDono().getUsername();
    }

    public String getTitulo() {
        return titulo;
    }

}
