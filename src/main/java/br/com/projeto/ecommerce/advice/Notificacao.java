package br.com.projeto.ecommerce.advice;

import br.com.projeto.ecommerce.categoria.modelo.excessao.CategoriaJaCadastradaException;
import br.com.projeto.ecommerce.categoria.modelo.excessao.CategoriaNaoEncontradaException;
import br.com.projeto.ecommerce.produto.modelo.excessao.NaoPodeAbaterQuantidadeException;
import br.com.projeto.ecommerce.produto.modelo.excessao.ProdutoDeveTerNoMinimoTresCaracteristicasException;
import br.com.projeto.ecommerce.produto.modelo.excessao.ProdutoNaoEncontradoException;
import br.com.projeto.ecommerce.usuario.modelo.excessao.NaoEDonoException;
import br.com.projeto.ecommerce.usuario.modelo.excessao.UsuarioEmailInvalidoException;
import br.com.projeto.ecommerce.usuario.modelo.excessao.UsuarioJaCadastradoException;
import br.com.projeto.ecommerce.usuario.modelo.excessao.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.Set;

@RestControllerAdvice
class Notificacao {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro dadosInvalidos( final MethodArgumentNotValidException exception ){
        return new RespostaErro(400, exception.getFieldErrors().get(0).getDefaultMessage() );
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro dadosInvalidos( final ConstraintViolationException exception ){
        final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        final Optional<ConstraintViolation<?>> first = violations.stream().findFirst();
        if( first.isEmpty() )
            return new RespostaErro( 400, exception.getMessage());
        return new RespostaErro( 400, first.get().getMessage() );
    }

    @ExceptionHandler({UsuarioEmailInvalidoException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro emailInvalido( final UsuarioEmailInvalidoException exception ){
        return new RespostaErro(400, exception.getMessage());
    }

    @ExceptionHandler({UsuarioJaCadastradoException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro usuarioJaCadastrado( final UsuarioJaCadastradoException exception ){
        return new RespostaErro(400, exception.getMessage());
    }

    @ExceptionHandler({UsuarioNaoEncontradoException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro usuarioNaoEncontrado( final UsuarioNaoEncontradoException exception ){
        return new RespostaErro(400, exception.getMessage());
    }

    @ExceptionHandler({CategoriaJaCadastradaException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro categoriaJaCadastrada( final CategoriaJaCadastradaException exception ){
        return new RespostaErro(400, exception.getMessage());
    }

    @ExceptionHandler({CategoriaNaoEncontradaException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro categoriaNaoEncontrada( final CategoriaNaoEncontradaException exception ){
        return new RespostaErro(400, exception.getMessage());
    }

    @ExceptionHandler({ProdutoDeveTerNoMinimoTresCaracteristicasException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro produtoComCaracteristicasInvalidas( final ProdutoDeveTerNoMinimoTresCaracteristicasException exception ){
        return new RespostaErro(400, exception.getMessage());
    }

    @ExceptionHandler({ProdutoNaoEncontradoException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro produtoNaoEncontrado( final ProdutoNaoEncontradoException exception ){
        return new RespostaErro(400, exception.getMessage());
    }

    @ExceptionHandler({NaoEDonoException.class})
    @ResponseStatus( HttpStatus.FORBIDDEN )
    RespostaErro naoEDono( final NaoEDonoException exception ){
        return new RespostaErro(403, exception.getMessage());
    }

    @ExceptionHandler({NaoPodeAbaterQuantidadeException.class})
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    RespostaErro naoPodeAbatarQuantidade( final NaoPodeAbaterQuantidadeException exception ){
        return new RespostaErro(400, exception.getMessage());
    }

}
