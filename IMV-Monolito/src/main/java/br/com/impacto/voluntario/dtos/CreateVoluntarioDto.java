package br.com.impacto.voluntario.dtos;

import br.com.impacto.voluntario.dtos.DtoEndereco;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record CreateVoluntarioDto(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "CPF inválido, deve conter 11 dígitos")
        String cpf,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "Senha é obrigatória")
        String senha,

        @NotNull(message = "Data de nascimento é obrigatória")
        LocalDate dataNascimento,

        @NotNull(message = "Endereço é obrigatório")
        @Valid
        DtoEndereco endereco,

        @NotEmpty(message = "Habilidades sao obrigatórias")
        List<HabilidadesEnum> habilidades,

        String habilidadeOutro
) {
}
