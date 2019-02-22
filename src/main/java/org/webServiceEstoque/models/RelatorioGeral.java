package org.webServiceEstoque.models;

public class RelatorioGeral {
	String produto;
	String loja;
	Integer vendida;
	Integer avaria;
	Integer entrada;
	Integer movimentada;
	Integer furo;
	
	
	public Integer getFuro() {
		return furo;
	}
	public void setFuro(Integer furo) {
		this.furo = furo;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
	}
	
	public Integer getVendida() {
		return vendida;
	}
	public void setVendida(Integer vendida) {
		this.vendida = vendida;
	}
	public Integer getAvaria() {
		return avaria;
	}
	public void setAvaria(Integer avaria) {
		this.avaria = avaria;
	}
	public Integer getEntrada() {
		return entrada;
	}
	public void setEntrada(Integer entrada) {
		this.entrada = entrada;
	}
	public Integer getMovimentada() {
		return movimentada;
	}
	public void setMovimentada(Integer movimentada) {
		this.movimentada = movimentada;
	}
	
}
