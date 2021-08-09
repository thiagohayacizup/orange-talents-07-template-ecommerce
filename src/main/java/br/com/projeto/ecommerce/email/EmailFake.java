package br.com.projeto.ecommerce.email;

import br.com.projeto.ecommerce.pagamento.compra.modelo.Compra;

public class EmailFake {

    public static void send(final String paraQuem, final String conteudo, final String de){
        System.out.println("Para: " + paraQuem);
        System.out.println("Conteudo: " + conteudo);
        System.out.println("De: " + de);
    }

    public static void processa( final Compra compra ){
        System.out.println("Para: " + compra.getComprador().getUsername());
        System.out.println("Conteudo: ");
        System.out.println(" # " + compra.getComprador().getUsername());
        System.out.println(" # " + compra.getProduto().getNome());
        System.out.println(" # " + compra.getProduto().getValor());
        System.out.println("De: Mercado Livre");
        System.out.println("Compra realizadas com sucesso.");
    }

    public static void processaErro( final Compra compra ){
        System.out.println("Para: " + compra.getComprador().getUsername());
        System.out.println("Conteudo: ");
        System.out.println(" # " + compra.getPagamento() );
        System.out.println("De: Mercado Livre");
        System.out.println("Erro ao realizar pagamento, tente novamente.");
    }

}
