package br.com.impacto.voluntario.controllers;

import br.com.impacto.voluntario.dtos.CreateNecessidadeDto;
import br.com.impacto.voluntario.dtos.CreateVoluntarioDto;
import br.com.impacto.voluntario.services.NecessidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/necessidade")
public class NecessidadeController {

    @Autowired
    private NecessidadeService service;

    @PostMapping
    public String createNecessidade(@Valid CreateNecessidadeDto dto, Model model){
        var necessidade =  service.create(dto);
        model.addAttribute("necessidade", necessidade);
        return "redirect:admin/painel";
    }
}
