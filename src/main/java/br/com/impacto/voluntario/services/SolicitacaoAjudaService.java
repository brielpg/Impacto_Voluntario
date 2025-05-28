package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.CreateSolicitacaoAjudaDto;
import br.com.impacto.voluntario.enums.AjudaRequeridaEnum;
import br.com.impacto.voluntario.enums.StatusEnum;
import br.com.impacto.voluntario.exceptions.InvalidDateException;
import br.com.impacto.voluntario.models.Endereco;
import br.com.impacto.voluntario.models.SolicitacaoAjuda;
import br.com.impacto.voluntario.repositories.EnderecoRepository;
import br.com.impacto.voluntario.repositories.SolicitacaoAjudaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SolicitacaoAjudaService {

    @Autowired
    private SolicitacaoAjudaRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public SolicitacaoAjuda create(CreateSolicitacaoAjudaDto dto){
        if (dto.dataAcontecimento().isAfter(LocalDate.now())) throw new InvalidDateException();

        var endereco = enderecoRepository.save(new Endereco(dto.endereco()));
        var solicitacao = dtoToEntity(dto);

        solicitacao.setEndereco(endereco);
        solicitacao.setAtivo(true);
        solicitacao.setDataEnvio(LocalDate.now());
        solicitacao.setStatus(StatusEnum.ANALISE);

        repository.save(solicitacao);

        return solicitacao;
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoAjuda> getAll() {
        return repository.findAllByAtivo();
    }

    private SolicitacaoAjuda dtoToEntity(CreateSolicitacaoAjudaDto dto){
        var solicitacao = new SolicitacaoAjuda();

        solicitacao.setNomeSolicitante(dto.nomeSolicitante());
        solicitacao.setEmailSolicitante(dto.emailSolicitante());
        solicitacao.setTelefoneSolicitante(dto.telefoneSolicitante());
        solicitacao.setDescricao(dto.descricao());
        solicitacao.setDataAcontecimento(dto.dataAcontecimento());
        solicitacao.setPessoasAfetadas(dto.pessoasAfetadas());
        solicitacao.setDesastre(dto.desastre());
        solicitacao.setUrgencia(dto.urgencia());
        solicitacao.setAjudaRequerida(dto.ajudaRequerida());

        if (dto.ajudaRequerida().contains(AjudaRequeridaEnum.OUTROS))
            solicitacao.setOutros(dto.outros());

        return solicitacao;
    }
}
