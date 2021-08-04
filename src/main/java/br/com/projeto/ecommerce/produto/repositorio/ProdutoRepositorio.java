package br.com.projeto.ecommerce.produto.repositorio;

import br.com.projeto.ecommerce.produto.modelo.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
}
