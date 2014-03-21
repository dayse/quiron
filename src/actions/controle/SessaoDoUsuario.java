package actions.controle;

import java.io.Serializable;

import modelo.TipoUsuario;
import modelo.Usuario;

/**
 * 
 * Classe que oferece recursos para obtenção de informações a respeito do
 * usuário em sessão.
 * 
 * @author bruno.oliveira (Atualização)
 * 
 */
public class SessaoDoUsuario implements Serializable {

	// Componentes de Controle
	private static final long serialVersionUID = 1L;
	private Usuario usuarioLogado;
	@SuppressWarnings("unused")
	private boolean aluno;
	@SuppressWarnings("unused")
	private boolean clinico;
	@SuppressWarnings("unused")
	private boolean tecnico;
	@SuppressWarnings("unused")
	private boolean engenheiroConhecimento;
	@SuppressWarnings("unused")
	private boolean administrador;

	/*  ************* Get & Set ************ */

	public boolean isAluno() {
		return usuarioLogado.getTipoUsuario().equals(TipoUsuario.ALUNO);
	}

	public boolean isClinico() {
		return usuarioLogado.getTipoUsuario().getTipoUsuario().equals(
				TipoUsuario.CLINICO);
	}

	public boolean isTecnico() {
		return usuarioLogado.getTipoUsuario().getTipoUsuario().equals(
				TipoUsuario.TECNICO);
	}

	public boolean isEngenheiroConhecimento() {
		return usuarioLogado.getTipoUsuario().getTipoUsuario().equals(
				TipoUsuario.ENGENHEIRO_DE_CONHECIMENTO);
	}

	public boolean isAdministrador() {
		return usuarioLogado.getTipoUsuario().getTipoUsuario().equals(
				TipoUsuario.ADMINISTRADOR);
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
}