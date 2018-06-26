package br.ufc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.model.Usuario;
import br.ufc.service.UsuarioService;
import br.ufc.service.exception.CpfUsuarioJaCadastradoException;
import br.ufc.service.exception.EmailUsuarioJaCadastradoException;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Usuario usuario) {
        ModelAndView mv = new ModelAndView("site/cadastro");
        mv.addObject("usuario", usuario);
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastro(@Valid Usuario usuario, BindingResult  result, RedirectAttributes attributes) {
        if(result.hasErrors())
            return cadastro(usuario);
		
		try {
			usuarioService.salvar(usuario);
		} catch (EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return cadastro(usuario);
		} catch (CpfUsuarioJaCadastradoException e) {
			result.rejectValue("cpf", e.getMessage(), e.getMessage());
			return cadastro(usuario);
        } 
    
        attributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso!");
		return new ModelAndView("redirect:/login");
    }

    @GetMapping("/admin/usuarios") 
    public ModelAndView listUsuarios() {
        ModelAndView mv = new ModelAndView("admin/usuarios/list");  
        mv.addObject("usuarios", usuarioService.findAll());
        return mv;
    }
    
}