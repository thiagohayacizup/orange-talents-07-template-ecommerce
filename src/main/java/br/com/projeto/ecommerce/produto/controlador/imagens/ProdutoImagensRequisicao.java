package br.com.projeto.ecommerce.produto.controlador.imagens;

import br.com.projeto.ecommerce.produto.modelo.Produto;
import br.com.projeto.ecommerce.produto.repositorio.ProdutoRepositorio;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProdutoImagensRequisicao {

    @NotNull
    @Size( min = 1 )
    private final List<MultipartFile> imagens;

    public ProdutoImagensRequisicao(final List<MultipartFile> imagens) {
        this.imagens = imagens;
    }

    private CloudFake carregarNaNuvem(){
        return new CloudFake( imagens );
    }

    public ProdutoImagensResposta associaImagens(final Long id, final ProdutoRepositorio produtoRepositorio){
        return new ProdutoImagensResposta(
            Produto
                    .buscarPorId( id, produtoRepositorio )
                    .associaImagens(carregarNaNuvem().obterLinksEnderecos(), produtoRepositorio)
        );
    }

}
