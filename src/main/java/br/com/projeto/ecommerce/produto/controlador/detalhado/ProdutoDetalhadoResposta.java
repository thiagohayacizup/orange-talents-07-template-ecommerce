package br.com.projeto.ecommerce.produto.controlador.detalhado;

import br.com.projeto.ecommerce.opiniao.produto.modelo.OpiniaoProduto;
import br.com.projeto.ecommerce.pergunta.produto.modelo.PerguntaProduto;
import br.com.projeto.ecommerce.produto.controlador.CaracteristicaResposta;
import br.com.projeto.ecommerce.produto.modelo.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProdutoDetalhadoResposta {

    private final Produto produto;

    private final List<PerguntaProduto> perguntaProduto;

    private final List<OpiniaoProduto> opiniaoProduto;

    ProdutoDetalhadoResposta(final Produto produto, final List<PerguntaProduto> perguntaProduto, final List<OpiniaoProduto> opiniaoProduto) {
        this.produto = produto;
        this.perguntaProduto = perguntaProduto;
        this.opiniaoProduto = opiniaoProduto;
    }

    public String getNome(){
        return produto.getNome();
    }

    public BigDecimal getValor(){
        return produto.getValor();
    }

    public Set<String> getImanges(){
        return produto.getImagens();
    }

    public List<CaracteristicaResposta> getCaracteristicas(){
        return produto.getCaracteristicas()
                .stream()
                .map(CaracteristicaResposta::new)
                .collect(Collectors.toList());
    }

    public String getDescricao(){
        return produto.getDescricao();
    }

    public List<String> getPerguntas(){
        return perguntaProduto
                .stream()
                .map(PerguntaProduto::getTitulo)
                .collect(Collectors.toList());
    }

    public List<String> getOpinioes(){
        return opiniaoProduto
                .stream()
                .map(OpiniaoProduto::getDescricao)
                .collect(Collectors.toList());
    }

    public Double getMedia(){
        return OpiniaoProduto.calcularMedia( opiniaoProduto );
    }

    public Integer getTotalNotasProduto(){
        return OpiniaoProduto.totalNotas( opiniaoProduto );
    }

}
