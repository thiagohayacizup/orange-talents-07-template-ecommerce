package br.com.projeto.ecommerce.usuario.modelo.excessao;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException( final String mensagem ){
        super( mensagem );
    }

}
