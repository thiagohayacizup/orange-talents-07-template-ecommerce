package br.com.projeto.ecommerce.usuario.modelo;

import br.com.projeto.ecommerce.email.Email;
import br.com.projeto.ecommerce.usuario.modelo.excessao.UsuarioEmailInvalidoException;
import br.com.projeto.ecommerce.usuario.modelo.excessao.UsuarioJaCadastradoException;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario implements UserDetails {

    public static Usuario buscarPorEmail( final String email, final UsuarioRepositorio usuarioRepositorio ){
        return usuarioRepositorio.findByEmail( email ).get();
    }

    public static UsernamePasswordAuthenticationToken autenticar( final Long id, final UsuarioRepositorio usuarioRepositorio ){
        final Usuario usuario = usuarioRepositorio.findById( id ).get();
        return new UsernamePasswordAuthenticationToken( usuario, null, usuario.perfis );
    }

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

    @ManyToMany( fetch = FetchType.EAGER )
    private final List<Perfil> perfis = new ArrayList<>();

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
