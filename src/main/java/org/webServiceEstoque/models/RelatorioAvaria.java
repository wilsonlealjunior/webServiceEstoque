package org.webServiceEstoque.models;

import java.util.Date;

public class RelatorioAvaria {
	public Date data;
	public Double prejuizo;
	public String loja;
	public String nome;
	public Integer quantidade;
	public Double precoDeCompra;
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getPrejuizo() {
		return prejuizo;
	}
	public void setPrejuizo(Double prejuizo) {
		this.prejuizo = prejuizo;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
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
	






}
