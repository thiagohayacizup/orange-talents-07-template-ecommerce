package br.com.projeto.ecommerce.pagamento.compra;

import br.com.projeto.ecommerce.Autenticacao;
import br.com.projeto.ecommerce.MockErro;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
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
class CompraIntegrationTest {

    private static final String COMPRA_ENDPOINT = "/compra";

    @Autowired
    private MockMvc mockMvc;

    private String bearerToken;

    @BeforeEach
    void onSetUp() throws Exception {
        System.out.println("before");
        this.bearerToken = Autenticacao.autenticar(
                mockMvc,
                "/usuario",
                "{\"email\":\"marcos.silveira@email.com\",\"senha\":\"123456\"}"
        );
    }

    @Test
    @DisplayName("Compra cadastrada com sucesso pagseguro")
    @Sql(scripts = {"inserir-dados.sql"})
    void compraCadastradaPagseguroComSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(COMPRA_ENDPOINT)
                                .contentType( MediaType.APPLICATION_JSON )
                                .header("Authorization", "Bearer " + bearerToken )
                                .content(
                                        Files.readString(
                                                ResourceUtils
                                                        .getFile("classpath:br/com/projeto/ecommerce/pagamento/pagamento-pagseguro-sucesso.json")
                                                        .toPath()
                                        )
                                )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isFound() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.linkPagamento").value("pagseguro.com?returnId=1&redirectUrl=/pagseguro") );
    }

    @Test
    @DisplayName("Compra cadastrada com sucesso paypal")
    @Sql(scripts = {"inserir-dados.sql"})
    void compraCadastradaPaypalComSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(COMPRA_ENDPOINT)
                                .contentType( MediaType.APPLICATION_JSON )
                                .header("Authorization", "Bearer " + bearerToken )
                                .content(
                                        Files.readString(
                                                ResourceUtils
                                                        .getFile("classpath:br/com/projeto/ecommerce/pagamento/pagamento-paypal-sucesso.json")
                                                        .toPath()
                                        )
                                )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isFound() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.linkPagamento").value("paypal.com?buyerId=1&redirectUrl=/paypal") );
    }

    @Test
    @DisplayName("Comprador nao encontrado")
    @Sql(scripts = {"inserir-dados.sql"})
    void compradorNaoEncontrado() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/pagamento/comprador-nao-encontrado.json")
                        .toPath()
        );
        MockErro.mockBadRequestToken(mockMvc, request, COMPRA_ENDPOINT, bearerToken);
    }

    @Test
    @DisplayName("Produto nao encontrado")
    void produtoNaoEncontrado() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/pagamento/produto-nao-encontrado.json")
                        .toPath()
        );
        MockErro.mockBadRequestToken(mockMvc, request, COMPRA_ENDPOINT, bearerToken);
    }

    @Test
    @DisplayName("Quantidade produto nao pode ser abatido")
    @Sql(scripts = {"inserir-dados.sql"})
    void quantidadeProdutoNaoPodeSerAbatido() throws Exception {
        final String request = Files.readString(
                ResourceUtils
                        .getFile("classpath:br/com/projeto/ecommerce/pagamento/quantidade-nao-abatida.json")
                        .toPath()
        );
        MockErro.mockBadRequestToken(mockMvc, request, COMPRA_ENDPOINT, bearerToken);
    }

}
