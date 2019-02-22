package org.webServiceEstoque.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class MomentoDaUltimaAtualizacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date momentoDaUltimaAtualizacao;
	
	
	public MomentoDaUltimaAtualizacao(){
		momentoDaUltimaAtualizacao = new Date();
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getMomentoDaUltimaAtualizacao() {
		return momentoDaUltimaAtualizacao;
	}
	public void setMomentoDaUltimaAtualizacao(Date momentoDaUltimaAtualizacao) {
		this.momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacao;
	}
	
	

}
