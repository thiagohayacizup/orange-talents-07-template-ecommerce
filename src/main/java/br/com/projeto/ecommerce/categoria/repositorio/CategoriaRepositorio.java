package br.com.projeto.ecommerce.categoria.repositorio;

import br.com.projeto.ecommerce.categoria.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNome( final String nome );

}
