package br.com.impacto.voluntario.microservice.consumers;

import br.com.impacto.voluntario.microservice.dtos.ConsumeDto;
import br.com.impacto.voluntario.microservice.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload ConsumeDto data){
        log.info("Mensagem recebida: " + data.toString());
        emailService.sendEmail(data);
    }
}
