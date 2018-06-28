package br.ufc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.ufc.session.Carrinho;

@ControllerAdvice
public class CarrinhoControllerAdvice {
    
    @Autowired
    private Carrinho carrinho;

    @ModelAttribute
    public void addCarrinhoAttribute(Model model) {
        model.addAttribute("carrinho", carrinho);
    }
    
}