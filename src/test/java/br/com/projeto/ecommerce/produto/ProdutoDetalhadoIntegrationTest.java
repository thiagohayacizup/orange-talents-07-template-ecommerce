package br.com.projeto.ecommerce.produto;

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

@ActiveProfiles( value = "test" )
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext( classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD )
class ProdutoDetalhadoIntegrationTest {

    private static final String PRODUTO_DETALHADO_ENDPOINT = "/produto/1";

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
    @DisplayName("Nao tem produto cadastrado")
    void naoTemProdutoCadastrado() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(PRODUTO_DETALHADO_ENDPOINT)
                                .header("Authorization", "Bearer " + bearerToken )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.codigo").value(400) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Produto { 1 } nao encontrado.") );
    }

    @Test
    @DisplayName("Produto cadastrado sucesso")
    @Sql(scripts = {"inserir-produto.sql"})
    void produtoCadastradoSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(PRODUTO_DETALHADO_ENDPOINT)
                                .header("Authorization", "Bearer " + bearerToken )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.nome").value("Smartphone Motorola") )
                .andExpect( MockMvcResultMatchers.jsonPath("$.valor").value(2000.00) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.imagens").isEmpty() )
                .andExpect( MockMvcResultMatchers.jsonPath("$.caracteristicas").isArray() )
                .andExpect( MockMvcResultMatchers.jsonPath("$.caracteristicas[0].nome").value("Bateria") )
                .andExpect( MockMvcResultMatchers.jsonPath("$.caracteristicas[0].descricao").value("4000ma") )
                .andExpect( MockMvcResultMatchers.jsonPath("$.descricao").value("Smartphone samsung") )
                .andExpect( MockMvcResultMatchers.jsonPath("$.perguntas").isEmpty() )
                .andExpect( MockMvcResultMatchers.jsonPath("$.opinioes").isArray() )
                .andExpect( MockMvcResultMatchers.jsonPath("$.opinioes[0]").value("opiniao") )
                .andExpect( MockMvcResultMatchers.jsonPath("$.media").value(5.0) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.totalNotasProduto").value(1) );
    }

}
