package org.webServiceEstoque.models;

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
public class Furo {
	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	@Expose
	String id;
	@Expose
	@OneToMany(targetEntity = ItemFuro.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<ItemFuro> furoEntradeProdutos;
	@Expose
	String idLoja;
	@Expose
	String idUsuario;
	@Expose
	String idProduto;
	@Expose
	int sincronizado;
	@Temporal(TemporalType.TIMESTAMP)
	@Expose
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	Date data;
	@Expose
	double valor;
	@Expose
	double precoDeVenda;
	@Expose
	int quantidade;

	@Expose
	int desativado;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	private Date momentoDaUltimaAtualizacao;

	public int getDesativado() {
		return desativado;
	}

	public void setDesativado(int desativado) {
		this.desativado = desativado;
	}

	public Date getMomentoDaUltimaAtualizacao() {
		return momentoDaUltimaAtualizacao;
	}

	public void setMomentoDaUltimaAtualizacao(Date momentoDaUltimaAtualizacao) {
		this.momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacao;
	}

	
	public String getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}

	public double getPrecoDeVenda() {
		return precoDeVenda;
	}

	public void setPrecoDeVenda(double precoDeVenda) {
		this.precoDeVenda = precoDeVenda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void sincroniza() {
		this.sincronizado = 1;
	}

	public void desincroniza() {
		this.sincronizado = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ItemFuro> getFuroEntradeProdutos() {
		return furoEntradeProdutos;
	}

	public void setFuroEntradeProdutos(List<ItemFuro> furoEntradeProdutos) {
		this.furoEntradeProdutos = furoEntradeProdutos;
	}

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(int sincronizado) {
		this.sincronizado = sincronizado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
