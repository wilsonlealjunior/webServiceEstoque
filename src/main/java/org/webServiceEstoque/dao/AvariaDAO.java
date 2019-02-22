package org.webServiceEstoque.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.webServiceEstoque.models.Avaria;
import org.webServiceEstoque.models.Loja;
import org.webServiceEstoque.models.MomentoDaUltimaAtualizacao;
import org.webServiceEstoque.models.RelatorioAvaria;
import org.webServiceEstoque.models.Venda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
@Transactional
public class AvariaDAO {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	MomentoDaUltimaAtualizacaoDAO momentoDaUltimaAtualizacaoDAO;
	@Autowired
	LojaDAO lojaDAO;

	public void gravar(Avaria avaria) {

		avaria.setMomentoDaUltimaAtualizacao(new Date());
		manager.persist(avaria);
	}

	@SuppressWarnings("unchecked")
	public List<Avaria> listarTodos() {
		Query query = manager.createQuery("from " + "Avaria where desativado=0 order by momentoDaUltimaAtualizacao desc");
		List<Avaria> listaDeAvaria = query.getResultList();
		// Gson gson = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		// GsonBuilder g = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		// Gson gson = new Gson();
		// String avariasGson = gson.toJson(listaDeAvaria);
		return listaDeAvaria;
	}

	public List<Avaria> novosRegistro(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(date);
		String Stringquery = " from Avaria where momentoDaUltimaAtualizacao > '" + s + "'"
				+ " order by momentoDaUltimaAtualizacao desc ";
		Query query = manager.createQuery(Stringquery);
		List<Avaria> listaDeAvaria = query.getResultList();

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		// GsonBuilder g = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		// Gson gson = new Gson();
		String avariasGson = gson.toJson(listaDeAvaria);
		return listaDeAvaria;

	}

	public List<RelatorioAvaria> relatorio(String de, String ate, String idLoja) {
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
		List<RelatorioAvaria> relatorioAvarias = new ArrayList<>();
		String Stringquery = "SELECT a.data, l.nome AS loja, aep.quantidade, ep.precoDeCompra,p.nome, aep.quantidade*ep.precoDeCompra as prejuizo FROM Avaria a INNER JOIN Loja l ON l.id = a.idLoja INNER JOIN ItemAvaria aep ON aep.idAvaria = a.id INNER JOIN EntradaProduto ep ON aep.idEntradaProduto = ep.id INNER JOIN Produto p ON ep.produto_id = p.id where a.idLoja like '"
				+ idLoja + "' and a.data between '" + de + "' and '" + ate + "' and a.desativado = 0 order by l.nome, a.data desc;";
		Query query = manager.createNativeQuery(Stringquery);
		List<Object[]> listaDeAvaria = query.getResultList();

		for (Object[] avaria : listaDeAvaria) {
			Date data = (Date) avaria[0];
			Integer quantidade = (Integer) avaria[2];
			String loja = (String) avaria[1];
			Double precoDecompra = (Double) avaria[3];
			String nome = (String) avaria[4];
			Double prejuizo = (Double) avaria[5];
			RelatorioAvaria rs = new RelatorioAvaria();
			rs.setData(data);
			rs.setLoja(loja);
			rs.setQuantidade(quantidade);
			rs.setPrecoDeCompra(precoDecompra);
			rs.setNome(nome);
			rs.setPrejuizo(prejuizo);

			relatorioAvarias.add(rs);

		}

		return relatorioAvarias;

	}

	public List<Avaria> merge(List<Avaria> a) {
		List<Avaria> avarias = new ArrayList<>();

		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacaoDAO
				.getMomentoDaUltimaAtualizacao();
		for (Avaria avaria : a) {
			avaria.setSincronizado(1);
			avaria.setMomentoDaUltimaAtualizacao(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao());
			avarias.add(manager.merge(avaria));
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		// GsonBuilder g = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		// Gson gson = new Gson();
		String avariasGson = gson.toJson(avarias);
		return avarias;

	}

	public Avaria buscarPorId(String id) {
		return manager.find(Avaria.class, id);
	}

}
