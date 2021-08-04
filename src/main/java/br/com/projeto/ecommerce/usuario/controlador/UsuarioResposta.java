package br.com.projeto.ecommerce.usuario.controlador;

import br.com.projeto.ecommerce.usuario.modelo.Usuario;

class UsuarioResposta {

    private final Usuario usuario;

    private final String token;

    UsuarioResposta(final Usuario usuario, final String token) {
        this.usuario = usuario;
        this.token = token;
    }

    public Long getId(){
        return usuario.getId();
    }

    public String getToken(){ return token; }

    public String getTipoToken(){ return "Bearer Token"; }

}
