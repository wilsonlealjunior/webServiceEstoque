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
import org.webServiceEstoque.models.EntradaProduto;
import org.webServiceEstoque.models.MomentoDaUltimaAtualizacao;
import org.webServiceEstoque.models.Produto;
import org.webServiceEstoque.models.RelatorioAvaria;
import org.webServiceEstoque.models.RelatorioEntradaProduto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
@Transactional
public class EntradaProdutoDAO {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	MomentoDaUltimaAtualizacaoDAO momentoDaUltimaAtualizacaoDAO;

	public void gravar(EntradaProduto EntradaProduto) {
		manager.persist(EntradaProduto);

	}

	@SuppressWarnings("unchecked")
	public List<EntradaProduto> listarTodos() {
		Query query = manager.createQuery("from " + "EntradaProduto where desativado=0");
		List<EntradaProduto> listaDeEntradaProduto = query.getResultList();
		return listaDeEntradaProduto;
	}

	public List<EntradaProduto> novosRegistro(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(date);
		String Stringquery = " from EntradaProduto where momentoDaUltimaAtualizacao > '" + s + "'"
				+ " order by momentoDaUltimaAtualizacao desc ";
		Query query = manager.createQuery(Stringquery);
		List<EntradaProduto> listaDeEntradaProduto = query.getResultList();

		return listaDeEntradaProduto;

	}

	public List<EntradaProduto> merge(List<EntradaProduto> l) {
		List<EntradaProduto> EntradaProdutos = new ArrayList<>();

		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacaoDAO
				.getMomentoDaUltimaAtualizacao();

		for (EntradaProduto EntradaProduto : l) {
			EntradaProduto.setSincronizado(1);
			EntradaProduto.setMomentoDaUltimaAtualizacao(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao());
			EntradaProdutos.add(manager.merge(EntradaProduto));
		}

		return EntradaProdutos;

	}

	public List<RelatorioEntradaProduto> relatorio(String de, String ate, String lojaEscolhidaId) {
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
		
		List<RelatorioEntradaProduto> relatorioEntradaProdutos = new ArrayList<>();
		 formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String Stringquery = "SELECT e.data,p.nome,e.quantidade,e.precoDeCompra, l.nome as loja FROM  EntradaProduto e inner join Produto p on e.produto_id=p.id inner join Loja l on p.loja_id=l.id where data between '"+de+ "'and '"+ate+"' and  e.desativado = 0 order by  loja, e.data desc;";
		Query query = manager.createNativeQuery(Stringquery);
		List<Object[]> listaDeEntradaProduto = query.getResultList();
		

		for (Object[] avaria : listaDeEntradaProduto) {
		

			Date data = (Date) avaria[0];
			String nome = (String) avaria[1];
			Integer quantidade = (Integer) avaria[2];
			Double precoDeCompra = (Double) avaria[3];
			String loja = (String) avaria[4];
			RelatorioEntradaProduto rs = new RelatorioEntradaProduto();
			rs.setData(data);
			rs.setLoja(loja);
			rs.setPrecoDeCompra(precoDeCompra);
			rs.setNome(nome);
			rs.setQuantidade(quantidade);
			relatorioEntradaProdutos.add(rs);
		

		}

		return relatorioEntradaProdutos;

	}
}