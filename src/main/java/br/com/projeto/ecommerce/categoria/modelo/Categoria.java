package br.com.projeto.ecommerce.categoria.modelo;

import br.com.projeto.ecommerce.categoria.modelo.excessao.CategoriaJaCadastradaException;
import br.com.projeto.ecommerce.categoria.repositorio.CategoriaRepositorio;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Categoria {

    public static Optional<Categoria> buscarPorNome( final String nome, final CategoriaRepositorio categoriaRepositorio ){
        return categoriaRepositorio.findByNome( nome );
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    private String nome;

    @OneToOne
    private Categoria categoriaMae;

    private Categoria(){}

    public Categoria( final String nome, final Categoria categoriaMae ){
        this.nome = nome;
        this.categoriaMae = categoriaMae;
    }

    public Categoria cadastrar( final CategoriaRepositorio categoriaRepositorio ){
        categoriaRepositorio.findByNome( nome )
                .ifPresent( categoria -> {
                    throw new CategoriaJaCadastradaException(
                            String.format("Categoria { %s } ja esta cadastrada.", nome)
                    );
                });
        return categoriaRepositorio.save( this );
    }

    public String getNome() {
        return nome;
    }

    public String getCategoriaMae() {
        final Optional<Categoria> categoriaMae = Optional.ofNullable(this.categoriaMae);
        if( categoriaMae.isPresent() ) return categoriaMae.get().getNome();
        return "";
    }

}
