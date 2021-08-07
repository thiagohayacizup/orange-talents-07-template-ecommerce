package br.com.projeto.ecommerce.pagamento.processamento.modelo;

public enum StatusTransacao {

    SUCESSO,
    ERRO;

    public static StatusTransacao obterStatus(final int statusPaypal) {
        if( statusPaypal == 1 )
            return SUCESSO;
        return ERRO;
    }

}
