package org.webServiceEstoque.models;

import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

@Entity
public class Venda {

	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	@Expose
	public String id;
	@Expose
	@OneToMany(targetEntity = ItemVenda.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<ItemVenda> itemVendas;
	@Expose
	public String nomeVendedor;

	@Temporal(TemporalType.TIMESTAMP)
	@Expose
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public Date dataDaVenda;
	@Expose
	public String formaDePagamento;
	@Temporal(TemporalType.TIMESTAMP)
	@Expose
	public Date MomentoDaUltimaAtualizacao;
	@Expose
	public int sincronizado;
	@Expose
	public double totalDinheiro;
	@Expose
	public double lucro;

	@Expose
	public String idLoja;
	@Expose
	public Double totalCartao;

	@Expose
	int desativado;

	public int getDesativado() {
		return desativado;
	}

	public void setDesativado(int desativado) {
		this.desativado = desativado;
	}

	public Venda() {
		itemVendas = new ArrayList<ItemVenda>();

	}

	public Double getTotalCartao() {
		return totalCartao;
	}

	public void setTotalCartao(Double totalCartao) {
		this.totalCartao = totalCartao;
	}

	public double getTotalDinheiro() {
		return totalDinheiro;
	}

	public void setTotalDinheiro(double total) {
		this.totalDinheiro = total;
	}

	public double getLucro() {
		return lucro;
	}

	public void setLucro(double lucro) {
		this.lucro = lucro;
	}

	public List<ItemVenda> getItemVendas() {
		return itemVendas;
	}

	public void setItemVendas(List<ItemVenda> itemVendas) {
		this.itemVendas = itemVendas;
	}

	public int getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(int sincronizado) {
		this.sincronizado = sincronizado;
	}

	public List<ItemVenda> getProdutoEntradaProdutos() {
		return itemVendas;
	}

	public void setProdutoEntradaProdutos(List<ItemVenda> produtoEntradaProdutos) {
		this.itemVendas = produtoEntradaProdutos;
	}

	public Date getMomentoDaUltimaAtualizacao() {
		return MomentoDaUltimaAtualizacao;
	}

	public void setMomentoDaUltimaAtualizacao(Date MomentoDaUltimaAtualizacao) {
		this.MomentoDaUltimaAtualizacao = MomentoDaUltimaAtualizacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ItemVenda> getProduto() {
		return itemVendas;
	}

	public void setProduto(List<ItemVenda> produtoEntradaProdutos) {
		this.itemVendas = produtoEntradaProdutos;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public Date getDataDaVenda() {
		return dataDaVenda;
	}

	public void setDataDaVenda(Date dataDaVenda) {
		this.dataDaVenda = dataDaVenda;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}

}
