package br.com.javaparaweb.financeiro.web;

import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import br.com.javaparaweb.financeiro.usuario.Usuario;
import br.com.javaparaweb.financeiro.usuario.UsuarioRN;

@ManagedBean(name = "usuarioBean")
@RequestScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private String confimarSenha;
	private List<Usuario> lista;
	private String destinoSalvar;

	public String novo(){
		this.destinoSalvar = "usuariosucesso";
		this.usuario = new Usuario();
		this.usuario.setAtivo(true);
		return "/publico/usuario";
	}
	
	public String atribuiPermissao(Usuario usuario, String permissao){
		this.usuario = usuario;
		Set<String> permissoes = this.usuario.getPermissao();
		if(permissoes.contains(permissao)){
			permissoes.remove(permissao);
		} else {
			permissoes.add(permissao);
		}
		
		return null;
	}
	
	public String editar(){
		this.confimarSenha = this.usuario.getSenha();
		return "/publico/usuario";
	}
	
	public String salvar(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		String senha = this.usuario.getSenha();
		if(!senha.equals(this.confimarSenha)){
			FacesMessage facesMessage = new FacesMessage("A senha não foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}
		
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(this.usuario);
		
		return this.destinoSalvar;
	}
	
	public String excluir(){
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.excluir(this.usuario);
		this.lista = null;
		return null;
	}
	
	public String ativar(){
		if(this.usuario.isAtivo())
			this.usuario.setAtivo(false);
		else
			this.usuario.setAtivo(true);
		
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(this.usuario);
		return null;
	}
	
	
	public List<Usuario> getLista() {
		if(this.lista == null){
			UsuarioRN usuarioRN = new UsuarioRN();
			this.lista = usuarioRN.listar();
		}
		return this.lista;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfimarSenha() {
		return confimarSenha;
	}

	public void setConfimarSenha(String confimarSenha) {
		this.confimarSenha = confimarSenha;
	}

}
