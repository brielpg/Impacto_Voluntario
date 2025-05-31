package br.com.impacto.voluntario.config;

import br.com.impacto.voluntario.services.EmailProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class MockConfig {
    @Bean
    public EmailProducer emailProducer() {
        return mock(EmailProducer.class);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return mock(RabbitTemplate.class);
    }
}
