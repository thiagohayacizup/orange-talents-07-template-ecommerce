package br.com.projeto.ecommerce.pagamento.processamento.controlador;

import br.com.projeto.ecommerce.evento.Evento;
import br.com.projeto.ecommerce.nota.fiscal.NotaFiscal;
import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;
import br.com.projeto.ecommerce.pagamento.compra.repositorio.CompraRepositorio;
import br.com.projeto.ecommerce.ranking.Ranking;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class ProcessamentoControlador {

    private final CompraRepositorio compraRepositorio;

    private final NotaFiscal notaFiscal;

    private final Ranking ranking;

    ProcessamentoControlador(final CompraRepositorio compraRepositorio, final NotaFiscal notaFiscal, final Ranking ranking) {
        this.compraRepositorio = compraRepositorio;
        this.notaFiscal = notaFiscal;
        this.ranking = ranking;
    }

    @PostMapping("/pagseguro/{id}")
    @ResponseStatus( HttpStatus.OK )
    public void processarPagseguro(@PathVariable("id") final Long idCompra, @Valid final ProcessamentoPagseguroRequisicao processamentoPagseguroRequisicao ){
        final Compra compra = processamentoPagseguroRequisicao.adicionaTransacao(idCompra, compraRepositorio);
        new Evento(notaFiscal, ranking).dispararEventos( compra );
    }

    @PostMapping("/paypal/{id}")
    @ResponseStatus( HttpStatus.OK )
    public void processarPypal(@PathVariable("id") final Long idCompra, @Valid final ProcessamentoPaypalRequisicao processamentoPagseguroRequisicao ){
        final Compra compra = processamentoPagseguroRequisicao.adicionaTransacao(idCompra, compraRepositorio);
        new Evento(notaFiscal, ranking).dispararEventos( compra );
    }

}
