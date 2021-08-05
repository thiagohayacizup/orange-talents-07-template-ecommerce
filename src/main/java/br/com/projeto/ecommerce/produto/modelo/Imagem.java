package br.com.projeto.ecommerce.produto.modelo;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Imagem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    @URL
    private String link;

    private Imagem(){}

    public Imagem( final String link ){
        this.link = link;
    }

    String getLink(){
        return link;
    }

}
