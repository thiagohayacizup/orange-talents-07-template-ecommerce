package br.com.projeto.ecommerce.usuario.modelo.excessao;

public class UsuarioJaCadastradoException extends RuntimeException {

    public UsuarioJaCadastradoException( final String mensagem ){
        super( mensagem );
    }

}
