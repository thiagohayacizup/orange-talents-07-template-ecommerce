package br.com.projeto.ecommerce.produto.repositorio;

import br.com.projeto.ecommerce.produto.modelo.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaracteristicaRepositorio extends JpaRepository<Caracteristica, Long> {
}
