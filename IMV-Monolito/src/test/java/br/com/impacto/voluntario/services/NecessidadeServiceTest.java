package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.CreateNecessidadeDto;
import br.com.impacto.voluntario.dtos.DtoEndereco;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import br.com.impacto.voluntario.enums.StatusEnum;
import br.com.impacto.voluntario.exceptions.ObjectNotFoundException;
import br.com.impacto.voluntario.models.Necessidade;
import br.com.impacto.voluntario.models.Voluntario;
import br.com.impacto.voluntario.repositories.EnderecoRepository;
import br.com.impacto.voluntario.repositories.NecessidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NecessidadeServiceTest {

    @Mock
    private NecessidadeRepository necessidadeRepository;

    @Mock
    private VoluntarioService voluntarioService;

    @Mock
    private EmailProducer emailProducer;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private NecessidadeService necessidadeService;

    private CreateNecessidadeDto validDto;

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
        validDto = new CreateNecessidadeDto(
                "Campaign Title",
                "Description",
                20,
                endereco,
                null,
                null,
                List.of(HabilidadesEnum.OUTRO),
                "Other skill"
        );
    }

    @Test
    public void testCreate_Success() {
        when(enderecoRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(necessidadeRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Necessidade result = necessidadeService.create(validDto);

        assertNotNull(result);
        assertEquals(validDto.titulo(), result.getTitulo());
        assertEquals(StatusEnum.ANDAMENTO, result.getStatus());
        assertTrue(result.getAtivo());
        verify(necessidadeRepository, times(1)).save(any(Necessidade.class));
    }

    @Test
    public void testGetAll_ReturnsList() {
        when(necessidadeRepository.findAllByAtivo()).thenReturn(List.of(new Necessidade()));

        List<Necessidade> result = necessidadeService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(necessidadeRepository, times(1)).findAllByAtivo();
    }

    @Test
    public void testExcluir_SetsAtivoFalseAndClearsVoluntarios() {
        Necessidade necessidade = new Necessidade();
        necessidade.setAtivo(true);
        Voluntario voluntario = new Voluntario();
        necessidade.getVoluntarios().add(voluntario);
        voluntario.getNecessidades().add(necessidade);

        when(necessidadeRepository.existsById(1L)).thenReturn(true);
        when(necessidadeRepository.getReferenceById(1L)).thenReturn(necessidade);

        necessidadeService.excluir(1L);

        assertFalse(necessidade.getAtivo());
        assertTrue(necessidade.getVoluntarios().isEmpty());
        verify(voluntarioService, times(1)).save(voluntario);
        verify(necessidadeRepository, times(1)).save(necessidade);
    }

    @Test
    public void testExcluir_ThrowsObjectNotFoundException() {
        when(necessidadeRepository.existsById(1L)).thenReturn(false);
        assertThrows(ObjectNotFoundException.class, () -> necessidadeService.excluir(1L));
    }

    @Test
    public void testFinalizar_SetsStatusFinalizadoAndSendsEmails() {
        Necessidade necessidade = new Necessidade();
        necessidade.setTitulo("Campaign");
        necessidade.setAtivo(true);
        necessidade.setPessoasAfetadas(10);
        Voluntario voluntario = new Voluntario();
        voluntario.setNome("John");
        voluntario.setEmail("john@example.com");
        voluntario.setMissoesConcluidas(0);
        voluntario.setVidasImpactadas(0);
        necessidade.getVoluntarios().add(voluntario);

        when(necessidadeRepository.existsById(1L)).thenReturn(true);
        when(necessidadeRepository.getReferenceById(1L)).thenReturn(necessidade);

        necessidadeService.finalizar(1L);

        assertEquals(StatusEnum.FINALIZADO, necessidade.getStatus());
        assertFalse(necessidade.getAtivo());
        assertEquals(1, voluntario.getMissoesConcluidas());
        assertEquals(10, voluntario.getVidasImpactadas());
        verify(voluntarioService, times(1)).save(voluntario);
        verify(emailProducer, times(1)).sendEmailMessage(eq(voluntario.getEmail()), anyString(), anyString());
        verify(necessidadeRepository, times(1)).save(necessidade);
    }

    @Test
    public void testFindById_ThrowsObjectNotFoundException() {
        when(necessidadeRepository.existsById(1L)).thenReturn(false);
        assertThrows(ObjectNotFoundException.class, () -> necessidadeService.findById(1L));
    }
}
