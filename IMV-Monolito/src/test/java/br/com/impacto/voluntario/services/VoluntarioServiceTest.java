package br.com.impacto.voluntario.services;

import br.com.impacto.voluntario.dtos.CreateVoluntarioDto;
import br.com.impacto.voluntario.dtos.DtoEndereco;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import br.com.impacto.voluntario.enums.Roles;
import br.com.impacto.voluntario.exceptions.ConflictException;
import br.com.impacto.voluntario.exceptions.InvalidDateException;
import br.com.impacto.voluntario.exceptions.ObjectNotFoundException;
import br.com.impacto.voluntario.models.Endereco;
import br.com.impacto.voluntario.models.Voluntario;
import br.com.impacto.voluntario.repositories.EnderecoRepository;
import br.com.impacto.voluntario.repositories.VoluntarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoluntarioServiceTest {

    @Mock
    private VoluntarioRepository voluntarioRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private NecessidadeService necessidadeService;

    @Mock
    private EmailProducer emailProducer;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private VoluntarioService voluntarioService;

    private CreateVoluntarioDto validDto;

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
        validDto = new CreateVoluntarioDto(
                "John Doe",
                "john@example.com",
                "12345678901",
                "123456789",
                "password",
                LocalDate.now().minusYears(20),
                endereco,
                List.of(HabilidadesEnum.OUTRO),
                "Other skill"
        );
    }

    @Test
    public void testCreate_Success() {
        when(voluntarioRepository.existsByCpf(validDto.cpf())).thenReturn(false);
        when(voluntarioRepository.existsByEmail(validDto.email())).thenReturn(false);
        when(enderecoRepository.save(any(Endereco.class))).thenAnswer(i -> i.getArgument(0));
        when(passwordEncoder.encode(validDto.senha())).thenReturn("encodedPassword");

        Voluntario result = voluntarioService.create(validDto);

        assertNotNull(result);
        assertEquals(validDto.nome(), result.getNome());
        assertEquals(validDto.email(), result.getEmail());
        assertEquals("encodedPassword", result.getSenha());
        assertTrue(result.getAtivo());
        assertEquals(Roles.USER, result.getRole());
        verify(emailProducer, times(1)).sendEmailMessage(eq(result.getEmail()), anyString(), anyString());
        verify(voluntarioRepository, times(1)).save(any(Voluntario.class));
    }

    @Test
    public void testCreate_ThrowsConflictException_WhenCpfExists() {
        when(voluntarioRepository.existsByCpf(validDto.cpf())).thenReturn(true);
        ConflictException ex = assertThrows(ConflictException.class, () -> voluntarioService.create(validDto));
        assertEquals("Cpf already registered", ex.getMessage());
    }

    @Test
    public void testCreate_ThrowsConflictException_WhenEmailExists() {
        when(voluntarioRepository.existsByCpf(validDto.cpf())).thenReturn(false);
        when(voluntarioRepository.existsByEmail(validDto.email())).thenReturn(true);
        ConflictException ex = assertThrows(ConflictException.class, () -> voluntarioService.create(validDto));
        assertEquals("Email already registered", ex.getMessage());
    }

    @Test
    public void testCreate_ThrowsInvalidDateException_WhenBirthDateInFuture() {
        CreateVoluntarioDto dto = new CreateVoluntarioDto(
                "Jane Doe",
                "jane@example.com",
                "12345678902",
                "987654321",
                "password",
                LocalDate.now().plusDays(1),
                null,
                List.of(),
                null
        );
        InvalidDateException ex = assertThrows(InvalidDateException.class, () -> voluntarioService.create(dto));
        assertNotNull(ex);
    }

    @Test
    public void testCreate_ThrowsInvalidDateException_WhenUnderage() {
        CreateVoluntarioDto dto = new CreateVoluntarioDto(
                "Jane Doe",
                "jane@example.com",
                "12345678902",
                "987654321",
                "password",
                LocalDate.now().minusYears(17),
                null,
                List.of(),
                null
        );
        InvalidDateException ex = assertThrows(InvalidDateException.class, () -> voluntarioService.create(dto));
        assertEquals("Voluntario must be at least 18 years old", ex.getMessage());
    }

    @Test
    public void testGetByEmail_Success() {
        Voluntario voluntario = new Voluntario();
        voluntario.setEmail(validDto.email());
        when(voluntarioRepository.findByEmail(validDto.email())).thenReturn(Optional.of(voluntario));

        Voluntario result = voluntarioService.getByEmail(validDto.email());

        assertNotNull(result);
        assertEquals(validDto.email(), result.getEmail());
    }

    @Test
    public void testGetByEmail_ThrowsObjectNotFoundException() {
        when(voluntarioRepository.findByEmail(validDto.email())).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> voluntarioService.getByEmail(validDto.email()));
    }

    @Test
    public void testLoadUserByUsername_Success() {
        Voluntario voluntario = new Voluntario();
        voluntario.setEmail(validDto.email());
        when(voluntarioRepository.findByEmail(validDto.email())).thenReturn(Optional.of(voluntario));

        UserDetails userDetails = voluntarioService.loadUserByUsername(validDto.email());

        assertNotNull(userDetails);
        assertEquals(validDto.email(), userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_ThrowsObjectNotFoundException() {
        when(voluntarioRepository.findByEmail(validDto.email())).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> voluntarioService.loadUserByUsername(validDto.email()));
    }
}
