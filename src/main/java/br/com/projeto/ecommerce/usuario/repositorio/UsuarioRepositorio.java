package br.com.projeto.ecommerce.usuario.repositorio;

import br.com.projeto.ecommerce.usuario.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail( final String email );

}
