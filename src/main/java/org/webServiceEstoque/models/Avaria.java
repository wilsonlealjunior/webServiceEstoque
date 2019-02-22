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
public class Avaria {

	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	@Expose
	String id;
	@Expose
	@OneToMany(targetEntity = ItemAvaria.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<ItemAvaria> avariaEntradeProdutos;
	@Expose
	String idLoja;
	@Expose
	int sincronizado;
	@Expose
	double prejuizo;
	@Expose
	int desativado;
	@Expose
	String idProduto;
	@Expose
	int quantidade;

	@Temporal(TemporalType.TIMESTAMP)
	@Expose
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	Date data;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	private Date momentoDaUltimaAtualizacao;

	public Avaria() {
		avariaEntradeProdutos = new ArrayList<ItemAvaria>();

	}

	public int getDesativado() {
		return desativado;
	}

	public void setDesativado(int desativado) {
		this.desativado = desativado;
	}

	public double getPrejuizo() {
		return prejuizo;
	}

	public void setPrejuizo(double prejuizo) {
		this.prejuizo = prejuizo;
	}

	public String getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Date getMomentoDaUltimaAtualizacao() {
		return momentoDaUltimaAtualizacao;
	}

	public void setMomentoDaUltimaAtualizacao(Date momentoDaUltimaAtualizacao) {
		this.momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ItemAvaria> getAvariaEntradeProdutos() {
		return avariaEntradeProdutos;
	}

	public void setAvariaEntradeProdutos(List<ItemAvaria> avariaEntradeProdutos) {
		this.avariaEntradeProdutos = avariaEntradeProdutos;
	}

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
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

}
