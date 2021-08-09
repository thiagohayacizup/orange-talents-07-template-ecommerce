package br.com.projeto.ecommerce.pagamento.processamento;

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
public class ProcessamentoPagamentoIntegrationTest {

    private static final String PAYPAL = "/paypal";
    private static final String PAGSEGURO = "/pagseguro";

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
    @DisplayName("Pagseguro sucesso")
    @Sql(scripts = {"dados.sql"})
    void pagseguroSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(PAGSEGURO + "/1")
                                .contentType( MediaType.MULTIPART_FORM_DATA )
                                .header("Authorization", "Bearer " + bearerToken )
                                .param("idPagamento", "456789i8uytgfdcvhjkjv")
                                .param("statusPagseguro", "SUCESSO")
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() );
    }

    @Test
    @DisplayName("Pagseguro transacao de erro salva com sucesso")
    @Sql(scripts = {"dados.sql"})
    void pagseguroErroSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(PAGSEGURO + "/1")
                                .contentType( MediaType.MULTIPART_FORM_DATA )
                                .header("Authorization", "Bearer " + bearerToken )
                                .param("idPagamento", "456789i8uytgfdcvhjkjv")
                                .param("statusPagseguro", "ERRO")
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() );
    }

    @Test
    @DisplayName("Paypal sucesso")
    @Sql(scripts = {"dados.sql"})
    void paypalSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(PAYPAL + "/1")
                                .contentType( MediaType.MULTIPART_FORM_DATA )
                                .header("Authorization", "Bearer " + bearerToken )
                                .param("idPagamento", "456789i8uytgfdcvhjkjv")
                                .param("statusPaypal", "1")
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isOk() );
    }

    @Test
    @DisplayName("Compra nao encontrada")
    void compraNaoEncontrada() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(PAYPAL + "/5")
                                .contentType( MediaType.MULTIPART_FORM_DATA )
                                .header("Authorization", "Bearer " + bearerToken )
                                .param("idPagamento", "456789i8uytgfdcvhjkjv")
                                .param("statusPagseguro", "SUCESSO")
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.jsonPath("$.codigo").value(400) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Compra { 5 } nao encontrada.") );
    }

    @Test
    @DisplayName("Transacao ja processada")
    @Sql(scripts = {"dados.sql"})
    void transacaoJaProcessada() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(PAYPAL + "/1")
                                .contentType( MediaType.MULTIPART_FORM_DATA )
                                .header("Authorization", "Bearer " + bearerToken )
                                .param("idPagamento", "123456")
                                .param("statusPagseguro", "ERRO")
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.jsonPath("$.codigo").value(400) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Transacao ja foi processada") );
    }

    @Test
    @DisplayName("Transacao ja processada sucesso")
    @Sql(scripts = {"dados.sql"})
    void transacaoJaProcessadaSucesso() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(PAGSEGURO + "/2")
                                .contentType( MediaType.MULTIPART_FORM_DATA )
                                .header("Authorization", "Bearer " + bearerToken )
                                .param("idPagamento", "12345678")
                                .param("statusPagseguro", "SUCESSO")
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.jsonPath("$.codigo").value(400) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Transacao ja foi concluida com sucesso") );
    }

}
