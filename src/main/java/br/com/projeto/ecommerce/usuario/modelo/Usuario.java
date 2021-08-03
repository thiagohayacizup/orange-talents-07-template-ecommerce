package br.com.projeto.ecommerce.usuario.modelo;

import br.com.projeto.ecommerce.email.Email;
import br.com.projeto.ecommerce.usuario.modelo.excessao.UsuarioEmailInvalidoException;
import br.com.projeto.ecommerce.usuario.modelo.excessao.UsuarioJaCadastradoException;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
public class Usuario {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    @Size( min =  6 )
    private String senha;

    @NotNull
    private final Instant dataCadastro = Instant.now();

    private Usuario(){}

    public Usuario(final String email, final String senha){
        if( !Email.match( email ) )
            throw new UsuarioEmailInvalidoException(
                    String.format("Email { %s } com formato invalido.", email)
            );
        this.email = email;
        this.senha = new BCryptPasswordEncoder().encode( senha );
    }

    public Long getId(){
        return id;
    }

    public Usuario cadastrar(final UsuarioRepositorio usuarioRepositorio){
        usuarioRepositorio.findByEmail( email )
                .ifPresent( usuario -> {
                    throw new UsuarioJaCadastradoException(
                        String.format("Usuario com email { %s } ja esta cadastrado.", email )
                    );
                });
        return usuarioRepositorio.save( this );
    }

}
