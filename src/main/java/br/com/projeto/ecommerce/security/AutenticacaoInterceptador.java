package br.com.projeto.ecommerce.security;

import br.com.projeto.ecommerce.usuario.modelo.Usuario;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class AutenticacaoInterceptador extends OncePerRequestFilter {

    AutenticacaoInterceptador(final Token token, final UsuarioRepositorio usuarioRepositorio) {
        this.token = token;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    private final Token token;

    private final UsuarioRepositorio usuarioRepositorio;

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final FilterChain filterChain) throws ServletException, IOException {
        token.recuperarToken( httpServletRequest.getHeader("Authorization") )
                .ifValid( this::autenticar );
        filterChain.doFilter( httpServletRequest, httpServletResponse );
    }

    private void autenticar( final String token ){
        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        Usuario.autenticar(
                                this.token.obterConteudo( token ),
                                usuarioRepositorio
                        )
                );
    }

}
