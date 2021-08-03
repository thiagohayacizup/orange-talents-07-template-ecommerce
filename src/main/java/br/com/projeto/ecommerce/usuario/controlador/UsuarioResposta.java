package br.com.projeto.ecommerce.usuario.controlador;

import br.com.projeto.ecommerce.usuario.modelo.Usuario;

class UsuarioResposta {

    private final Usuario usuario;

    UsuarioResposta(final Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId(){
        return usuario.getId();
    }

}
