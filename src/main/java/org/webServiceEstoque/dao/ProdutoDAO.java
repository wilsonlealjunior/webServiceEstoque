package org.webServiceEstoque.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.webServiceEstoque.models.Loja;
import org.webServiceEstoque.models.MomentoDaUltimaAtualizacao;
import org.webServiceEstoque.models.Produto;
import org.webServiceEstoque.models.RelatorioGeral;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
@Transactional
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	MomentoDaUltimaAtualizacaoDAO momentoDaUltimaAtualizacaoDAO;

	public void gravar(Produto produto) {
		manager.persist(produto);

	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarTodos() {
		Query query = manager.createQuery("from " + "Produto");
		List<Produto> listaDeProdutos = query.getResultList();
		return listaDeProdutos;
	}

	public List<Produto> novosRegistro(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(date);
		String Stringquery = " from Produto where momentoDaUltimaAtualizacao > '" + s + "'"
				+ " order by momentoDaUltimaAtualizacao desc ";
		Query query = manager.createQuery(Stringquery);
		List<Produto> listaDeProduto = query.getResultList();
		return listaDeProduto;

	}
	public List<RelatorioGeral> Relatorio(String lojaEscolhidaId,String nomeVendedor, String de, String ate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date inicio = null;
		Date fim = null;
		try {
			inicio = formatter.parse(de);
			fim = formatter.parse(ate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		de = formatter.format(inicio);
		ate = formatter.format(fim);
		List<RelatorioGeral> relatorioGeral = new ArrayList<>();
		
	
		String Stringquery = "SELECT p.nome AS produto, l.nome AS Loja, IFNULL((SELECT SUM(vep.quantidadeVendida) FROM Venda AS v INNER JOIN ItemVenda vep ON vep.idVenda = v.id INNER JOIN EntradaProduto ep ON ep.id = vep.idEntradaProduto WHERE p.id = vep.idProduto and v.nomeVendedor like '"+nomeVendedor+"'  and v.desativado = 0 and v.dataDaVenda between '" + de + "' and '" + ate + "'),0) AS vendida FROM Produto AS p INNER JOIN Loja l ON p.loja_id = l.id where l.id like '"+lojaEscolhidaId+"'  order by l.nome,vendida desc";
		Query query = manager.createNativeQuery(Stringquery);
		List<Object[]> listaDeGeral = query.getResultList();
		
		for (Object[] geral : listaDeGeral) {
			String produto = (String)geral[0];
			String loja = (String)geral[1];
			Integer vendida = ((BigDecimal)geral[2]).intValue();
	
			
			RelatorioGeral rg =new RelatorioGeral();
			rg.setProduto(produto);
			rg.setLoja(loja);
			rg.setVendida(vendida);
			relatorioGeral.add(rg);
			
		}

		return relatorioGeral;
		
		
	}

	public List<Produto> merge(List<Produto> p) {
		List<Produto> produtos = new ArrayList<>();
		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacaoDAO.getMomentoDaUltimaAtualizacao();
		
		for (Produto produto : p) {
			produto.setSincronizado(1);
			produto.setMomentoDaUltimaAtualizacao(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao());
			produtos.add(manager.merge(produto));
		}
		return produtos;

	}
}