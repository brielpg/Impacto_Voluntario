package br.com.impacto.voluntario.microservice.dtos;

public record ConsumeDto(
        String emailTo,
        String subject,
        String text
) {
}
