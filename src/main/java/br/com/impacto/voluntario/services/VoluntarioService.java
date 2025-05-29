package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.CreateVoluntarioDto;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import br.com.impacto.voluntario.enums.Roles;
import br.com.impacto.voluntario.exceptions.ConflictException;
import br.com.impacto.voluntario.exceptions.InvalidDateException;
import br.com.impacto.voluntario.exceptions.ObjectNotFoundException;
import br.com.impacto.voluntario.models.Endereco;
import br.com.impacto.voluntario.models.Voluntario;
import br.com.impacto.voluntario.repositories.EnderecoRepository;
import br.com.impacto.voluntario.repositories.VoluntarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    private NecessidadeService necessidadeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Voluntario create(CreateVoluntarioDto dto){
        if (repository.existsByCpf(dto.cpf())) throw new ConflictException("Cpf already registered");
        if (repository.existsByEmail(dto.email())) throw new ConflictException("Email already registered");
        if (dto.dataNascimento().isAfter(LocalDate.now())) throw new InvalidDateException();
        if (Period.between(dto.dataNascimento(), LocalDate.now()).getYears() < 18)
            throw new InvalidDateException("Voluntario must be at least 18 years old");

        var endereco = enderecoRepository.save(new Endereco(dto.endereco()));
        var voluntario = dtoToEntity(dto);

        voluntario.setEndereco(endereco);
        voluntario.setAtivo(true);
        voluntario.setDataCadastro(LocalDate.now());
        voluntario.setMissoesConcluidas(0);
        voluntario.setVidasImpactadas(0);
        voluntario.setRole(Roles.USER);

        this.save(voluntario);

        return voluntario;
    }

    @Transactional
    public void queroAjudar(String email, Long necessidadeId) {
        var voluntario = this.getByEmail(email);
        var necessidade = necessidadeService.findById(necessidadeId);

        if (!voluntario.getNecessidades().contains(necessidade)) {
            voluntario.getNecessidades().add(necessidade);
            necessidade.getVoluntarios().add(voluntario);
            necessidade.setQtdVoluntarios(necessidade.getVoluntarios().size());
            this.save(voluntario);
            necessidadeService.save(necessidade);
        }
    }

    @Transactional(readOnly = true)
    public Voluntario getByEmail(String email) {
        var voluntario = repository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Voluntario with email \""+email+"\" not found"));

        return (Voluntario) voluntario;
    }

    @Transactional
    public void save(Voluntario voluntario){
        repository.save(voluntario);
    }

    private Voluntario dtoToEntity(CreateVoluntarioDto dto){
        var voluntario = new Voluntario();

        voluntario.setNome(dto.nome());
        voluntario.setEmail(dto.email());
        voluntario.setCpf(dto.cpf());
        voluntario.setTelefone(dto.telefone());
        voluntario.setSenha(passwordEncoder.encode(dto.senha()));
        voluntario.setDataNascimento(dto.dataNascimento());
        voluntario.setHabilidades(dto.habilidades());

        if (dto.habilidades().contains(HabilidadesEnum.OUTRO))
            voluntario.setHabilidadeOutro(dto.habilidadeOutro());

        return voluntario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new ObjectNotFoundException("Voluntario with email \""+username+"\" not found"));
    }
}
