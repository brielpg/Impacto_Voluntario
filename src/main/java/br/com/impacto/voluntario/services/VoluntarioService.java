package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.CreateVoluntarioDto;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import br.com.impacto.voluntario.enums.Roles;
import br.com.impacto.voluntario.models.Endereco;
import br.com.impacto.voluntario.models.Voluntario;
import br.com.impacto.voluntario.repositories.EnderecoRepository;
import br.com.impacto.voluntario.repositories.VoluntarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
public class VoluntarioService implements UserDetailsService {

    @Autowired
    private VoluntarioRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Voluntario create(CreateVoluntarioDto dto){
        if (repository.existsByCpf(dto.cpf())) throw new RuntimeException("Cpf already registered");
        if (repository.existsByEmail(dto.email())) throw new RuntimeException("Email already registered");
        if (dto.habilidades().isEmpty()) throw new RuntimeException("Habilidades cannot be empty");
        if (dto.dataNascimento().isAfter(LocalDate.now())) throw new RuntimeException("Date cannot be higher than now");
        if (Period.between(dto.dataNascimento(), LocalDate.now()).getYears() < 18)
            throw new RuntimeException("Voluntario must be at least 18 years old");

        var endereco = enderecoRepository.save(new Endereco(dto.endereco()));
        var voluntario = dtoToEntity(dto, endereco);

        voluntario.setAtivo(true);
        voluntario.setDataCadastro(LocalDate.now());
        voluntario.setMissoesConcluidas(0);
        voluntario.setRole(Roles.USER);

        repository.save(voluntario);

        return voluntario;
    }

    private Voluntario dtoToEntity(CreateVoluntarioDto dto, Endereco endereco){
        var voluntario = new Voluntario();

        voluntario.setNome(dto.nome());
        voluntario.setEmail(dto.email());
        voluntario.setCpf(dto.cpf());
        voluntario.setTelefone(dto.telefone());
        voluntario.setSenha(passwordEncoder.encode(dto.senha()));
        voluntario.setDataNascimento(dto.dataNascimento());
        voluntario.setEndereco(endereco);
        voluntario.setHabilidades(dto.habilidades());

        if (dto.habilidades().contains(HabilidadesEnum.OUTRO))
            voluntario.setHabilidadeOutro(dto.habilidadeOutro());

        return voluntario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Voluntario not found with email: " + username));
    }
}
