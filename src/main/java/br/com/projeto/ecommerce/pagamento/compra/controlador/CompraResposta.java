package br.com.projeto.ecommerce.pagamento.compra.controlador;

import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;

class CompraResposta {

    private final Compra compra;

    CompraResposta(final Compra compra) {
        this.compra = compra;
    }

    public String getLinkPagamento(){
        return compra.getPagamento();
    }

    String emailComprador(){
        return compra.getComprador().getUsername();
    }

    String emailVendedor(){
        return compra.getProduto().getDono().getUsername();
    }

}
