package br.com.impacto.voluntario.controllers;

import br.com.impacto.voluntario.config.MockConfig;
import br.com.impacto.voluntario.dtos.CreateSolicitacaoAjudaDto;
import br.com.impacto.voluntario.dtos.DtoEndereco;
import br.com.impacto.voluntario.enums.AjudaRequeridaEnum;
import br.com.impacto.voluntario.enums.DesastreEnum;
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
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(MockConfig.class)
public class SolicitacaoAjudaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreatePage() throws Exception {
        mockMvc.perform(get("/ajuda"))
                .andExpect(status().isOk())
                .andExpect(view().name("ajuda/formulario"))
                .andExpect(model().attributeExists("solicitacao"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateSolicitacao() throws Exception {
        DtoEndereco endereco = new DtoEndereco("12345-678", "CidadeTeste", "ST", "BairroTeste", 123, "ComplementoTeste");

        CreateSolicitacaoAjudaDto dto = new CreateSolicitacaoAjudaDto(
                "Requester",
                "requester@example.com",
                "123456789",
                "Description",
                LocalDate.now().minusDays(1),
                10,
                endereco,
                DesastreEnum.INCENDIO,
                UrgenciaEnum.ALTA,
                List.of(AjudaRequeridaEnum.OUTROS),
                "Other help"
        );

        mockMvc.perform(post("/ajuda")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nomeSolicitante", dto.nomeSolicitante())
                        .param("emailSolicitante", dto.emailSolicitante())
                        .param("telefoneSolicitante", dto.telefoneSolicitante())
                        .param("descricao", dto.descricao())
                        .param("dataAcontecimento", dto.dataAcontecimento().toString())
                        .param("dataEnvio", LocalDate.now().toString())
                        .param("ativo", String.valueOf(true))
                        .param("pessoasAfetadas", dto.pessoasAfetadas().toString())
                        .param("endereco.cep", endereco.cep())
                        .param("endereco.cidade", endereco.cidade())
                        .param("endereco.uf", endereco.uf())
                        .param("endereco.bairro", endereco.bairro())
                        .param("endereco.numero", endereco.numero().toString())
                        .param("endereco.complemento", endereco.complemento())
                        .param("desastre", dto.desastre().name())
                        .param("urgencia", dto.urgencia().name())
                        .param("status", String.valueOf(StatusEnum.ANALISE))
                        .param("ajudaRequerida", AjudaRequeridaEnum.OUTROS.name())
                        .param("outros", dto.outros())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/primeirosSocorros"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testNegarSolicitacao() throws Exception {
        mockMvc.perform(get("/ajuda/negar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/painel"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAprovarSolicitacao() throws Exception {
        mockMvc.perform(get("/ajuda/aprovar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/painel"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testExcluirSolicitacao() throws Exception {
        mockMvc.perform(get("/ajuda/excluir/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/painel"));
    }
}
