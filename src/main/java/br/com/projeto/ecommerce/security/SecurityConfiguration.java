package br.com.projeto.ecommerce.security;

import br.com.projeto.ecommerce.usuario.repositorio.UsuarioRepositorio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AutenticacaoServico autenticacaoServico;

    private final Token token;

    private final UsuarioRepositorio usuarioRepositorio;

    SecurityConfiguration(final AutenticacaoServico autenticacaoServico, final Token token, final UsuarioRepositorio usuarioRepositorio) {
        this.autenticacaoServico = autenticacaoServico;
        this.token = token;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( HttpMethod.POST, "/usuario" ).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS )
                .and().addFilterBefore(
                        new AutenticacaoInterceptador(token, usuarioRepositorio),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( autenticacaoServico ).passwordEncoder( new BCryptPasswordEncoder() );
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
