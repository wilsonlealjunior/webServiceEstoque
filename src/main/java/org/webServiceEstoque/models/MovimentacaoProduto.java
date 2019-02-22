package org.webServiceEstoque.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

/**
 * Created by wilso on 14/11/2017.
 */

@Entity
public class MovimentacaoProduto {
	
	

	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	@Expose
    String id;
	@Expose
    String idLojaDe;
	@Expose
    String idLojaPara;
	@Expose
    int quantidade;
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
	@Temporal(TemporalType.TIMESTAMP)
    Date momentoDaUltimaAtualizacao;
	
	@Expose
	int desativado;
	
	
	
	public int getDesativado() {
		return desativado;
	}
	public void setDesativado(int desativado) {
		this.desativado = desativado;
	}
	public MovimentacaoProduto(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdLojaDe() {
		return idLojaDe;
	}
	public void setIdLojaDe(String idLojaDe) {
		this.idLojaDe = idLojaDe;
	}
	public String getIdLojaPara() {
		return idLojaPara;
	}
	public void setIdLojaPara(String idLojaPara) {
		this.idLojaPara = idLojaPara;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
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
	public Date getMomentoDaUltimaAtualizacao() {
		return momentoDaUltimaAtualizacao;
	}
	public void setMomentoDaUltimaAtualizacao(Date momentoDaUltimaAtualizacao) {
		this.momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacao;
	}
    
    
   
}
