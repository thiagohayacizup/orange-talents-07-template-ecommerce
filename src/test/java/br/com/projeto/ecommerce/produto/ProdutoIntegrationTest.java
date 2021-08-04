package br.com.projeto.ecommerce.produto;

import br.com.projeto.ecommerce.Autenticacao;
import br.com.projeto.ecommerce.MockErro;
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
class ProdutoIntegrationTest {

    private static final String PRODUTO_ENDPOINT = "/produto";

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
    @DisplayName("Produto cadastrado sucesso")
    @Sql(statements = "INSERT INTO Categoria(nome) VALUES('Celulares')")
    void produtoCadastradoSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(PRODUTO_ENDPOINT)
                                .contentType( MediaType.APPLICATION_JSON )
                                .header("Authorization", "Bearer " + bearerToken )
                                .content(
                                        Files.readString(
                                                ResourceUtils
                                                        .getFile("classpath:br/com/projeto/ecommerce/produto/produto-cadastrado-sucesso.json")
                                                        .toPath()
                                        )
                                )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.id").value(1L) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.nome").value("Smartphone Motorola") );
    }

    @Test
    @DisplayName("Produto deve ter pelo menos tres caracteristicas")
    @Sql(statements = "INSERT INTO Categoria(nome) VALUES('Celulares')")
    void produtoMinimoTresCaracteristicas() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/produto/produto-tres-caracteristicas.json")
                        .toPath()
        );
        MockErro.mockBadRequestToken(mockMvc, request, PRODUTO_ENDPOINT, bearerToken);
    }

    @Test
    @DisplayName("Categoria nao encontrada")
    void categoriaNaoEncontrada() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/produto/produto-tres-caracteristicas.json")
                        .toPath()
        );
        MockErro.mockBadRequestToken(mockMvc, request, PRODUTO_ENDPOINT, bearerToken);
    }

    @Test
    @DisplayName("Descricao com mais de 1000 caracteres")
    @Sql(statements = "INSERT INTO Categoria(nome) VALUES('Celulares')")
    void descricaoComMais1000Caracteres() throws Exception{
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/produto/descricao-1001.json")
                        .toPath()
        );
        MockErro.mockBadRequestToken(mockMvc, request, PRODUTO_ENDPOINT, bearerToken);
    }

}
