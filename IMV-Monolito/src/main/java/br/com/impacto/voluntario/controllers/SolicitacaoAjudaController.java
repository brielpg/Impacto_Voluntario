package br.com.impacto.voluntario.controllers;

import br.com.impacto.voluntario.dtos.CreateSolicitacaoAjudaDto;
import br.com.impacto.voluntario.models.SolicitacaoAjuda;
import br.com.impacto.voluntario.services.SolicitacaoAjudaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ajuda")
public class SolicitacaoAjudaController {

    @Autowired
    private SolicitacaoAjudaService service;

    @GetMapping
    public String createPage(Model model){
        model.addAttribute("solicitacao", new SolicitacaoAjuda());
        return "ajuda/formulario";
    }

    @PostMapping
    public String createSolicitacao(@Valid CreateSolicitacaoAjudaDto dto, Model model){
        var solicitacao = service.create(dto);
        model.addAttribute("solicitacao", solicitacao);
        return "redirect:/primeirosSocorros";
    }

    @GetMapping("/negar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String negarSolicitacao(@PathVariable Long id){
        service.negar(id);
        return "redirect:/admin/painel";
    }

    @GetMapping("/aprovar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String aprovarSolicitacao(@PathVariable Long id){
        service.aprovar(id);
        return "redirect:/admin/painel";
    }

    @GetMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluirSolicitacao(@PathVariable Long id){
        service.excluir(id);
        return "redirect:/admin/painel";
    }
}
