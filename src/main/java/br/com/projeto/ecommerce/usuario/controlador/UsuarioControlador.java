package br.com.projeto.ecommerce.usuario.controlador;

import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class UsuarioControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    UsuarioControlador(final UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @PostMapping("/usuario")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody UsuarioResposta cadastrar(@RequestBody @Valid final UsuarioRequisicao usuarioRequisicao ){
        return usuarioRequisicao.cadastrar( usuarioRepositorio );
    }

}
