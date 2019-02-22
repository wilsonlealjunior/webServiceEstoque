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
import org.webServiceEstoque.models.RelatorioGeral;
import org.webServiceEstoque.models.Venda;
import org.webServiceEstoque.models.EntradaProduto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
@Transactional
public class LojaDAO {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	MomentoDaUltimaAtualizacaoDAO momentoDaUltimaAtualizacaoDAO;

	public void gravar(Loja loja) {
		loja.setMomentoDaUltimaAtualizacao(new Date());
		manager.persist(loja);
	}

	@SuppressWarnings("unchecked")
	public List<Loja> listarTodos() {
		Query query = manager.createQuery("from " + "Loja order by momentoDaUltimaAtualizacao desc");
		List<Loja> lojas = query.getResultList();
		return lojas;

	}

	public List<Loja> novosRegistro(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(date);
		String Stringquery = " from Loja where momentoDaUltimaAtualizacao > '" + s + "'"
				+ " order by momentoDaUltimaAtualizacao desc ";
		Query query = manager.createQuery(Stringquery);
		List<Loja> lojas = query.getResultList();

		return lojas;

	}

	public List<Loja> merge(List<Loja> l) {
		List<Loja> lojas = new ArrayList<>();

		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacaoDAO
				.getMomentoDaUltimaAtualizacao();

		for (Loja loja : l) {
			loja.setSincronizado(1);
			loja.setMomentoDaUltimaAtualizacao(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao());
			lojas.add(manager.merge(loja));
		}

		return lojas;

	}

	public Loja buscarPorId(String id) {
		return manager.find(Loja.class, id);
	}

	public List<RelatorioGeral> Relatorio(String lojaEscolhidaId, String de, String ate) {
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

		String Stringquery = "SELECT p.nome AS produto, l.nome AS Loja, IFNULL((SELECT SUM(vep.quantidadeVendida) FROM Venda AS v INNER JOIN ItemVenda vep ON vep.idVenda = v.id INNER JOIN EntradaProduto ep ON ep.id = vep.idEntradaProduto WHERE p.id = vep.idProduto and v.desativado = 0 and v.dataDaVenda between '"
				+ de + "' and '" + ate
				+ "'),0) AS vendida, IFNULL((SELECT  SUM(aep.quantidade) AS avaria FROM Avaria a  INNER JOIN ItemAvaria aep ON aep.idAvaria = a.id INNER JOIN EntradaProduto ep ON ep.id = aep.idEntradaProduto WHERE  p.id = ep.produto_id and a.desativado = 0 and a.data between '"
				+ de + "' and '" + ate
				+ "' ),0) AS avaria, IFNULL((SELECT  SUM(ep.quantidade) FROM EntradaProduto ep WHERE p.id = ep.produto_id and ep.desativado = 0 and ep.data between '"
				+ de + "' and '" + ate
				+ "'),0) AS Entrada,IFNULL((SELECT SUM(mp.quantidade) FROM MovimentacaoProduto mp WHERE  p.id = mp.idProduto and mp.data between '"
				+ de + "' and '" + ate
				+ "'),0) AS movimentada, IFNULL((SELECT SUM(fep.quantidade) AS Furo FROM furo f INNER JOIN ItemFuro fep ON fep.idFuro = f.id INNER JOIN EntradaProduto ep ON fep.idEntradaProduto = ep.id WHERE p.id = ep.produto_id AND f.desativado = 0 and f.data BETWEEN '"
				+ de + "' AND '" + ate
				+ "'), 0) AS furo   FROM Produto AS p INNER JOIN Loja l ON p.loja_id = l.id where l.id like '"
				+ lojaEscolhidaId + "'  order by l.nome,p.nome";
		Query query = manager.createNativeQuery(Stringquery);
		List<Object[]> listaDeGeral = query.getResultList();

		for (Object[] geral : listaDeGeral) {
			String produto = (String) geral[0];
			String loja = (String) geral[1];
			Integer vendida = ((BigDecimal) geral[2]).intValue();
			Integer avaria = ((BigDecimal) geral[3]).intValue();
			Integer entrada = ((BigDecimal) geral[4]).intValue();
			Integer movimentada = ((BigDecimal) geral[5]).intValue();
			Integer furo = ((BigDecimal) geral[6]).intValue();

			RelatorioGeral rg = new RelatorioGeral();
			rg.setProduto(produto);
			rg.setLoja(loja);
			rg.setVendida(vendida);
			rg.setEntrada(entrada);
			rg.setMovimentada(movimentada);
			rg.setAvaria(avaria);
			rg.setFuro(furo);
			relatorioGeral.add(rg);

		}

		return relatorioGeral;

	}

	public List<RelatorioGeral> RelatorioFuncionario(String lojaEscolhidaId, String usuarioEscolhido, String de,
			String ate) {
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

		String Stringquery = "SELECT p.nome AS produto, l.nome AS Loja, IFNULL((SELECT SUM(vep.quantidadeVendida) FROM Venda AS v INNER JOIN ItemVenda vep ON vep.idVenda = v.id INNER JOIN EntradaProduto ep ON ep.id = vep.idEntradaProduto WHERE v.nomeVendedor like '"
				+ usuarioEscolhido + "' and p.id = vep.idProduto and v.desativado = 0 and v.dataDaVenda between '" + de
				+ "' and '" + ate
				+ "'),0) AS vendida, IFNULL((SELECT  SUM(aep.quantidade) AS avaria FROM Avaria a  INNER JOIN ItemAvaria aep ON aep.idAvaria = a.id INNER JOIN EntradaProduto ep ON ep.id = aep.idEntradaProduto WHERE  p.id = ep.produto_id and a.desativado = 0 and a.data between '"
				+ de + "' and '" + ate
				+ "' ),0) AS avaria, IFNULL((SELECT  SUM(ep.quantidade) FROM EntradaProduto ep WHERE p.id = ep.produto_id and ep.desativado = 0 and ep.data between '"
				+ de + "' and '" + ate
				+ "'),0) AS Entrada,IFNULL((SELECT SUM(mp.quantidade) FROM MovimentacaoProduto mp WHERE  p.id = mp.idProduto and mp.data between '"
				+ de + "' and '" + ate
				+ "'),0) AS movimentada, IFNULL((SELECT SUM(fep.quantidade) AS Furo FROM furo f INNER JOIN ItemFuro fep ON fep.idFuro = f.id INNER JOIN EntradaProduto ep ON fep.idEntradaProduto = ep.id WHERE p.id = ep.produto_id AND f.desativado = 0 and f.data BETWEEN '"
				+ de + "' AND '" + ate
				+ "'), 0) AS furo   FROM Produto AS p INNER JOIN Loja l ON p.loja_id = l.id where l.id like '"
				+ lojaEscolhidaId + "'  order by l.nome,p.nome";
		Query query = manager.createNativeQuery(Stringquery);
		List<Object[]> listaDeGeral = query.getResultList();

		for (Object[] geral : listaDeGeral) {
			String produto = (String) geral[0];
			String loja = (String) geral[1];
			Integer vendida = ((BigDecimal) geral[2]).intValue();
			Integer avaria = ((BigDecimal) geral[3]).intValue();
			Integer entrada = ((BigDecimal) geral[4]).intValue();
			Integer movimentada = ((BigDecimal) geral[5]).intValue();
			Integer furo = ((BigDecimal) geral[6]).intValue();

			RelatorioGeral rg = new RelatorioGeral();
			rg.setProduto(produto);
			rg.setLoja(loja);
			rg.setVendida(vendida);
			rg.setEntrada(entrada);
			rg.setMovimentada(movimentada);
			rg.setAvaria(avaria);
			rg.setFuro(furo);
			relatorioGeral.add(rg);

		}

		return relatorioGeral;

	}

	public List<EstoqueAtual> estoqueAtual(String lojaId) {
		String sql = "select p.nome, p.quantidade, p.preco,l.nome as loja from produto  p inner join loja l on  p.loja_id=l.id where l.id like '"
				+ lojaId + "' order by nome";
		Query query = manager.createNativeQuery(sql);
		List<Object[]> estoques = query.getResultList();
		List<EstoqueAtual> estoqueList = new ArrayList<>();
		for (Object[] estoque : estoques) {
			EstoqueAtual estoqueAtual = new EstoqueAtual();
			estoqueAtual.nome = (String) estoque[0];
			estoqueAtual.quantidade = (Integer) estoque[1];
			estoqueAtual.preco = (Double) estoque[2];
			estoqueAtual.loja = (String) estoque[3];
			estoqueList.add(estoqueAtual);
		}
		return estoqueList;

	}

	public class EstoqueAtual {
		String nome;
		Integer quantidade;
		Double preco;
		String loja;
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public Integer getQuantidade() {
			return quantidade;
		}
		public void setQuantidade(Integer quantidade) {
			this.quantidade = quantidade;
		}
		public Double getPreco() {
			return preco;
		}
		public void setPreco(Double preco) {
			this.preco = preco;
		}
		public String getLoja() {
			return loja;
		}
		public void setLoja(String loja) {
			this.loja = loja;
		}
		

	}

}
