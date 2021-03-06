package br.com.fcamara.pessoa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableAspectJAutoProxy
@EnableJpaAuditing
@EntityScan(basePackages = {"br.com.fcamara.pessoa"})
@SpringBootApplication(scanBasePackages = "br.com.fcamara.pessoa")
public class PessoaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PessoaApplication.class, args);
    }
}
