package br.com.projeto.ecommerce.usuario.controlador;

import br.com.projeto.ecommerce.usuario.modelo.Usuario;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

class UsuarioRequisicao {

    @NotBlank
    private final String email;

    @NotBlank
    @Size( min =  6 )
    private final String senha;

    UsuarioRequisicao(final String email, final String senha) {
        this.email = email;
        this.senha = senha;
    }

    UsuarioResposta cadastrar(final UsuarioRepositorio usuarioRepositorio ){
        return new UsuarioResposta(
                new Usuario( email, senha )
                        .cadastrar( usuarioRepositorio )
        );
    }

}
