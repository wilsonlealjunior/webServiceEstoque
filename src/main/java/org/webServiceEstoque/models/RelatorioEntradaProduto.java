package org.webServiceEstoque.models;

import java.util.Date;

public class RelatorioEntradaProduto {
	  Date data;
	  String nome;
	  Integer quantidade;
	  Double precoDeCompra;
	  String loja;
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getPrecoDeCompra() {
		return precoDeCompra;
	}
	public void setPrecoDeCompra(Double precoDeCompra) {
		this.precoDeCompra = precoDeCompra;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
	}
	  
	  

}
