package br.com.impacto.voluntario.controllers;

import br.com.impacto.voluntario.config.MockConfig;
import br.com.impacto.voluntario.enums.DesastreEnum;
import br.com.impacto.voluntario.enums.HabilidadesEnum;
import br.com.impacto.voluntario.enums.StatusEnum;
import br.com.impacto.voluntario.enums.UrgenciaEnum;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(MockConfig.class)
public class NecessidadeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    public void testNecessidadesPage() throws Exception {
        mockMvc.perform(get("/necessidade"))
                .andExpect(status().isOk())
                .andExpect(view().name("necessidades"))
                .andExpect(model().attributeExists("necessidades"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateNecessidade() throws Exception {
        mockMvc.perform(post("/necessidade")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("titulo", "Campaign Title")
                        .param("descricao", "Description")
                        .param("pessoasAfetadas", "20")
                        .param("habilidadesRequeridas", HabilidadesEnum.OUTRO.name())
                        .param("habilidadeOutro", "Other skill")
                        .param("endereco.cep", "12345-678")
                        .param("endereco.cidade", "CidadeTeste")
                        .param("endereco.uf", "ST")
                        .param("endereco.bairro", "BairroTeste")
                        .param("endereco.numero", "123")
                        .param("endereco.complemento", "ComplementoTeste")
                        .param("desastre", DesastreEnum.INCENDIO.name())
                        .param("urgencia", UrgenciaEnum.ALTA.name())
                        .param("status", StatusEnum.ANALISE.name())
                        .param("dataEnvio", LocalDate.now().toString())
                        .param("ativo", "true")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("admin/painel"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testExcluirNecessidade() throws Exception {
        mockMvc.perform(get("/necessidade/excluir/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/painel"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testFinalizarNecessidade() throws Exception {
        mockMvc.perform(get("/necessidade/finalizar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/painel"));
    }
}
