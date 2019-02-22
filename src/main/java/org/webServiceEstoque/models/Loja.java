package org.webServiceEstoque.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

@Entity
public class Loja {
	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	@Expose
	String id;
	@Expose
	String nome;
	@Expose
	String endereco;
	
	@OneToMany(mappedBy = "loja", targetEntity = Produto.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Produto> produtos;
	
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	private Date momentoDaUltimaAtualizacao;
	
	@Expose
	private int sincronizado;
	
	public Loja() {
		momentoDaUltimaAtualizacao = new Date();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
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
	
	




	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	
	

}
