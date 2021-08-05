package br.com.projeto.ecommerce.pergunta.produto.controlador;

import br.com.projeto.ecommerce.pergunta.produto.modelo.PerguntaProduto;

class PerguntaProdutoResposta {

    private final PerguntaProduto perguntaProduto;

    PerguntaProdutoResposta(final PerguntaProduto perguntaProduto) {
        this.perguntaProduto = perguntaProduto;
    }

    public Long getId(){
        return perguntaProduto.getId();
    }

    String emailVendedor(){
        return perguntaProduto.getEmailVendedor();
    }

    String emailInteressado(){
        return perguntaProduto.getEmailInteressado();
    }

    String pergunta(){
        return perguntaProduto.getTitulo();
    }

}
