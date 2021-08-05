package br.com.projeto.ecommerce.produto;

import br.com.projeto.ecommerce.Autenticacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

@ActiveProfiles( value = "test" )
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext( classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD )
class ImagensIntegrationTest {

    private static final String PRODUTO_IMAGENS_ENDPOINT = "/produto/1/imagens";

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
    @DisplayName("Imagens cadastradas sucesso")
    @Sql(scripts = {"inserir-produto.sql"})
    void imagensCadastradasSucesso() throws Exception{
        final ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .multipart(PRODUTO_IMAGENS_ENDPOINT)
                                .file(
                                        new MockMultipartFile(
                                                "imagens",
                                                new FileInputStream("src/test/resources/br/com/projeto/ecommerce/produto/teste.png")
                                        )
                                )
                                .param("dono", "email@email.com")
                                .contentType( MediaType.MULTIPART_FORM_DATA )
                                .header("Authorization", "Bearer " + bearerToken )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.links[0]").value("https://cloudfake.com.br/") );
    }

    @Test
    @DisplayName("Produto nao encontrado")
    void produtoNaoEncontrado() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .multipart(PRODUTO_IMAGENS_ENDPOINT)
                                .file(
                                        new MockMultipartFile(
                                                "imagens",
                                                new FileInputStream("src/test/resources/br/com/projeto/ecommerce/produto/teste.png")
                                        )
                                )
                                .param("dono", "email@email.com")
                                .contentType( MediaType.MULTIPART_FORM_DATA )
                                .header("Authorization", "Bearer " + bearerToken )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.codigo").value(400) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Produto { 1 } nao encontrado.") );
    }

    @Test
    @DisplayName("Usuario nao e dono do produto")
    @Sql(scripts = {"inserir-produto.sql"})
    void usuarioNaoDonoProduto() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .multipart(PRODUTO_IMAGENS_ENDPOINT)
                                .file(
                                        new MockMultipartFile(
                                                "imagens",
                                                new FileInputStream("src/test/resources/br/com/projeto/ecommerce/produto/teste.png")
                                        )
                                )
                                .param("dono", "marcos.silveira@email.com")
                                .contentType( MediaType.MULTIPART_FORM_DATA )
                                .header("Authorization", "Bearer " + bearerToken )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isForbidden() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.codigo").value(403) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Usuario { marcos.silveira@email.com } nao e dono do produto.") );
    }

}
