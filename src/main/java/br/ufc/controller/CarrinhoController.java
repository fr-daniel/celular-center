package br.ufc.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.model.Produto;
import br.ufc.model.Usuario;
import br.ufc.service.CompraService;
import br.ufc.service.UsuarioService;
import br.ufc.session.Carrinho;

@Controller
public class CarrinhoController {

    @Autowired
    private Carrinho carrinho;

    @Autowired 
    private CompraService compraService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/carrinho")
    public ModelAndView carrinho() {
        ModelAndView mv = new ModelAndView("site/carrinho");
        mv.addObject("produtos", carrinho.list());
        mv.addObject("quantidade", carrinho.getQuantidade());
        mv.addObject("valorTotal", carrinho.calcularValorTotal());
        return mv;
    }

    @GetMapping("/carrinho/add/{idProduto}")
	public String addProduto(@PathVariable("idProduto") Produto produto, RedirectAttributes attributes) {
        carrinho.add(produto);
        attributes.addFlashAttribute("mensagem", "Produto adicionado com sucesso!");
        return "redirect:/carrinho";
    }

    @GetMapping("/carrinho/rm/{idProduto}")
	public String rmProduto(@PathVariable("idProduto") Produto produto, RedirectAttributes attributes) {
        carrinho.remove(produto);
        attributes.addFlashAttribute("mensagem", "Produto removido com sucesso!");
        return "redirect:/carrinho";
    }
    
    @GetMapping("/user/carrinho/comprar")
    public String finalizarCompra(Principal principal, RedirectAttributes attributes) {
        Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
        compraService.salvar(carrinho.list(), usuario);
        carrinho.limpar();
        attributes.addFlashAttribute("mensagem", "Compra realizada com sucesso!");
        return "redirect:/user/compras";
    }

}