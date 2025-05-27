package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.CreateVoluntarioDto;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import br.com.impacto.voluntario.models.Endereco;
import br.com.impacto.voluntario.models.Voluntario;
import br.com.impacto.voluntario.repositories.VoluntarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
public class VoluntarioService {

    @Autowired
    private VoluntarioRepository repository;

    @Transactional
    public Voluntario create(CreateVoluntarioDto dto){
        if (repository.existsByCpf(dto.cpf())) throw new RuntimeException("Cpf already registered");
        if (repository.existsByEmail(dto.email())) throw new RuntimeException("Email already registered");
        if (dto.habilidades().isEmpty()) throw new RuntimeException("Habilidades cannot be empty");
        if (dto.dataNascimento().isAfter(LocalDate.now())) throw new RuntimeException("Date cannot be higher than now");
        if (Period.between(dto.dataNascimento(), LocalDate.now()).getYears() < 18)
            throw new RuntimeException("Voluntario must be at least 18 years old");

        var endereco = new Endereco(dto.endereco());
        var voluntario = dtoToEntity(dto, endereco);

        voluntario.setAtivo(true);
        voluntario.setDataCadastro(LocalDate.now());
        voluntario.setNecessidadesConcluidas(0L);

        repository.save(voluntario);

        return voluntario;
    }

    private Voluntario dtoToEntity(CreateVoluntarioDto dto, Endereco endereco){
        var voluntario = new Voluntario();

        voluntario.setNome(dto.nome());
        voluntario.setEmail(dto.email());
        voluntario.setCpf(dto.cpf());
        voluntario.setTelefone(dto.telefone());
        voluntario.setSenha(dto.senha());
        voluntario.setDataNascimento(dto.dataNascimento());
        voluntario.setEndereco(endereco);
        voluntario.setHabilidades(dto.habilidades());

        if (dto.habilidades().contains(HabilidadesEnum.OUTRO))
            voluntario.setHabilidadeOutro(dto.habilidadeOutro());

        return voluntario;
    }
}
