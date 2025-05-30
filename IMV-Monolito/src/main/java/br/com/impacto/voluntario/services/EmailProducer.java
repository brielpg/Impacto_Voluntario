package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.EmailMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void sendEmailMessage(String emailTo, String subject, String text){
        EmailMessageDto emailMessageDto = new EmailMessageDto(emailTo, subject, text);
        rabbitTemplate.convertAndSend("", routingKey, emailMessageDto);
    }
}
