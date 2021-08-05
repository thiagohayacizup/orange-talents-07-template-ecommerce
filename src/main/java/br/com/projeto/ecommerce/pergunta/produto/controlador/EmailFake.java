package br.com.projeto.ecommerce.pergunta.produto.controlador;

class EmailFake {

    public static void send(final String paraQuem, final String conteudo, final String de){
        System.out.println("Para: " + paraQuem);
        System.out.println("Conteudo: " + conteudo);
        System.out.println("De: " + de);
    }

}
