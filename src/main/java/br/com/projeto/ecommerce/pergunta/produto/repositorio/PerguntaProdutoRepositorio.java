package br.com.projeto.ecommerce.pergunta.produto.repositorio;

import br.com.projeto.ecommerce.pergunta.produto.modelo.PerguntaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaProdutoRepositorio extends JpaRepository<PerguntaProduto, Long> {
}
