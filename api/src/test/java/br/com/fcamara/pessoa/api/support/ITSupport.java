package br.com.fcamara.pessoa.api.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public interface ITSupport {
    String CPF_GERADO = "49008655080";

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    default <T> T post(String path, Object content, ResultMatcher resultMatcher, Class<T> clazzResponse) {
        return request(path, HttpMethod.POST, content, resultMatcher, clazzResponse);
    }

    default <T> T put(String path, Object content, ResultMatcher resultMatcher, Class<T> clazzResponse) {
        return request(path, HttpMethod.PUT, content, resultMatcher, clazzResponse);
    }

    @SneakyThrows
    default void delete(String path, ResultMatcher resultMatcher) {
        var request = MockMvcRequestBuilders.delete(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        getMockMvc()
                .perform(request)
                .andExpect(resultMatcher);
    }

    default <T> T get(String path, ResultMatcher resultMatcher, Class<T> clazzResponse) {
        return request(path, HttpMethod.GET, null, resultMatcher, clazzResponse);
    }

    @SneakyThrows
    default <T> T request(String path, HttpMethod method, Object content, ResultMatcher resultMatcher, Class<T> clazzResponse) {
        var request = MockMvcRequestBuilders.request(method, path)
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
