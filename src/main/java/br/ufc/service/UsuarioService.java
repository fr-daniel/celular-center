package br.ufc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.format.CPFFormatter;
import br.ufc.model.Usuario;
import br.ufc.repository.UsuarioRepository;
import br.ufc.service.exception.CpfUsuarioJaCadastradoException;
import br.ufc.service.exception.EmailUsuarioJaCadastradoException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).get();
    }

    public Usuario salvar(Usuario usuario) throws EmailUsuarioJaCadastradoException, CpfUsuarioJaCadastradoException {   
        Optional<Usuario> usuarioEmailExistente = usuarioRepository.findByEmail(usuario.getEmail());
       
		if (usuarioEmailExistente.isPresent())
            throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
            
        String cpf = new CPFFormatter().unformat(usuario.getCpf());
        Optional<Usuario> usuarioCpfExistente = usuarioRepository.findByCpf(cpf);
        
        if (usuarioCpfExistente.isPresent())
			throw new CpfUsuarioJaCadastradoException("CPF já cadastrado");
		
		usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
				
		return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
}