package br.ufc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.model.Produto;
import br.ufc.session.Carrinho;


@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private Carrinho carrinho;

    @GetMapping
    public ModelAndView carrinho() {
        ModelAndView mv = new ModelAndView("site/carrinho");
        mv.addObject("produtos", carrinho.list());
        mv.addObject("quantidade", carrinho.getQuantidade());
        mv.addObject("valorTotal", carrinho.calcularValorTotal());
        return mv;
    }

    @GetMapping("/add/{idProduto}")
	public String addProduto(@PathVariable("idProduto") Produto produto, RedirectAttributes attributes) {
        carrinho.add(produto);
        attributes.addFlashAttribute("mensagem", "Produto adicionado com sucesso!");
        return "redirect:/carrinho";
    }

    @GetMapping("/rm/{idProduto}")
	public String rmProduto(@PathVariable("idProduto") Produto produto, RedirectAttributes attributes) {
        carrinho.remove(produto);
        attributes.addFlashAttribute("mensagem", "Produto removido com sucesso!");
        return "redirect:/carrinho";
    }
    
}