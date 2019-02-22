package org.webServiceEstoque.models;

import java.util.Date;

public class RelatorioVendaFuncionario {
	String id;
	String formaDePagamento;
	Date dataDaVenda;
	Double lucro;
	String nomeVendedor;
	Double totalDinheiro;
	Double precoDeVenda;
	Integer quantidadeVendida;
	String nome;
	Double totalUnitario;
	String loja;
	Double totalCartao;

	public Double getTotalCartao() {
		return totalCartao;
	}

	public void setTotalCartao(Double totalCartao) {
		this.totalCartao = totalCartao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public Date getDataDaVenda() {
		return dataDaVenda;
	}

	public void setDataDaVenda(Date dataDaVenda) {
		this.dataDaVenda = dataDaVenda;
	}

	public Double getLucro() {
		return lucro;
	}

	public void setLucro(Double lucro) {
		this.lucro = lucro;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public Double getTotalDinheiro() {
		return totalDinheiro;
	}

	public void setTotalDinheiro(Double totalDinheiro) {
		this.totalDinheiro = totalDinheiro;
	}

	public Double getPrecoDeVenda() {
		return precoDeVenda;
	}

	public void setPrecoDeVenda(Double precoDeVenda) {
		this.precoDeVenda = precoDeVenda;
	}

	public Integer getQuantidadeVendida() {
		return quantidadeVendida;
	}

	public void setQuantidadeVendida(Integer quantidadeVendida) {
		this.quantidadeVendida = quantidadeVendida;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getTotalUnitario() {
		return totalUnitario;
	}

	public void setTotalUnitario(Double totalUnitario) {
		this.totalUnitario = totalUnitario;
	}

	public String getLoja() {
		return loja;
	}

	public void setLoja(String loja) {
		this.loja = loja;
	}

}
