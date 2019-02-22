package org.webServiceEstoque.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

@Entity
public class ItemVenda {
	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	@Expose
	String id;
	@Expose
	String idProduto;
	@Expose
	String idEntradaProduto;
	@Expose
	String idVenda;
	@Expose
	int quantidadeVendida;
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

	public String getIdEntradaProduto() {
		return idEntradaProduto;
	}

	public void setIdEntradaProduto(String idEntradaProduto) {
		this.idEntradaProduto = idEntradaProduto;
	}

	public int getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(int sincronizado) {
		this.sincronizado = sincronizado;
	}

	public String getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(String idVenda) {
		this.idVenda = idVenda;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}

	public String getIdLote() {
		return idEntradaProduto;
	}

	public void setIdLote(String idLote) {
		this.idEntradaProduto = idLote;
	}

	public int getQuantidadeVendida() {
		return quantidadeVendida;
	}

	public void setQuantidadeVendida(int quantidadeVendida) {
		this.quantidadeVendida = quantidadeVendida;
	}

	public void sincroniza() {
		this.sincronizado = 1;
	}

	public void desincroniza() {
		this.sincronizado = 0;
	}

}
