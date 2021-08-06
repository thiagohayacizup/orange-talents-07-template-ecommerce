package br.com.projeto.ecommerce.pagamento.compra.repositorio;

import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepositorio extends JpaRepository<Compra, Long> {
}
