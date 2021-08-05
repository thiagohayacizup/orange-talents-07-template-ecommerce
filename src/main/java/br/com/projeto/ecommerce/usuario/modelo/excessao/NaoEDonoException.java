package br.com.projeto.ecommerce.usuario.modelo.excessao;

public class NaoEDonoException extends RuntimeException {

    public NaoEDonoException( final String mensagem ){
        super(mensagem);
    }

}
