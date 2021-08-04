package br.com.projeto.ecommerce.usuario;

import br.com.projeto.ecommerce.MockErro;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.util.Map;

@ActiveProfiles( value = "test" )
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext( classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD )
class UsuarioIntegrationTest {

    private static final String USUARIO_ENDPOINT = "/usuario";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Usuario com formato de email invalido")
    void emailUsuarioInvalido() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/usuario/usuario-email-invalido.json")
                        .toPath()
        );
        MockErro.mockBadRequest(mockMvc, request, USUARIO_ENDPOINT);
    }

    @Test
    @DisplayName("Usuario com senha invalida")
    void senhaInvalida() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/usuario/usuario-senha-invalido.json")
                        .toPath()
        );
        MockErro.mockBadRequest(mockMvc, request, USUARIO_ENDPOINT);
    }

    @Test
    @DisplayName("Usuario com senha invalida vazia")
    void senhaInvalidaVazia() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/usuario/usuario-senha-invalido-vazia.json")
                        .toPath()
        );
        MockErro.mockBadRequest(mockMvc, request, USUARIO_ENDPOINT);
    }

    @Test
    @DisplayName("Usuario ja cadastrado")
    @Sql(scripts = {"usuario-inserir.sql"})
    void usuarioJaCadastrado() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/usuario/usuario-ja-cadastrado.json")
                        .toPath()
        );
        MockErro.mockBadRequest(mockMvc, request, USUARIO_ENDPOINT);
    }

    @Test
    @DisplayName("Usuario esta cadastrado sucesso")
    void usuarioEstaCadastradoSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(USUARIO_ENDPOINT)
                                .contentType( MediaType.APPLICATION_JSON )
                                .content(
                                        Files.readString(
                                                ResourceUtils
                                                        .getFile("classpath:br/com/projeto/ecommerce/usuario/usuario-cadastrado-sucesso.json")
                                                        .toPath()
                                        )
                                )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.id").value(1L) );
    }

}
