package org.webServiceEstoque.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

@Entity
public class ItemAvaria {
	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	@Expose
	String id;
	@Expose
	String idAvaria;
	@Expose
	String idEntradaProduto;
	@Expose
	int quantidade;
	@Expose
	int sincronizado;

	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdAvaria() {
		return idAvaria;
	}
	public void setIdAvaria(String idAvaria) {
		this.idAvaria = idAvaria;
	}
	public String getIdEntradaProduto() {
		return idEntradaProduto;
	}
	public void setIdEntradaProduto(String idEntradaProduto) {
		this.idEntradaProduto = idEntradaProduto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getSincronizado() {
		return sincronizado;
	}
	public void setSincronizado(int sincronizado) {
		this.sincronizado = sincronizado;
	}
	
	

}
