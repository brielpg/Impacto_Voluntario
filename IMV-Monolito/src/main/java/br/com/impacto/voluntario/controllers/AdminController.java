package br.com.impacto.voluntario.controllers;

import br.com.impacto.voluntario.models.Voluntario;
import br.com.impacto.voluntario.services.NecessidadeService;
import br.com.impacto.voluntario.services.SolicitacaoAjudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private NecessidadeService necessidadeService;

    @Autowired
    private SolicitacaoAjudaService solicitacaoAjudaService;

    @GetMapping("/painel")
    public String admin(Model model){
        var necessidades = necessidadeService.getAll();
        var solicitacoes = solicitacaoAjudaService.getAll();
        model.addAttribute("necessidades", necessidades);
        model.addAttribute("solicitacoes", solicitacoes);
        return "admin/painel";
    }
}
