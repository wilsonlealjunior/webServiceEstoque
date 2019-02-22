package org.webServiceEstoque.models;

import java.util.Date;

public class RelatorioMovimentacaoProduto {
	Date data;
	String nomeProd;
	Integer quantidade;
	String lojaDe;
	String lojaPara;

	public RelatorioMovimentacaoProduto() {

	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNomeProd() {
		return nomeProd;
	}

	public void setNomeProd(String nomeProd) {
		this.nomeProd = nomeProd;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getLojaDe() {
		return lojaDe;
	}

	public void setLojaDe(String lojaDe) {
		this.lojaDe = lojaDe;
	}

	public String getLojaPara() {
		return lojaPara;
	}

	public void setLojaPara(String lojaPara) {
		this.lojaPara = lojaPara;
	}

	

}
