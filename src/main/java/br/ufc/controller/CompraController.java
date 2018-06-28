package br.ufc.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.model.Compra;
import br.ufc.model.Usuario;
import br.ufc.service.CompraService;
import br.ufc.service.UsuarioService;

@Controller
public class CompraController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CompraService compraService;

    @GetMapping("/user/compras")
    public ModelAndView compraUser(Principal principal) {
        ModelAndView mv = new ModelAndView("/site/compras/list");
        Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
        mv.addObject("pedidos", usuario.getCompras());
        return mv;
    }

    @GetMapping("/user/compras/{id}")
    public ModelAndView comprasUser(@PathVariable("id") Compra compra, Principal principal) {
        ModelAndView mv = new ModelAndView("/site/compras/compra");
        mv.addObject("compra", compra);
        return mv;
    }

    @GetMapping("/admin/compras")
    public ModelAndView comprasAll() {
        ModelAndView mv = new ModelAndView("/admin/compras/list");
        mv.addObject("compras", compraService.list());
        return mv;
    }

    @GetMapping("/admin/compras/{id}")
    public ModelAndView compra(@PathVariable("id") Compra compra, Principal principal) {
        ModelAndView mv = new ModelAndView("/admin/compras/compra");
        mv.addObject("compra", compra);
        return mv;
    }
}