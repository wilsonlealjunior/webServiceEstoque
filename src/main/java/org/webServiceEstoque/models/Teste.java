package org.webServiceEstoque.models;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Teste {
	
	@JsonDeserialize(using = CustomDateDeserializer.class)
	Date data;
	String nome;
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	
	

}
