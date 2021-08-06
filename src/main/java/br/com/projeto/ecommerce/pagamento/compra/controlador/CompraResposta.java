package br.com.projeto.ecommerce.pagamento.compra.controlador;

class CompraResposta {

    private final String linkGateway;

    CompraResposta(final String linkGateway) {
        this.linkGateway = linkGateway;
    }

    public String getLinkPagamento(){
        return linkGateway;
    }

}
