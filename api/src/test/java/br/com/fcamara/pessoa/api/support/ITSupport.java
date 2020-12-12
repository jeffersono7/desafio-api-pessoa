package br.com.fcamara.pessoa.api.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public interface ITSupport {
    String CPF_GERADO = "49008655080";

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    @SneakyThrows
    default <T> T post(String path, Object content, ResultMatcher resultMatcher, Class<T> clazzResponse) {
        var request = MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        if (content != null) {
            request = request.content(getObjectMapper().writeValueAsString(content));
        }

        var response = getMockMvc()
                .perform(request)
                .andExpect(resultMatcher)
                .andReturn()
                .getResponse()
                .getContentAsString();

        if (clazzResponse != String.class) {
            return getObjectMapper().readValue(response, clazzResponse);
        }
        return (T) response;
    }
}
