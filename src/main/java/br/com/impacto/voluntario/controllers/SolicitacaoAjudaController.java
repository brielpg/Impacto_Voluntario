package br.com.impacto.voluntario.controllers;

import br.com.impacto.voluntario.dtos.CreateSolicitacaoAjudaDto;
import br.com.impacto.voluntario.models.SolicitacaoAjuda;
import br.com.impacto.voluntario.services.SolicitacaoAjudaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "redirect:home";
    }
}
