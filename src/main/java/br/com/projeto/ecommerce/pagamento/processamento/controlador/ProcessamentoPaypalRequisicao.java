package br.com.projeto.ecommerce.pagamento.processamento.controlador;

import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;
import br.com.projeto.ecommerce.pagamento.compra.repositorio.CompraRepositorio;
import br.com.projeto.ecommerce.pagamento.processamento.modelo.StatusTransacao;
import br.com.projeto.ecommerce.pagamento.processamento.modelo.Transacao;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

class ProcessamentoPaypalRequisicao {

    @NotBlank
    private final String idPagamento;

    @Min(0)
    @Max(1)
    private final int statusPaypal;

    ProcessamentoPaypalRequisicao(final String idPagamento, final int statusPaypal) {
        this.idPagamento = idPagamento;
        this.statusPaypal = statusPaypal;
    }


    void adicionaTransacao(final Long idCompra,final CompraRepositorio compraRepositorio) {
        Compra
                .buscarPorId(idCompra, compraRepositorio)
                .adicionarTransacao(
                        new Transacao(StatusTransacao.obterStatus(statusPaypal), idPagamento ),
                        compraRepositorio
                );
    }

}
