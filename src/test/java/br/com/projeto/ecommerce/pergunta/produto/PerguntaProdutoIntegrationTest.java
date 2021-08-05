package br.com.projeto.ecommerce.pergunta.produto;

import br.com.projeto.ecommerce.Autenticacao;
import org.junit.jupiter.api.BeforeEach;
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

import java.nio.file.Files;

@ActiveProfiles( value = "test" )
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext( classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD )
class PerguntaProdutoIntegrationTest {

    private static final String PERGUNTA_PRODUTO_ENDPOINT = "/pergunta-produto";

    @Autowired
    private MockMvc mockMvc;

    private String bearerToken;

    @BeforeEach
    void setUp() throws Exception {
        bearerToken = Autenticacao.autenticar(
                mockMvc,
                "/usuario",
                "{\"email\":\"marcos.silveira@email.com\",\"senha\":\"123456\"}"
        );
    }

    @Test
    @DisplayName("Pergunta cadastrada sucesso")
    @Sql(scripts = {"inserir-dados.sql"})
    void perguntaCadastradaSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(PERGUNTA_PRODUTO_ENDPOINT)
                                .contentType( MediaType.APPLICATION_JSON )
                                .header("Authorization", "Bearer " + bearerToken )
                                .content(
                                        Files.readString(
                                                ResourceUtils
                                                        .getFile("classpath:br/com/projeto/ecommerce/pergunta/pergunta-cadastrada-sucesso.json")
                                                        .toPath()
                                        )
                                )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.id").value(1L) );
    }

}