package br.ufc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.service.CompraService;
import br.ufc.service.ProdutoService;
import br.ufc.service.UsuarioService;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired 
    private CompraService compraService;

    @RequestMapping("/")
    public ModelAndView site() {
        ModelAndView mv = new ModelAndView("site/index");
        mv.addObject("new_produtos", produtoService.buscarUltimos());
        mv.addObject("produtos", produtoService.destaques());
        return mv;
    }

    @RequestMapping("/admin")
    public ModelAndView admin() {
        ModelAndView mv = new ModelAndView("admin/index");
        mv.addObject("total_produtos", produtoService.total());
        mv.addObject("total_usuarios", usuarioService.total());
        mv.addObject("total_compras", compraService.total());
        return mv;
    }
    
}
