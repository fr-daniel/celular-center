package br.ufc.service.exception;

public class CpfUsuarioJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 138833473549966877L;
	
	public CpfUsuarioJaCadastradoException(String message) {
		super(message);
	}
	
}
