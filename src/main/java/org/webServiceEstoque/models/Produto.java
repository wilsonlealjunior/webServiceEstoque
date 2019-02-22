package org.webServiceEstoque.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

@Entity
public class Produto {
	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	@Expose
	private String id;
	@Expose
	private int estoqueMinimo;
	@Expose
	private String nome;
	@Expose
	private double preco;
	@Expose
	private int quantidade;

	@OneToMany(mappedBy = "produto", targetEntity = EntradaProduto.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EntradaProduto> entradaProdutos;

	@ManyToOne
	@JoinColumn(name = "loja_id")
	@Expose
	private Loja loja;
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	private Date momentoDaUltimaAtualizacao;

	@Expose
	private int sincronizado;

	@Expose
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	private List<String> idProdutoVinculado;
	@Expose
	private String idProdutoPrincipal;
	@Expose
	private int vinculo;

	public Produto() {
		momentoDaUltimaAtualizacao = new Date();

	}

	public int getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(int sincronizado) {
		this.sincronizado = sincronizado;
	}

	public List<String> getIdProdutoVinculado() {
		return idProdutoVinculado;
	}

	public void setIdProdutoVinculado(List<String> idProdutoVinculado) {
		this.idProdutoVinculado = idProdutoVinculado;
	}

	public String getIdProdutoPrincipal() {
		return idProdutoPrincipal;
	}

	public void setIdProdutoPrincipal(String idProdutoPrincipal) {
		this.idProdutoPrincipal = idProdutoPrincipal;
	}


	public int getVinculo() {
		return vinculo;
	}

	public void setVinculo(int vinculo) {
		this.vinculo = vinculo;
	}

	public Date getMomentoDaUltimaAtualizacao() {
		return momentoDaUltimaAtualizacao;
	}

	public void setMomentoDaUltimaAtualizacao(Date momentoDaUltimaAtualizacao) {
		this.momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacao;
	}

	public Produto(String id, String nome, int quantidade) {

		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public String getNome() {
		return nome;
	}

	public List<EntradaProduto> getEntradaProdutos() {
		return entradaProdutos;
	}

	public void setEntradaProdutos(List<EntradaProduto> entradaProdutos) {
		this.entradaProdutos = entradaProdutos;
	}

	public int getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "Produto [nome=" + nome + ", preco=" + preco + ", Quantidade=" + quantidade + "]";
	}
}
