package br.com.impacto.voluntario.controllers;

import br.com.impacto.voluntario.dtos.CreateNecessidadeDto;
import br.com.impacto.voluntario.services.NecessidadeService;
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
@RequestMapping("/necessidade")
public class NecessidadeController {

    @Autowired
    private NecessidadeService service;

    @GetMapping
    public String necessidadesPage(Model model){
        var necessidades = service.getAll();
        model.addAttribute("necessidades", necessidades);
        return "necessidades";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createNecessidade(@Valid CreateNecessidadeDto dto, Model model){
        var necessidade =  service.create(dto);
        model.addAttribute("necessidade", necessidade);
        return "redirect:admin/painel";
    }

    @GetMapping("/excluir/{id}")
    public String excluirNecessidade(@PathVariable Long id){
        service.excluir(id);
        return "redirect:/admin/painel";
    }
}
