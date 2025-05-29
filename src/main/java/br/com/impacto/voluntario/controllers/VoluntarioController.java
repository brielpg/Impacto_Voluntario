package br.com.impacto.voluntario.controllers;

import br.com.impacto.voluntario.dtos.CreateVoluntarioDto;
import br.com.impacto.voluntario.models.Voluntario;
import br.com.impacto.voluntario.services.NecessidadeService;
import br.com.impacto.voluntario.services.VoluntarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController {

    @Autowired
    private VoluntarioService service;

    @Autowired
    private NecessidadeService necessidadeService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        var voluntario = service.getByEmail(principal.getName());
        var oportunidades = necessidadeService.getAll().size();
        model.addAttribute("voluntario", voluntario);
        model.addAttribute("oportunidades", oportunidades);
        return "voluntario/dashboard";
    }

    @GetMapping("/ajudar/{id}")
    public String queroAjudar(Principal principal, @PathVariable Long id){
        service.queroAjudar(principal.getName(), id);
        return "redirect:/voluntario/dashboard";
    }

    @GetMapping
    public String createPage(Model model){
        model.addAttribute("voluntario", new Voluntario());
        return "voluntario/cadastro";
    }

    @PostMapping
    public String createVoluntario(@Valid CreateVoluntarioDto dto, Model model){
        var voluntario =  service.create(dto);
        model.addAttribute("voluntario", voluntario);
        return "redirect:home";
    }
}
