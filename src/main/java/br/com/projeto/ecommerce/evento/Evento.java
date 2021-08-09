package br.com.projeto.ecommerce.evento;

import br.com.projeto.ecommerce.email.EmailFake;
import br.com.projeto.ecommerce.nota.fiscal.NotaFiscal;
import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;
import br.com.projeto.ecommerce.ranking.Ranking;

public class Evento {

    private final NotaFiscal notaFiscal;

    private final Ranking ranking;

    public Evento(final NotaFiscal notaFiscal, final Ranking ranking) {
        this.notaFiscal = notaFiscal;
        this.ranking = ranking;
    }

    public void dispararEventos(final Compra compra){
        if( compra.transacaoConcluidaCadastrada() ){
            notaFiscal.processa( compra );
            ranking.processa( compra );
            EmailFake.processa( compra );
        }else {
            EmailFake.processaErro( compra );
        }
    }

}
