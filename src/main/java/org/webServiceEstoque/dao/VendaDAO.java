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
import org.webServiceEstoque.models.RelatorioVendaFuncionario;
import org.webServiceEstoque.models.Venda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
@Transactional
public class VendaDAO {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	MomentoDaUltimaAtualizacaoDAO momentoDaUltimaAtualizacaoDAO;

	public void gravar(Venda venda) {
		venda.setMomentoDaUltimaAtualizacao(new Date());
		manager.persist(venda);
	}

	@SuppressWarnings("unchecked")
	public List<Venda> listarTodos() {
		Query query = manager.createQuery("from " + "Venda where desativado=0 order by nomeVendedor");
		List<Venda> listaDeVenda = query.getResultList();

		return listaDeVenda;

	}

	public List<Venda> novosRegistro(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(date);
		String Stringquery = " from Venda where momentoDaUltimaAtualizacao > '" + s + "'"
				+ " order by momentoDaUltimaAtualizacao desc ";
		Query query = manager.createQuery(Stringquery);
		List<Venda> listaDeVenda = query.getResultList();
		return listaDeVenda;

	}

	public List<Venda> Relatorio(String formaDePagamentoEscolhido, String lojaEscolhidaId, String usuarioEscolhido,
			String de, String ate) {
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
		List<Venda> relatorioVendas = new ArrayList<>();

		String Stringquery = " from Venda where formaDePagamento like '" + formaDePagamentoEscolhido
				+ "' and nomeVendedor like '" + usuarioEscolhido + "' and idLoja like '" + lojaEscolhidaId
				+ "' and dataDaVenda between '" + de + "' and '" + ate
				+ "' and desativado = 0  order by nomeVendedor, formaDePagamento, dataDaVenda desc";
		Query query = manager.createQuery(Stringquery);
		List<Venda> listaDeVenda = query.getResultList();

		return listaDeVenda;

	}

	public List<RelatorioVendaFuncionario> RelatorioVendaFuncionario(String formaDePagamentoEscolhido,
			String lojaEscolhidaId, String usuarioEscolhido, String de, String ate) {
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
		List<RelatorioVendaFuncionario> relatorioVendaFuncionario = new ArrayList<>();

		String Stringquery = "SELECT v.id, v.formaDePagamento, v.dataDaVenda, v.lucro, v.nomeVendedor,"
				+ " v.totalDinheiro, vep.precoDeVenda, vep.quantidadeVendida, p.nome, (vep.quantidadeVendida*vep.precoDeVenda)"
				+ " as totalUnitario, l.nome as loja, ifnull(v.totalCartao,0) as totalCartao FROM venda v inner join"
				+ " loja l on l.id=v.idLoja INNER JOIN itemvenda vep ON v.id = vep.idVenda INNER JOIN"
				+ " produto p ON p.id = vep.idProduto WHERE v.formaDePagamento like '%" + formaDePagamentoEscolhido
				+ "' and v.nomeVendedor like '" + usuarioEscolhido + "' and v.dataDaVenda between '" + de + "'and '"
				+ ate + "' and v.desativado=0 and l.id like '" + lojaEscolhidaId
				+ "'  ORDER BY nomeVendedor , formaDePagamento , dataDaVenda DESC";
		Query query = manager.createNativeQuery(Stringquery);
		List<Object[]> listaDeEntradaProduto = query.getResultList();

		for (Object[] avaria : listaDeEntradaProduto) {
			String id = (String) avaria[0];
			String formaDePagamento = (String) avaria[1];
			Date dataDaVenda = (Date) avaria[2];
			Double lucro = (Double) avaria[3];
			String nomeVendedor = (String) avaria[4];
			Double totalDinheiro = (Double) avaria[5];
			Double precoDeVenda = (Double) avaria[6];
			Integer quantidadeVendida = (Integer) avaria[7];
			String nome = (String) avaria[8];
			Double totalUnitario = (Double) avaria[9];
			String loja = (String) avaria[10];
			Double totalCartao = (Double) avaria[11];

			RelatorioVendaFuncionario rvf = new RelatorioVendaFuncionario();
			rvf.setId(id);
			rvf.setFormaDePagamento(formaDePagamento);
			rvf.setDataDaVenda(dataDaVenda);
			rvf.setLucro(lucro);
			rvf.setNomeVendedor(nomeVendedor);

			rvf.setPrecoDeVenda(precoDeVenda);
			rvf.setQuantidadeVendida(quantidadeVendida);
			rvf.setNome(nome);
			rvf.setTotalUnitario(totalUnitario);
			rvf.setLoja(loja);
			rvf.setTotalDinheiro(totalDinheiro);
			rvf.setTotalCartao(totalCartao);

			relatorioVendaFuncionario.add(rvf);

		}

		return relatorioVendaFuncionario;

	}

	public List<Venda> merge(List<Venda> v) {
		List<Venda> vendas = new ArrayList<>();

		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacaoDAO
				.getMomentoDaUltimaAtualizacao();
		for (Venda venda : v) {
			venda.setSincronizado(1);
			venda.setMomentoDaUltimaAtualizacao(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao());
			vendas.add(manager.merge(venda));
		}
		return vendas;

	}

	public Venda buscarPorId(String id) {
		return manager.find(Venda.class, id);
	}

}
