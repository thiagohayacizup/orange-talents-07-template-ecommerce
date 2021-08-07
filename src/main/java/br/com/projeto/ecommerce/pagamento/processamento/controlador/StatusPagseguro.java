package br.com.projeto.ecommerce.pagamento.processamento.controlador;

import br.com.projeto.ecommerce.pagamento.processamento.modelo.StatusTransacao;

enum StatusPagseguro {

    SUCESSO,
    ERRO;

    StatusTransacao normaliza() {
        if( this.equals( SUCESSO ) )
            return StatusTransacao.SUCESSO;
        return StatusTransacao.ERRO;
    }
}
