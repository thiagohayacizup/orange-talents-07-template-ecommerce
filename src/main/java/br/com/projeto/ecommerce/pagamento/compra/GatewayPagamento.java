package br.com.projeto.ecommerce.pagamento.compra;

public enum GatewayPagamento {

    PAGSEGURO{
        @Override
        public String redirect(final Long idCompra) {
            return "pagseguro.com?returnId=" + idCompra.toString() + "&redirectUrl=/pagseguro/" + idCompra;
        }
    },
    PAYPAL{
        @Override
        public String redirect(final Long idCompra) {
            return "paypal.com?buyerId=" + idCompra.toString() + "&redirectUrl=/paypal/" + idCompra;
        }
    };

    public abstract String redirect( final Long idCompra );

}
