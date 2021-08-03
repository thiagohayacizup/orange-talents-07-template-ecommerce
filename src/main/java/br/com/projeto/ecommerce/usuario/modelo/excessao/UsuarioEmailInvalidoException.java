package br.com.projeto.ecommerce.usuario.modelo.excessao;

public class UsuarioEmailInvalidoException extends RuntimeException {

    public UsuarioEmailInvalidoException( final String mensagem ){
        super(mensagem);
    }

}
