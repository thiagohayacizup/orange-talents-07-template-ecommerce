package br.com.projeto.ecommerce.security;

import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
class SecurityFactory {

    @Bean
    Security security( final AuthenticationManager authenticationManager, final Token token ){
        return new Security( authenticationManager, token );
    }

    @Bean
    Token token(){
        return new Token();
    }

    @Bean
    AutenticacaoServico autenticacaoServico(final UsuarioRepositorio usuarioRepositorio){
        return new AutenticacaoServico( usuarioRepositorio );
    }

}
