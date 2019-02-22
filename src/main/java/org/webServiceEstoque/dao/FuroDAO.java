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
import org.webServiceEstoque.models.Furo;
import org.webServiceEstoque.models.MomentoDaUltimaAtualizacao;
import org.webServiceEstoque.models.RelatorioFuro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
@Transactional
public class FuroDAO {
	@PersistenceContext
	private EntityManager manager;
	@Autowired
	MomentoDaUltimaAtualizacaoDAO momentoDaUltimaAtualizacaoDAO;
	@Autowired
	LojaDAO lojaDAO;

	public void gravar(Furo furo) {

		furo.setMomentoDaUltimaAtualizacao(new Date());
		manager.persist(furo);
	}

	@SuppressWarnings("unchecked")
	public List<Furo> listarTodos() {
		Query query = manager.createQuery("from " + "Furo where desativado=0 order by momentoDaUltimaAtualizacao desc");
		List<Furo> listaDeFuro = query.getResultList();
		// Gson gson = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		// GsonBuilder g = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		// Gson gson = new Gson();
		// String avariasGson = gson.toJson(listaDeAvaria);
		return listaDeFuro;
	}

	public List<Furo> novosRegistro(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(date);
		String Stringquery = " from Furo where momentoDaUltimaAtualizacao > '" + s + "'"
				+ " order by momentoDaUltimaAtualizacao desc ";
		Query query = manager.createQuery(Stringquery);
		List<Furo> listaDeFuro = query.getResultList();

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		// GsonBuilder g = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		// Gson gson = new Gson();
		// String avariasGson = gson.toJson(listaDeFuro);
		return listaDeFuro;

	}

	public List<RelatorioFuro> relatorio(String idLoja, String funcionarioId, String de, String ate) {
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
		List<RelatorioFuro> relatorioFuros = new ArrayList<>();
		formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String Stringquery = "SELECT a.data,a.idusuario as funcionario, l.nome AS loja, aep.quantidade, aep.precoDeVenda,p.nome,"
				+ " aep.quantidade*aep.precoDeVenda as valor FROM Furo a INNER JOIN Loja l ON l.id = a.idLoja INNER JOIN ItemFuro"
				+ " aep ON aep.idFuro = a.id INNER JOIN EntradaProduto ep ON aep.idEntradaProduto = ep.id INNER JOIN Produto p ON"
				+ " ep.produto_id = p.id where a.idLoja like '" + idLoja + "' and a.idusuario like '" + funcionarioId
				+ "' and a.data between '" + de + "' and '" + ate
				+ "' and a.desativado = 0 order by funcionario, a.data desc;";
		Query query = manager.createNativeQuery(Stringquery);
		List<Object[]> listaDeFuro = query.getResultList();

		for (Object[] furo : listaDeFuro) {

			Date data = (Date) furo[0];
			String usuario = (String) furo[1];
			Integer quantidade = (Integer) furo[3];
			String loja = (String) furo[2];
			Double precoDeVenda = (Double) furo[4];
			String nome = (String) furo[5];
			Double valor = (Double) furo[6];
			RelatorioFuro rs = new RelatorioFuro();
			rs.setData(data);
			rs.setLoja(loja);
			rs.setFuncionario(usuario);
			rs.setQuantidade(quantidade);
			rs.setPrecoDeVenda(precoDeVenda);
			rs.setNome(nome);
			rs.setValor(valor);

			relatorioFuros.add(rs);

		}

		return relatorioFuros;

	}

	public List<Furo> merge(List<Furo> a) {
		List<Furo> furos = new ArrayList<>();

		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacaoDAO
				.getMomentoDaUltimaAtualizacao();
		for (Furo furo : a) {
			furo.setSincronizado(1);
			furo.setMomentoDaUltimaAtualizacao(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao());
			furos.add(manager.merge(furo));
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		// GsonBuilder g = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		// Gson gson = new Gson();
		String avariasGson = gson.toJson(furos);
		return furos;

	}

	public Furo buscarPorId(String id) {
		return manager.find(Furo.class, id);
	}

}
