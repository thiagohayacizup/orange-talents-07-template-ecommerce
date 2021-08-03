package br.com.projeto.ecommerce;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class MockErro {

    public static void mockBadRequest(final MockMvc mockMvc, final String request, final String endpoint ) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post( endpoint )
                                .contentType( MediaType.APPLICATION_JSON )
                                .content( request )
                ).andDo( MockMvcResultHandlers.print() )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.codigo").value(400));
    }

}
