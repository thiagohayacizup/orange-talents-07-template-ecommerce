package br.com.projeto.ecommerce;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class Autenticacao {

    public static String autenticar(final MockMvc mockMvc, final String endpoint, final String content) throws Exception {
        return JsonPath.read(mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(endpoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(), "$.token");
    }

}
