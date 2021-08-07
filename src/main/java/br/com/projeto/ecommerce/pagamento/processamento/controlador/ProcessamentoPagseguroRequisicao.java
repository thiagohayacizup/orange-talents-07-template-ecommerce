package br.com.projeto.ecommerce.pagamento.processamento.controlador;

import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;
import br.com.projeto.ecommerce.pagamento.compra.repositorio.CompraRepositorio;
import br.com.projeto.ecommerce.pagamento.processamento.modelo.Transacao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

class ProcessamentoPagseguroRequisicao {

    @NotBlank
    private String idPagamento;

    @NotNull
    private StatusPagseguro statusPagseguro;

    public void setIdPagamento(final String idPagamento) {
        this.idPagamento = idPagamento;
    }

    public void setStatusPagseguro(final StatusPagseguro statusPagseguro) {
        this.statusPagseguro = statusPagseguro;
    }

    void adicionaTransacao(final Long idCompra, final CompraRepositorio compraRepositorio) {
        Compra
                .buscarPorId(idCompra, compraRepositorio)
                .adicionarTransacao(
                        new Transacao(statusPagseguro.normaliza(), idPagamento ),
                        compraRepositorio
                );
    }

}
