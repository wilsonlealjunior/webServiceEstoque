package org.webServiceEstoque.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

@Entity
public class ItemFuro {

	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	@Expose
	String id;
	@Expose
	String idFuro;
	@Expose
	String idEntradaProduto;
	@Expose
	int quantidade;
	@Expose
	int sincronizado;

	@Expose
	double precoDeVenda;

	public double getPrecoDeVenda() {
		return precoDeVenda;
	}

	public void setPrecoDeVenda(double precoDeVenda) {
		this.precoDeVenda = precoDeVenda;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdFuro() {
		return idFuro;
	}

	public void setIdFuro(String idFuro) {
		this.idFuro = idFuro;
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
