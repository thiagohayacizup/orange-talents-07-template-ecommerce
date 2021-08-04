package br.com.projeto.ecommerce.produto.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Caracteristica {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    private Caracteristica(){}

    public Caracteristica(final String nome, final String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

}
