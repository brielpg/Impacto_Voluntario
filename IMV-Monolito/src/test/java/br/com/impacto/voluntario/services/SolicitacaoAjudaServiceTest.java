package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.CreateSolicitacaoAjudaDto;
import br.com.impacto.voluntario.dtos.DtoEndereco;
import br.com.impacto.voluntario.enums.AjudaRequeridaEnum;
import br.com.impacto.voluntario.enums.StatusEnum;
import br.com.impacto.voluntario.exceptions.InvalidDateException;
import br.com.impacto.voluntario.exceptions.ObjectNotFoundException;
import br.com.impacto.voluntario.models.Endereco;
import br.com.impacto.voluntario.models.SolicitacaoAjuda;
import br.com.impacto.voluntario.repositories.EnderecoRepository;
import br.com.impacto.voluntario.repositories.SolicitacaoAjudaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SolicitacaoAjudaServiceTest {

    @Mock
    private SolicitacaoAjudaRepository solicitacaoRepository;

    @Mock
    private EmailProducer emailProducer;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private SolicitacaoAjudaService solicitacaoService;

    private CreateSolicitacaoAjudaDto validDto;

    @BeforeEach
    public void setup() {
        var endereco = new DtoEndereco(
                "12345-678",
                "CidadeTeste",
                "ST",
                "BairroTeste",
                123,
                "ComplementoTeste"
        );
        validDto = new CreateSolicitacaoAjudaDto(
                "Requester",
                "requester@example.com",
                "123456789",
                "Description",
                LocalDate.now().minusDays(1),
                10,
                endereco,
                null,
                null,
                List.of(AjudaRequeridaEnum.OUTROS),
                "Other help"
        );
    }

    @Test
    public void testCreate_Success() {
        when(enderecoRepository.save(any(Endereco.class))).thenAnswer(i -> i.getArgument(0));
        when(solicitacaoRepository.save(any(SolicitacaoAjuda.class))).thenAnswer(i -> i.getArgument(0));

        SolicitacaoAjuda result = solicitacaoService.create(validDto);

        assertNotNull(result);
        assertEquals(validDto.nomeSolicitante(), result.getNomeSolicitante());
        assertEquals(StatusEnum.ANALISE, result.getStatus());
        assertTrue(result.getAtivo());
        verify(emailProducer, times(1)).sendEmailMessage(eq(result.getEmailSolicitante()), anyString(), anyString());
        verify(solicitacaoRepository, times(1)).save(any(SolicitacaoAjuda.class));
    }

    @Test
    public void testCreate_ThrowsInvalidDateException_WhenDateInFuture() {
        CreateSolicitacaoAjudaDto dto = new CreateSolicitacaoAjudaDto(
                "Requester",
                "requester@example.com",
                "123456789",
                "Description",
                LocalDate.now().plusDays(1),
                10,
                null,
                null,
                null,
                List.of(),
                null
        );
        assertThrows(InvalidDateException.class, () -> solicitacaoService.create(dto));
    }

    @Test
    public void testGetAll_ReturnsList() {
        when(solicitacaoRepository.findAllByAtivo()).thenReturn(List.of(new SolicitacaoAjuda()));

        List<SolicitacaoAjuda> result = solicitacaoService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(solicitacaoRepository, times(1)).findAllByAtivo();
    }

    @Test
    public void testExcluir_SetsAtivoFalse() {
        SolicitacaoAjuda solicitacao = new SolicitacaoAjuda();
        solicitacao.setAtivo(true);
        when(solicitacaoRepository.existsById(1L)).thenReturn(true);
        when(solicitacaoRepository.getReferenceById(1L)).thenReturn(solicitacao);

        solicitacaoService.excluir(1L);

        assertFalse(solicitacao.getAtivo());
        verify(solicitacaoRepository, times(1)).save(solicitacao);
    }

    @Test
    public void testExcluir_ThrowsObjectNotFoundException() {
        when(solicitacaoRepository.existsById(1L)).thenReturn(false);
        assertThrows(ObjectNotFoundException.class, () -> solicitacaoService.excluir(1L));
    }

    @Test
    public void testNegar_SetsStatusNegado() {
        SolicitacaoAjuda solicitacao = new SolicitacaoAjuda();
        solicitacao.setStatus(StatusEnum.ANALISE);
        when(solicitacaoRepository.existsById(1L)).thenReturn(true);
        when(solicitacaoRepository.getReferenceById(1L)).thenReturn(solicitacao);

        solicitacaoService.negar(1L);

        assertEquals(StatusEnum.NEGADO, solicitacao.getStatus());
        verify(solicitacaoRepository, times(1)).save(solicitacao);
    }

    @Test
    public void testAprovar_SetsStatusAprovado() {
        SolicitacaoAjuda solicitacao = new SolicitacaoAjuda();
        solicitacao.setStatus(StatusEnum.ANALISE);
        when(solicitacaoRepository.existsById(1L)).thenReturn(true);
        when(solicitacaoRepository.getReferenceById(1L)).thenReturn(solicitacao);

        solicitacaoService.aprovar(1L);

        assertEquals(StatusEnum.APROVADO, solicitacao.getStatus());
        verify(solicitacaoRepository, times(1)).save(solicitacao);
    }

    @Test
    public void testFindById_ThrowsObjectNotFoundException() {
        when(solicitacaoRepository.existsById(1L)).thenReturn(false);
        assertThrows(ObjectNotFoundException.class, () -> {
            solicitacaoService.excluir(1L);
        });
    }
}
