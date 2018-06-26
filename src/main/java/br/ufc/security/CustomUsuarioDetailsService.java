package br.ufc.security;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufc.model.Usuario;
import br.ufc.service.UsuarioService;

@Repository
@Transactional
public class CustomUsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.buscarPorEmail(email);

		if(usuario == null)
			throw new UsernameNotFoundException("Usuário não enontrado!");

		return new UsuarioSistema(usuario, getGrupos(usuario));
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		return usuario.getGrupos();
	}

}