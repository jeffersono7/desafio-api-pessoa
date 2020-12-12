package br.com.fcamara.pessoa.api.config;

import br.com.fcamara.pessoa.core.exception.BusinessException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;

@ControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .body(buildMessage(ex));
    }

    private Message buildMessage(BusinessException ex) {
        return Message
                .builder()
                .message(ex.getMensagem().getDescricao())
                .build();
    }

    @Getter
    @Builder
    public static class Message implements Serializable {
        public static final long serialVersionUID = -1958677881246895321L;

        private final String message;
    }
}
