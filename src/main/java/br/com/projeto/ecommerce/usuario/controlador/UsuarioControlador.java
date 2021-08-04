package br.com.projeto.ecommerce.usuario.controlador;

import br.com.projeto.ecommerce.security.Security;
import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class UsuarioControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    private final Security security;

    UsuarioControlador(final UsuarioRepositorio usuarioRepositorio, final Security security) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.security = security;
    }

    @PostMapping("/usuario")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody UsuarioResposta cadastrar(@RequestBody @Valid final UsuarioRequisicao usuarioRequisicao ){
        return new UsuarioResposta(
                usuarioRequisicao.cadastrar( usuarioRepositorio ),
                security.autenticar( usuarioRequisicao.toUsernamePasswordAuthenticationToken() )
                        .gerarToken()
        );
    }

}
