package br.com.projeto.ecommerce.security;

import br.com.projeto.ecommerce.usuario.modelo.Usuario;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

class Token {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    public String gerarToken( final Authentication authentication ){
        final Usuario usuario = ( Usuario ) authentication.getPrincipal();
        final Date hoje = new Date();
        return Jwts.builder()
                .setIssuer(
                        new BCryptPasswordEncoder()
                                .encode( usuario.getUsername() + usuario.getPassword() )
                ).setIssuedAt( hoje )
                .setSubject( usuario.getId().toString() )
                .setExpiration( new Date( hoje.getTime() + Long.parseLong(expiration) ) )
                .signWith( SignatureAlgorithm.HS256, secret )
                .compact();
    }

    public TokenIntermediario recuperarToken( final String token ){
        if(token != null && token.startsWith("Bearer ") )
            return new TokenIntermediario( token.substring( 7 ) );
        return new TokenIntermediario("");
    }

    public Long obterConteudo( final String token ){
        return Long.parseLong(
                Jwts.parser()
                        .setSigningKey( secret )
                        .parseClaimsJws( token )
                        .getBody()
                        .getSubject()
        );
    }

    class TokenIntermediario{

        private final String token;

        TokenIntermediario(final String token) {
            this.token = token;
        }

        public void ifValid( final Consumer<String> consumer ){
            try{
                Jwts.parser().setSigningKey( secret ).parseClaimsJws( token );
                consumer.consume( token );
            }catch( final Exception ignored ){}
        }

    }

    @FunctionalInterface
    interface Consumer<T>{
        void consume( final T t );
    }

}
