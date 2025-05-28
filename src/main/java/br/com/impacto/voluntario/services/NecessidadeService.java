package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.CreateNecessidadeDto;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import br.com.impacto.voluntario.enums.StatusEnum;
import br.com.impacto.voluntario.models.Endereco;
import br.com.impacto.voluntario.models.Necessidade;
import br.com.impacto.voluntario.repositories.EnderecoRepository;
import br.com.impacto.voluntario.repositories.NecessidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class NecessidadeService {

    @Autowired
    private NecessidadeRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Necessidade create(CreateNecessidadeDto dto){
        var endereco = enderecoRepository.save(new Endereco(dto.endereco()));
        var necessidade = dtoToEntity(dto);

        necessidade.setEndereco(endereco);
        necessidade.setStatus(StatusEnum.ANDAMENTO);
        necessidade.setDataEnvio(LocalDate.now());
        necessidade.setAtivo(true);

        repository.save(necessidade);

        return necessidade;
    }

    @Transactional(readOnly = true)
    public List<Necessidade> getAll() {
        return repository.findAllByAtivo();
    }

    private Necessidade dtoToEntity(CreateNecessidadeDto dto){
        var necessidade = new Necessidade();

        necessidade.setTitulo(dto.titulo());
        necessidade.setDescricao(dto.descricao());
        necessidade.setPessoasAfetadas(dto.pessoasAfetadas());
        necessidade.setDesastre(dto.desastre());
        necessidade.setUrgencia(dto.urgencia());
        necessidade.setHabilidadesRequeridas(dto.habilidadesRequeridas());

        if (dto.habilidadesRequeridas().contains(HabilidadesEnum.OUTRO))
            necessidade.setHabilidadeOutro(dto.habilidadeOutro());

        return necessidade;
    }
}
