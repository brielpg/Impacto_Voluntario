package br.com.impacto.voluntario.controllers;

import br.com.impacto.voluntario.config.MockConfig;
import br.com.impacto.voluntario.dtos.CreateVoluntarioDto;
import br.com.impacto.voluntario.dtos.DtoEndereco;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(MockConfig.class)
public class VoluntarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "test@example.com")
    public void testDashboard() throws Exception {
        mockMvc.perform(get("/voluntario/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("voluntario/dashboard"))
                .andExpect(model().attributeExists("voluntario"))
                .andExpect(model().attributeExists("oportunidades"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testQueroAjudarRedirect() throws Exception {
        mockMvc.perform(get("/voluntario/ajudar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/voluntario/dashboard"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testCancelarInscricaoRedirect() throws Exception {
        mockMvc.perform(get("/voluntario/cancelar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/voluntario/dashboard"));
    }

    @Test
    public void testCreatePage() throws Exception {
        mockMvc.perform(get("/voluntario"))
                .andExpect(status().isOk())
                .andExpect(view().name("voluntario/cadastro"))
                .andExpect(model().attributeExists("voluntario"));
    }

    @Test
    public void testCreateVoluntario() throws Exception {
        DtoEndereco endereco = new DtoEndereco("12345-678", "CidadeTeste", "ST", "BairroTeste", 123, "ComplementoTeste");
        CreateVoluntarioDto dto = new CreateVoluntarioDto(
                "Test tester",
                "tester@example.com",
                "12345678902",
                "123456789",
                "password",
                LocalDate.now().minusYears(20),
                endereco,
                List.of(HabilidadesEnum.OUTRO),
                "Other skill"
        );

        mockMvc.perform(post("/voluntario")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", dto.nome())
                .param("email", dto.email())
                .param("cpf", dto.cpf())
                .param("telefone", dto.telefone())
                .param("senha", dto.senha())
                .param("dataNascimento", dto.dataNascimento().toString())
                .param("endereco.cep", endereco.cep())
                .param("endereco.cidade", endereco.cidade())
                .param("endereco.uf", endereco.uf())
                .param("endereco.bairro", endereco.bairro())
                .param("endereco.numero", endereco.numero().toString())
                .param("endereco.complemento", endereco.complemento())
                .param("habilidades", HabilidadesEnum.OUTRO.name())
                .param("habilidadeOutro", dto.habilidadeOutro())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("home"));
    }
}
