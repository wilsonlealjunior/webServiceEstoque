package org.webServiceEstoque.dao;

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
import org.webServiceEstoque.models.MomentoDaUltimaAtualizacao;
import org.webServiceEstoque.models.MovimentacaoProduto;
import org.webServiceEstoque.models.RelatorioAvaria;
import org.webServiceEstoque.models.RelatorioMovimentacaoProduto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
@Transactional
public class MovimentacaoProdutoDAO {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	MomentoDaUltimaAtualizacaoDAO momentoDaUltimaAtualizacaoDAO;

	public void gravar(MovimentacaoProduto movimentacaoProduto) {
		manager.persist(movimentacaoProduto);

	}

	@SuppressWarnings("unchecked")
	public List<MovimentacaoProduto> listarTodos() {
		Query query = manager.createQuery("from " + "MovimentacaoProduto where desativado=0");
		List<MovimentacaoProduto> listaDeMovimentacaoProduto = query.getResultList();
		return listaDeMovimentacaoProduto;
	}

	public List<MovimentacaoProduto> novosRegistro(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(date);
		String Stringquery = " from MovimentacaoProduto where momentoDaUltimaAtualizacao > '" + s + "'"
				+ " order by momentoDaUltimaAtualizacao desc ";
		Query query = manager.createQuery(Stringquery);
		List<MovimentacaoProduto> listaDeMovimentacaoProduto = query.getResultList();

		return listaDeMovimentacaoProduto;

	}

	public List<MovimentacaoProduto> merge(List<MovimentacaoProduto> m) {
		List<MovimentacaoProduto> movimentacaoProdutos = new ArrayList<>();

		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacaoDAO
				.getMomentoDaUltimaAtualizacao();
		for (MovimentacaoProduto mp : m) {
			mp.setSincronizado(1);
			mp.setMomentoDaUltimaAtualizacao(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao());
			movimentacaoProdutos.add(manager.merge(mp));
		}
		return movimentacaoProdutos;

	}

	public List<RelatorioMovimentacaoProduto> relatorio(String de, String ate) {
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
		List<RelatorioMovimentacaoProduto> relatorioMovimentacao = new ArrayList<>();
		formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String Stringquery = "SELECT p.nome as nomeProd,mp.data,mp.quantidade,lDe.nome as lojaDe,lPara.nome as lojaPara FROM MovimentacaoProduto mp inner join Produto p on mp.idProduto=p.id inner join Loja lDe on lDe.id=mp.idLojaDe inner join Loja lPara on lpara.id=mp.idLojaPara where data between '"
				+ de + "'and '" + ate + "' and mp.desativado = 0 order by mp.data desc";
		Query query = manager.createNativeQuery(Stringquery);
		List<Object[]> listaDeMovimentacao = query.getResultList();

		for (Object[] movimentacao : listaDeMovimentacao) {

			String nomeProd = (String) movimentacao[0];
			Date data = (Date) movimentacao[1];
			Integer quantidade = (Integer) movimentacao[2];
			String lojaDe = (String) movimentacao[3];
			String lojapara = (String) movimentacao[4];
			RelatorioMovimentacaoProduto rs = new RelatorioMovimentacaoProduto();
			rs.setData(data);
			rs.setNomeProd(nomeProd);
			rs.setQuantidade(quantidade);
			rs.setLojaDe(lojaDe);
			rs.setLojaPara(lojapara);

			relatorioMovimentacao.add(rs);

		}

		return relatorioMovimentacao;
	}
}