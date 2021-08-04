package br.com.projeto.ecommerce.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class Security {

    public Security(final AuthenticationManager authenticationManager, final Token token) {
        this.authenticationManager = authenticationManager;
        this.token = token;
    }

    private final AuthenticationManager authenticationManager;

    private final Token token;

    private Authentication authentication;

    public Security autenticar(final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken ){
        authentication = authenticationManager.authenticate( usernamePasswordAuthenticationToken );
        return this;
    }

    public String gerarToken(){
        return token.gerarToken( authentication );
    }

}
