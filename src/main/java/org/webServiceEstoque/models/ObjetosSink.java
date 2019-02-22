package org.webServiceEstoque.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ObjetosSink {
	@Expose
	List<Loja> lojas;
	@Expose
	List<Produto> produtos;
	@Expose
	List<MovimentacaoProduto> movimentacaoProdutos;
	@Expose
	List<EntradaProduto> entradaProdutos;
	@Expose
	List<Venda> vendas;
	@Expose
    List<Avaria> avarias;
	@Expose
    List<Usuario> usuarios;
	@Expose
	List<Furo> furos;
	@Expose
	Date momentoDaUltimaAtualizacao;
	

    public ObjetosSink(){
        avarias = new ArrayList<>();
        entradaProdutos = new ArrayList<>();
        lojas = new ArrayList<>();
        movimentacaoProdutos = new ArrayList<>();
        produtos = new ArrayList<>();
        vendas = new ArrayList<>();
        usuarios = new ArrayList<>();
        furos = new ArrayList<>();

    }
    
    

    
    public List<Furo> getFuros() {
		return furos;
	}




	public void setFuros(List<Furo> furos) {
		this.furos = furos;
	}




	public List<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}


	public Date getMomentoDaUltimaAtualizacao() {
        return momentoDaUltimaAtualizacao;
    }

    public void setMomentoDaUltimaAtualizacao(Date momentoDaUltimaAtualizacao) {
        this.momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacao;
    }

    public List<Avaria> getAvarias() {
        return avarias;
    }

    public void setAvarias(List<Avaria> avarias) {
        this.avarias = avarias;
    }

    public List<EntradaProduto> getEntradaProdutos() {
        return entradaProdutos;
    }

    public void setEntradaProdutos(List<EntradaProduto> entradaProdutos) {
        this.entradaProdutos = entradaProdutos;
    }

    public List<Loja> getLojas() {
        return lojas;
    }

    public void setLojas(List<Loja> lojas) {
        this.lojas = lojas;
    }

    public List<MovimentacaoProduto> getMovimentacaoProdutos() {
        return movimentacaoProdutos;
    }

    public void setMovimentacaoProdutos(List<MovimentacaoProduto> movimentacaoProdutos) {
        this.movimentacaoProdutos = movimentacaoProdutos;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }
}
