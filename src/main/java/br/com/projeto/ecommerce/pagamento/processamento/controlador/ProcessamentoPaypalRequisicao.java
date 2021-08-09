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
    private String idPagamento;

    @Min(0)
    @Max(1)
    private int statusPaypal;

    public void setIdPagamento(final String idPagamento) {
        this.idPagamento = idPagamento;
    }

    public void setStatusPaypal(final int statusPaypal) {
        this.statusPaypal = statusPaypal;
    }

    Compra adicionaTransacao(final Long idCompra, final CompraRepositorio compraRepositorio) {
        return Compra
                .buscarPorId(idCompra, compraRepositorio)
                .adicionarTransacao(
                        new Transacao(StatusTransacao.obterStatus(statusPaypal), idPagamento ),
                        compraRepositorio
                );
    }

}
