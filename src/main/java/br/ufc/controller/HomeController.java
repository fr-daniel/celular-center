package br.ufc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.service.ProdutoService;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("site/index");
        mv.addObject("new_produtos", produtoService.buscarUltimos());
        mv.addObject("produtos", produtoService.destaques());
        return mv;
    }
    

}
