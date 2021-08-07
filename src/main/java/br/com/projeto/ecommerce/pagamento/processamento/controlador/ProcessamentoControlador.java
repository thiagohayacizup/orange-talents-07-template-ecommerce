package br.com.projeto.ecommerce.pagamento.processamento.controlador;

import br.com.projeto.ecommerce.pagamento.compra.repositorio.CompraRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class ProcessamentoControlador {

    private final CompraRepositorio compraRepositorio;

    ProcessamentoControlador(final CompraRepositorio compraRepositorio) {
        this.compraRepositorio = compraRepositorio;
    }

    @PostMapping("/pagseguro/{id}")
    @ResponseStatus( HttpStatus.OK )
    public void processarPagseguro(@PathVariable("id") final Long idCompra, @Valid final ProcessamentoPagseguroRequisicao processamentoPagseguroRequisicao ){
        processamentoPagseguroRequisicao.adicionaTransacao( idCompra, compraRepositorio );
    }

    @PostMapping("/paypal/{id}")
    @ResponseStatus( HttpStatus.OK )
    public void processarPypal(@PathVariable("id") final Long idCompra, @Valid final ProcessamentoPaypalRequisicao processamentoPagseguroRequisicao ){
        processamentoPagseguroRequisicao.adicionaTransacao( idCompra, compraRepositorio );
    }

}
