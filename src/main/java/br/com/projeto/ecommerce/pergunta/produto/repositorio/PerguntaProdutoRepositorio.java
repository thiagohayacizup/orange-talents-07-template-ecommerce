package br.com.projeto.ecommerce.pergunta.produto.repositorio;

import br.com.projeto.ecommerce.pergunta.produto.modelo.PerguntaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerguntaProdutoRepositorio extends JpaRepository<PerguntaProduto, Long> {

    List<PerguntaProduto> findByProduto_id(final Long id );

}
