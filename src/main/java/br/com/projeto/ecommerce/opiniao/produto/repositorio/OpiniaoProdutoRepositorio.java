package br.com.projeto.ecommerce.opiniao.produto.repositorio;

import br.com.projeto.ecommerce.opiniao.produto.modelo.OpiniaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpiniaoProdutoRepositorio extends JpaRepository<OpiniaoProduto, Long> {

    List<OpiniaoProduto> findByProduto_id( final Long id );

}
