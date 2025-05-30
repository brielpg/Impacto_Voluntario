package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.CreateNecessidadeDto;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import br.com.impacto.voluntario.enums.StatusEnum;
import br.com.impacto.voluntario.exceptions.ObjectNotFoundException;
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
    private VoluntarioService voluntarioService;

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

        this.save(necessidade);

        return necessidade;
    }

    @Transactional(readOnly = true)
    public List<Necessidade> getAll() {
        return repository.findAllByAtivo();
    }

    @Transactional
    public void excluir(Long id) {
        var necessidade = this.findById(id);
        necessidade.setAtivo(false);
        necessidade.getVoluntarios().forEach(v -> {
            v.getNecessidades().remove(necessidade);
            voluntarioService.save(v);
        });
        necessidade.getVoluntarios().clear();
        necessidade.setQtdVoluntarios(0);
        this.save(necessidade);
    }

    @Transactional
    public void finalizar(Long id) {
        var necessidade = this.findById(id);
        necessidade.setStatus(StatusEnum.FINALIZADO);
        necessidade.setAtivo(false);
        necessidade.getVoluntarios().forEach(v -> {
            v.setMissoesConcluidas(v.getMissoesConcluidas() + 1);
            v.setVidasImpactadas(v.getVidasImpactadas() + necessidade.getPessoasAfetadas());
            voluntarioService.save(v);
        });
        this.save(necessidade);
    }

    @Transactional
    public Necessidade findById(Long id){
        if (!repository.existsById(id))
            throw new ObjectNotFoundException("Necessidade with id: "+id+" not found");

        return repository.getReferenceById(id);
    }

    @Transactional
    public void save(Necessidade necessidade){
        repository.save(necessidade);
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
