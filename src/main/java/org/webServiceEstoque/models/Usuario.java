package org.webServiceEstoque.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

@Entity
public class Usuario {
	/**
	 * 
	 */
	@Expose
	@Id
	private String nome;
	@Expose
	private String senha;
	@Expose
	private String role;
	// private List<Role> roles = new ArrayList<Role>();
	@Expose
	int desativado;
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	private Date momentoDaUltimaAtualizacao;
	@Expose
	int sincronizado;
	
	
	

	public int getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(int sincronizado) {
		this.sincronizado = sincronizado;
	}

	public Date getMomentoDaUltimaAtualizacao() {
		return momentoDaUltimaAtualizacao;
	}

	public void setMomentoDaUltimaAtualizacao(Date momentoDaUltimaAtualizacao) {
		this.momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacao;
	}

	public int getDesativado() {
		return desativado;
	}

	public void setDesativado(int desativado) {
		this.desativado = desativado;
	}

	public String getNome() {
		return nome;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}