package br.com.projeto.ecommerce.categoria;

import br.com.projeto.ecommerce.MockErro;
import com.jayway.jsonpath.JsonPath;
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
class CategoriaIntegrationTest {

    private static final String CATEGORIA_ENDPOINT = "/categoria";

    @Autowired
    private MockMvc mockMvc;

    private String bearerToken;

    @BeforeEach
    void setUp() throws Exception {
        bearerToken = JsonPath.read(mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/usuario")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\":\"marcos.silveira@email.com\",\"senha\":\"123456\"}")
                ).andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(), "$.token");
    }

    @Test
    @DisplayName("Categoria esta cadastrada com sucesso")
    void categoriaEstaCadastradaSucesso() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(CATEGORIA_ENDPOINT)
                                .contentType( MediaType.APPLICATION_JSON )
                                .header("Authorization", "Bearer " + bearerToken )
                                .content(
                                        Files.readString(
                                                ResourceUtils
                                                        .getFile("classpath:br/com/projeto/ecommerce/categoria/categoria-cadastrada-sucesso.json")
                                                        .toPath()
                                        )
                                )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.nome").value("Software") )
                .andExpect( MockMvcResultMatchers.jsonPath("$.categoriaMae").value("") );
    }

    @Test
    @DisplayName("Categoria ja cadastrada")
    @Sql( scripts = {"categoria-insert.sql"} )
    void categoriaJaCadastrada() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/categoria/categoria-ja-cadastrada.json")
                        .toPath()
        );
        MockErro.mockBadRequestToken(mockMvc, request, CATEGORIA_ENDPOINT, bearerToken);
    }

    @Test
    @DisplayName("Nome invalido")
    void nomeCategoriaInvalido() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/categoria/nome-categoria-invalido.json")
                        .toPath()
        );
        MockErro.mockBadRequestToken(mockMvc, request, CATEGORIA_ENDPOINT, bearerToken);
    }

    @Test
    @DisplayName("Nao tem Permissao")
    void naoTemPermissao() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/categoria/nome-categoria-invalido.json")
                        .toPath()
        );
        MockErro.forbidden(mockMvc, request, CATEGORIA_ENDPOINT);
    }

    @Test
    @DisplayName("Categoria cadastrada com categoria mae sucesso")
    @Sql(statements = "INSERT INTO Categoria(nome) VALUES ('Hardware')")
    void categoriaCadastradaComCategoriaMaeSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(CATEGORIA_ENDPOINT)
                                .contentType( MediaType.APPLICATION_JSON )
                                .header("Authorization", "Bearer " + bearerToken )
                                .content(
                                        Files.readString(
                                                ResourceUtils
                                                        .getFile("classpath:br/com/projeto/ecommerce/categoria/categoria-cadastrada-sucesso-categoria-mae.json")
                                                        .toPath()
                                        )
                                )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.nome").value("Placa Mae") )
                .andExpect( MockMvcResultMatchers.jsonPath("$.categoriaMae").value("Hardware") );
    }

}
