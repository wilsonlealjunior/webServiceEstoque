package org.webServiceEstoque.models;

import java.util.Date;

public class RelatorioFuro {
	public Date data;
	public Double valor;
	public String loja;
	public String nome;
	public String funcionario;
	public Integer quantidade;
	public Double precoDeVenda;
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	
	
	public String getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
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
	public Double getPrecoDeVenda() {
		return precoDeVenda;
	}
	public void setPrecoDeVenda(Double precoDeVenda) {
		this.precoDeVenda = precoDeVenda;
	}
	



}
