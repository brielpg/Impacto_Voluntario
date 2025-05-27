package br.com.impacto.voluntario.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class TemplatesController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/ps")
    public String primeirosSocorros(){
        return "primeirosSocorros";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
