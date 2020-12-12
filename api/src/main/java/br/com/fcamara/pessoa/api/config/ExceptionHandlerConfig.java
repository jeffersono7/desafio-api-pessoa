package br.com.fcamara.pessoa.api.config;

import br.com.fcamara.pessoa.core.exception.BusinessException;
import br.com.fcamara.pessoa.core.exception.InternalServerException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;

@ControllerAdvice
@Log4j
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    protected ResponseEntity<Message> handleBusinessException(BusinessException ex, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .body(buildMessage(ex));
    }

    @ExceptionHandler({InternalServerException.class})
    protected ResponseEntity<Message> handleInternalServerException(InternalServerException ex, WebRequest request) {
        logger.error(ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        Message
                                .builder()
                                .message("Houve um erro ao processar requisição!")
                                .build()
                );
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
