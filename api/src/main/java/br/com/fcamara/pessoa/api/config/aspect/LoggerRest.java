package br.com.fcamara.pessoa.api.config.aspect;

import java.lang.annotation.*;

/**
 * Anotação para habilitar o log dos métodos invocados pela api.
 * Deve ser usado em rest controller.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LoggerRest {
}
