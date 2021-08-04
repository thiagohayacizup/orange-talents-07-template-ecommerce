package br.com.projeto.ecommerce.usuario.modelo;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
class Perfil implements GrantedAuthority {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    private String nome;

    private Perfil(){}

    Perfil( final String nome ){
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return nome;
    }

}
