package br.com.projeto.ecommerce.security;

import br.com.projeto.ecommerce.usuario.modelo.Usuario;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class AutenticacaoServico implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    AutenticacaoServico(final UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
        return Usuario.buscarPorEmail( s, usuarioRepositorio );
    }
}
