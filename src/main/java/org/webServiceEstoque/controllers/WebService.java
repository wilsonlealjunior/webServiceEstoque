package org.webServiceEstoque.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.webServiceEstoque.dao.AvariaDAO;
import org.webServiceEstoque.dao.EntradaProdutoDAO;
import org.webServiceEstoque.dao.FuroDAO;
import org.webServiceEstoque.dao.LojaDAO;
import org.webServiceEstoque.dao.MomentoDaUltimaAtualizacaoDAO;
import org.webServiceEstoque.dao.MovimentacaoProdutoDAO;
import org.webServiceEstoque.dao.ProdutoDAO;
import org.webServiceEstoque.dao.UsuarioDAO;
import org.webServiceEstoque.dao.VendaDAO;
import org.webServiceEstoque.models.Avaria;
import org.webServiceEstoque.models.EntradaProduto;
import org.webServiceEstoque.models.Furo;
import org.webServiceEstoque.models.Loja;
import org.webServiceEstoque.models.MomentoDaUltimaAtualizacao;
import org.webServiceEstoque.models.MovimentacaoProduto;
import org.webServiceEstoque.models.ObjetosSink;
import org.webServiceEstoque.models.Produto;
import org.webServiceEstoque.models.RelatorioAvaria;
import org.webServiceEstoque.models.RelatorioEntradaProduto;
import org.webServiceEstoque.models.RelatorioFuro;
import org.webServiceEstoque.models.RelatorioGeral;
import org.webServiceEstoque.models.RelatorioMovimentacaoProduto;
import org.webServiceEstoque.models.RelatorioVendaFuncionario;
import org.webServiceEstoque.models.Teste;
import org.webServiceEstoque.models.Usuario;
import org.webServiceEstoque.models.Venda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import util.GenerateHashPasswordUtil;
import util.GeradorRelatorio;

@RestController
public class WebService {

	@Autowired
	VendaDAO vendaDAO;

	@Autowired
	MomentoDaUltimaAtualizacaoDAO momentoDaUltimaAtualizacaoDAO;

	@Autowired
	UsuarioDAO usuatioDAO;

	@Autowired
	AvariaDAO avariaDAO;
	@Autowired
	ProdutoDAO produtoDAO;
	@Autowired
	EntradaProdutoDAO entradaProdutoDAO;
	@Autowired
	LojaDAO lojaDAO;
	@Autowired
	FuroDAO furoDAO;

	@Autowired
	MovimentacaoProdutoDAO movimentacaoProdutoDAO;

	@RequestMapping(value = "/adicionarProduto", method = RequestMethod.POST)
	public String adicionarProduto(@RequestBody Produto p) {

		System.out.println(p.getId());
		System.out.println(p.getNome());
		System.out.println(p.getQuantidade());
		// System.out.println(p.getLocalizacao().getNome());
		EntradaProduto entradaProduto = new EntradaProduto();
		entradaProduto.setProduto(p);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entradaProduto.setData(new Date());
		entradaProduto.setQuantidade(10);
		entradaProduto.setPrecoDeCompra(50);
		Date data = new Date();
		p.setMomentoDaUltimaAtualizacao(data);

		produtoDAO.gravar(p);
		// localizacaoDAO.gravar(p.getLocalizacao());

		return "produto adicionado com sucesso";

	}

	@RequestMapping(value = "/adicionarLoja", method = RequestMethod.POST)
	public String adicionarProduto(@RequestBody Loja l) {

		System.out.println(l.getNome());

		lojaDAO.gravar(l);
		// loteDAO.gravar(lote);

		return "Loja adicionado com sucesso";

	}

	@RequestMapping(value = "/teste", method = RequestMethod.POST)
	public String teste(@RequestBody Teste t) {

		System.out.println(t.getData().toString());
		Gson gson = new Gson();

		List<Teste> teste = new ArrayList<>();
		teste.add(t);

		return gson.toJson(teste);

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String teste(HttpSession session, String username, String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(username);

		Usuario usuario = usuatioDAO.loadUserByUsernameAndPass(username,
				GenerateHashPasswordUtil.generateHash(password));

		if (usuario != null)
			session.setAttribute("usuarioLogado", usuario);

		Gson gson = new Gson();
		return gson.toJson(usuario);

	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String teste(HttpSession session, String username) {

		session.removeAttribute("usuarioLogado-" + username);
		Usuario u = null;
		Gson gson = new Gson();

		return gson.toJson(u);

	}

	@RequestMapping(value = "/listarProdutos", method = RequestMethod.GET)
	public String listarProdutos() {
		List<Produto> produtos = produtoDAO.listarTodos();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String produtosGson = gson.toJson(produtos);
		return produtosGson;

	}

	@RequestMapping(value = "/listarLojas", method = RequestMethod.GET)
	public String listarLojas() {
		List<Loja> lojas = lojaDAO.listarTodos();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String lojasGson = gson.toJson(lojas);
		return lojasGson;

	}

	@RequestMapping(value = "/relatorioVendas", method = RequestMethod.POST)
	public void relatorioVendas(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		String formaDePagamentoEscolhido = request.getParameter("formaDePagamentoEscolhido");

		String lojaEscolhidaId = request.getParameter("lojaEscolhidaId");
		String usuarioEscolhido = request.getParameter("usuarioEscolhido");
		String de = request.getParameter("de");
		String ate = request.getParameter("ate");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/vendas_descricao.jasper");
		// JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.jrxml"));
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<RelatorioVendaFuncionario> vendas = vendaDAO.RelatorioVendaFuncionario(formaDePagamentoEscolhido,
				lojaEscolhidaId, usuarioEscolhido, de, ate);
		JRDataSource dataSource = new JRBeanCollectionDataSource(vendas, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

	@RequestMapping(value = "/relatorioVendasFuncionario", method = RequestMethod.POST)
	public void relatorioVendasFuncionario(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		String formaDePagamentoEscolhido = request.getParameter("formaDePagamentoEscolhido");

		String lojaEscolhidaId = request.getParameter("lojaEscolhidaId");
		String usuarioEscolhido = request.getParameter("usuarioEscolhido");
		String de = request.getParameter("de");
		String ate = request.getParameter("ate");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/vendas_descricao_Funcionario.jasper");
		// JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.jrxml"));
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<RelatorioVendaFuncionario> vendas = vendaDAO.RelatorioVendaFuncionario(formaDePagamentoEscolhido,
				lojaEscolhidaId, usuarioEscolhido, de, ate);
		JRDataSource dataSource = new JRBeanCollectionDataSource(vendas, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

	@RequestMapping(value = "/relatorioGeral", method = RequestMethod.POST)
	public void relatorioGeral(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		//
		String lojaEscolhidaId = request.getParameter("lojaEscolhidaId");
		// String usuarioEscolhido = request.getParameter("usuarioEscolhido");
		String de = request.getParameter("de");
		String ate = request.getParameter("ate");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/geral.jasper");
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<RelatorioGeral> geral = lojaDAO.Relatorio(lojaEscolhidaId, de, ate);
		JRDataSource dataSource = new JRBeanCollectionDataSource(geral, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

	@RequestMapping(value = "/relatorioGeralFuncionario", method = RequestMethod.POST)
	public void relatorioGeralFuncionario(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		//
		String lojaEscolhidaId = request.getParameter("lojaEscolhidaId");
		String usuarioEscolhido = request.getParameter("usuarioEscolhido");
		String de = request.getParameter("de");
		String ate = request.getParameter("ate");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/geral.jasper");
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<RelatorioGeral> geral = lojaDAO.RelatorioFuncionario(lojaEscolhidaId, usuarioEscolhido, de, ate);
		JRDataSource dataSource = new JRBeanCollectionDataSource(geral, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

	@RequestMapping(value = "/relatorioProdutoMaisVendido", method = RequestMethod.POST)
	public void relatorioProdutoMaisVendido(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {

		// String formaDePagamentoEscolhido =
		// request.getParameter("formaDePagamentoEscolhido");
		//
		String lojaEscolhidaId = request.getParameter("lojaEscolhidaId");
		String usuarioEscolhido = request.getParameter("usuarioEscolhido");
		String de = request.getParameter("de");
		String ate = request.getParameter("ate");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/produtoMaisVendido.jasper");
		// JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.jrxml"));
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<RelatorioGeral> geral = produtoDAO.Relatorio(lojaEscolhidaId, usuarioEscolhido, de, ate);
		JRDataSource dataSource = new JRBeanCollectionDataSource(geral, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

	@RequestMapping(value = "/relatorioAvarias", method = RequestMethod.POST)
	public void relatorioAvarias(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		String lojaEscolhidaId = request.getParameter("lojaEscolhidaId");
		String de = request.getParameter("de");
		String ate = request.getParameter("ate");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/avaria.jasper");
		// JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.jrxml"));
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<RelatorioAvaria> avarias = avariaDAO.relatorio(de, ate, lojaEscolhidaId);

		JRDataSource dataSource = new JRBeanCollectionDataSource(avarias, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

	@RequestMapping(value = "/relatorioFuros", method = RequestMethod.POST)
	public void relatorioFuros(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		String lojaEscolhidaId = request.getParameter("lojaId");
		String funcionarioId = request.getParameter("funcionarioId");
		String de = request.getParameter("de");
		String ate = request.getParameter("ate");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/furo.jasper");
		// JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.jrxml"));
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<RelatorioFuro> furos = furoDAO.relatorio(lojaEscolhidaId, funcionarioId, de, ate);

		JRDataSource dataSource = new JRBeanCollectionDataSource(furos, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

	@RequestMapping(value = "/relatorioMovimentacaoProduto", method = RequestMethod.POST)
	public void relatorioMovimentacaoProduto(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		String de = request.getParameter("de");
		String ate = request.getParameter("ate");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/movimentacao_de_produto.jasper");
		// JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.jrxml"));
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<RelatorioMovimentacaoProduto> movimentacoes = movimentacaoProdutoDAO.relatorio(de, ate);

		JRDataSource dataSource = new JRBeanCollectionDataSource(movimentacoes, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

	@RequestMapping(value = "/relatorioEntradaProdutos", method = RequestMethod.POST)
	public void relatorioEntradaProdutos(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		String lojaEscolhidaId = request.getParameter("lojaEscolhidaId");
		String de = request.getParameter("de");
		String ate = request.getParameter("ate");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/entradaDeProduto.jasper");
		// JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.jrxml"));
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<RelatorioEntradaProduto> entradaProdutos = entradaProdutoDAO.relatorio(de, ate, lojaEscolhidaId);

		JRDataSource dataSource = new JRBeanCollectionDataSource(entradaProdutos, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

	// ver o que tem de diferente no servidor em relação ao celular e servidor
	// envia a diferenca para o celular
	@RequestMapping(value = "/diffLoja", method = RequestMethod.GET)
	public String diffLoja(@RequestHeader("datahora") String datahora) {

		List<Loja> lojas = lojaDAO.novosRegistro(new Date(datahora));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String lojasGson = gson.toJson(lojas);
		return lojasGson;

	}

	// atraves da variavel sinc é visto quem não está sincronizado então o
	// celular envia para o servidor os que nao tem no servidor
	@RequestMapping(value = "/sincronizaLojas", method = RequestMethod.PUT)
	public String sincronizaLojas(@RequestBody List<Loja> lojas) {
		List<Loja> lojasSalvas = lojaDAO.merge(lojas);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String lojasGson = gson.toJson(lojasSalvas);
		return lojasGson;

	}

	@RequestMapping(value = "/diffProduto", method = RequestMethod.GET)
	public String diffProduto(@RequestHeader("datahora") String datahora) {

		List<Produto> produtos = produtoDAO.novosRegistro(new Date(datahora));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String produtosGson = gson.toJson(produtos);
		return produtosGson;

	}

	@RequestMapping(value = "/sincronizaProdutos", method = RequestMethod.PUT, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
	public String sincronizaProdutos(@RequestBody List<Produto> produtos) {
		List<Produto> produtosSalvos = produtoDAO.merge(produtos);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String produtosGson = gson.toJson(produtosSalvos);
		return produtosGson;

	}

	@RequestMapping(value = "/listarEntradaProdutos", method = RequestMethod.GET)
	public String listarentradaProdutos() {

		List<EntradaProduto> entradaProdutos = entradaProdutoDAO.listarTodos();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String entradaProdutosGson = gson.toJson(entradaProdutos);
		return entradaProdutosGson;

	}

	// ver o que tem de diferente no servidor em relação ao celular e servidor
	// envia a diferenca para o celular
	@RequestMapping(value = "/diffEntradaProdutos", method = RequestMethod.GET)
	public String diffentradaProdutos(@RequestHeader("datahora") String datahora) {

		List<EntradaProduto> entradaProdutos = entradaProdutoDAO.novosRegistro(new Date(datahora));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String entradaProdutosGson = gson.toJson(entradaProdutos);
		return entradaProdutosGson;

	}

	// atraves da variavel sinc é visto quem não está sincronizado então o
	// celular envia para o servidor os que nao tem no servidor
	@RequestMapping(value = "/sincronizaEntradaProdutos", method = RequestMethod.PUT)
	public String diffentradaProdutos(@RequestBody List<EntradaProduto> entradaProdutos) {
		List<EntradaProduto> entradaProdutosSalvas = entradaProdutoDAO.merge(entradaProdutos);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String entradaProdutosGson = gson.toJson(entradaProdutosSalvas);
		return entradaProdutosGson;

	}

	@RequestMapping(value = "/listarVendas", method = RequestMethod.GET)
	public String listarVendas() {
		List<Venda> vendas = vendaDAO.listarTodos();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String vendasGson = gson.toJson(vendas);
		return vendasGson;

	}

	// ver o que tem de diferente no servidor em relação ao celular e servidor
	// envia a diferenca para o celular
	@RequestMapping(value = "/diffVendas", method = RequestMethod.GET)
	public String diffVendas(@RequestHeader("datahora") String datahora) {

		List<Venda> vendas = vendaDAO.novosRegistro(new Date(datahora));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String vendasGson = gson.toJson(vendas);
		return vendasGson;

	}

	// atraves da variavel sinc é visto quem não está sincronizado então o
	// celular envia para o servidor os que nao tem no servidor
	@RequestMapping(value = "/sincronizaVendas", method = RequestMethod.PUT)
	public String sincronizaVendas(@RequestBody List<Venda> vendas) {
		List<Venda> vendasSalvas = vendaDAO.merge(vendas);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String vendasGson = gson.toJson(vendasSalvas);
		return vendasGson;

	}

	@RequestMapping(value = "/listarMovimentacaoProduto", method = RequestMethod.GET)
	public String listarMovimentacaoProduto() {
		List<MovimentacaoProduto> movimentacoesProdutos = movimentacaoProdutoDAO.listarTodos();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String movimentacaoProdutoGson = gson.toJson(movimentacoesProdutos);
		return movimentacaoProdutoGson;

	}

	// ver o que tem de diferente no servidor em relação ao celular e servidor
	// envia a diferenca para o celular
	@RequestMapping(value = "/diffMovimentacaoProduto", method = RequestMethod.GET)
	public String diffMovimentacaoProduto(@RequestHeader("datahora") String datahora) {

		List<MovimentacaoProduto> movimentacoesProduto = movimentacaoProdutoDAO.novosRegistro(new Date(datahora));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String movimentacaoProdutoGson = gson.toJson(movimentacoesProduto);
		return movimentacaoProdutoGson;

	}

	// atraves da variavel sinc é visto quem não está sincronizado então o
	// celular envia para o servidor os que nao tem no servidor
	@RequestMapping(value = "/sincronizaMovimentacaoProduto", method = RequestMethod.PUT)
	public String sincronizaMovimentacaoProduto(@RequestBody List<MovimentacaoProduto> movimentacaoProduto) {
		List<MovimentacaoProduto> movimentacaoProdutoSalvas = movimentacaoProdutoDAO.merge(movimentacaoProduto);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String movimentacaoProdutoGson = gson.toJson(movimentacaoProdutoSalvas);
		return movimentacaoProdutoGson;

	}

	@RequestMapping(value = "/listarAvarias", method = RequestMethod.GET)
	public String listarAvarias() {

		List<Avaria> avarias = avariaDAO.listarTodos();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String avariasGson = gson.toJson(avarias);
		return avariasGson;

	}

	// ver o que tem de diferente no servidor em relação ao celular e servidor
	// envia a diferenca para o celular
	@RequestMapping(value = "/diffAvarias", method = RequestMethod.GET)
	public String diffAvaria(@RequestHeader("datahora") String datahora) {

		List<Avaria> avarias = avariaDAO.novosRegistro(new Date(datahora));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String avariasGson = gson.toJson(avarias);
		return avariasGson;

	}

	// atraves da variavel sinc é visto quem não está sincronizado então o
	// celular envia para o servidor os que nao tem no servidor
	@RequestMapping(value = "/sincronizaAvarias", method = RequestMethod.PUT)
	public String sincronizaAvaria(@RequestBody List<Avaria> avaria) {
		List<Avaria> avarias = avariaDAO.merge(avaria);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String avariasGson = gson.toJson(avarias);
		return avariasGson;

	}

	@RequestMapping(value = "/listarObjetosSink", method = RequestMethod.GET)
	public String listarObjetosSink() {
		List<Loja> lojas = lojaDAO.listarTodos();
		List<Produto> produtos = produtoDAO.listarTodos();
		List<EntradaProduto> entradaProdutos = entradaProdutoDAO.listarTodos();
		System.out.println("Tamanho da entrada de produto lista" + entradaProdutos.size());
		List<MovimentacaoProduto> movimentacaoProdutos = movimentacaoProdutoDAO.listarTodos();
		List<Avaria> avarias = avariaDAO.listarTodos();
		List<Venda> vendas = vendaDAO.listarTodos();
		List<Usuario> usuarios = usuatioDAO.listarTodos();
		List<Furo> furos = furoDAO.listarTodos();

		ObjetosSink objetosSink = new ObjetosSink();
		objetosSink.setAvarias(avarias);
		objetosSink.setEntradaProdutos(entradaProdutos);
		objetosSink.setLojas(lojas);
		objetosSink.setMovimentacaoProdutos(movimentacaoProdutos);
		objetosSink.setVendas(vendas);
		objetosSink.setProdutos(produtos);
		objetosSink.setUsuarios(usuarios);
		objetosSink.setFuros(furos);
		if (momentoDaUltimaAtualizacaoDAO.getMomentoDaUltimaAtualizacao() != null) {
			objetosSink.setMomentoDaUltimaAtualizacao(
					momentoDaUltimaAtualizacaoDAO.getMomentoDaUltimaAtualizacao().getMomentoDaUltimaAtualizacao());
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation()
				.create();
		String objetosSinkGson = gson.toJson(objetosSink);
		return objetosSinkGson;

	}

	// ver o que tem de diferente no servidor em relação ao celular e servidor
	// envia a diferenca para o celular
	@RequestMapping(value = "/diffObjetosSink", method = RequestMethod.GET)
	public String diffObjetosSink(@RequestHeader("datahora") String datahora) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d = formatter.parse(datahora);
		String teste = formatter.format(d);
		System.out.println("TESTE" + teste);
		List<Loja> lojas = lojaDAO.novosRegistro(d);
		List<Produto> produtos = produtoDAO.novosRegistro(d);
		List<EntradaProduto> entradaProdutos = entradaProdutoDAO.novosRegistro(d);
		System.out.println("Tamanho da entrada de produto diff" + entradaProdutos.size());
		List<MovimentacaoProduto> movimentacaoProdutos = movimentacaoProdutoDAO.novosRegistro(d);
		List<Avaria> avarias = avariaDAO.novosRegistro(d);
		List<Venda> vendas = vendaDAO.novosRegistro(d);
		List<Furo> furos = furoDAO.novosRegistro(d);

		List<Usuario> usuarios = usuatioDAO.novosRegistro(d);

		ObjetosSink objetosSink = new ObjetosSink();
		objetosSink.setAvarias(avarias);
		objetosSink.setEntradaProdutos(entradaProdutos);
		objetosSink.setLojas(lojas);
		objetosSink.setMovimentacaoProdutos(movimentacaoProdutos);
		objetosSink.setVendas(vendas);
		objetosSink.setProdutos(produtos);
		objetosSink.setUsuarios(usuarios);
		objetosSink.setFuros(furos);
		objetosSink.setMomentoDaUltimaAtualizacao(
				momentoDaUltimaAtualizacaoDAO.getMomentoDaUltimaAtualizacao().getMomentoDaUltimaAtualizacao());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation()
				.create();
		String objetosSinkGson = gson.toJson(objetosSink);
		return objetosSinkGson;

	}

	// atraves da variavel sinc é visto quem não está sincronizado então o
	// celular envia para o servidor os que nao tem no servidor
	@RequestMapping(value = "/sincronizaObjetosSink", method = RequestMethod.PUT)
	public String sincronizaObjetosSink(@RequestBody ObjetosSink objetosSink) {
		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = new MomentoDaUltimaAtualizacao();
		// o momento da ultima atualização so deve ser salvo no banco quando o
		// celular enviar algum objeto

		/*
		 * if ((objetosSink.getLojas().size() != 0) ||
		 * (objetosSink.getProdutos().size() != 0) ||
		 * (objetosSink.getEntradaProdutos().size() != 0) ||
		 * (objetosSink.getMovimentacaoProdutos().size() != 0) ||
		 * (objetosSink.getAvarias().size() != 0) ||
		 * (objetosSink.getVendas().size() != 0) ||
		 * (objetosSink.getFuros().size() != 0) ||
		 * (objetosSink.getUsuarios().size() != 0)) {
		 */
		momentoDaUltimaAtualizacaoDAO.gravar(momentoDaUltimaAtualizacao);

		List<Loja> lojas = lojaDAO.merge(objetosSink.getLojas());
		List<Produto> produtos = produtoDAO.merge(objetosSink.getProdutos());
		List<EntradaProduto> entradaProdutos = entradaProdutoDAO.merge(objetosSink.getEntradaProdutos());
		System.out.println("Tamanho da entrada de produto sinc" + entradaProdutos.size());
		List<MovimentacaoProduto> movimentacaoProdutos = movimentacaoProdutoDAO
				.merge(objetosSink.getMovimentacaoProdutos());
		List<Avaria> avarias = avariaDAO.merge(objetosSink.getAvarias());
		List<Venda> vendas = vendaDAO.merge(objetosSink.getVendas());
		List<Furo> furos = furoDAO.merge(objetosSink.getFuros());
		List<Usuario> usuario = usuatioDAO.merge(objetosSink.getUsuarios());

		ObjetosSink objetosSink1 = new ObjetosSink();
		objetosSink1.setAvarias(avarias);
		objetosSink1.setEntradaProdutos(entradaProdutos);
		objetosSink1.setLojas(lojas);
		objetosSink1.setMovimentacaoProdutos(movimentacaoProdutos);
		objetosSink1.setVendas(vendas);
		objetosSink1.setProdutos(produtos);
		objetosSink1.setFuros(furos);
		objetosSink1.setUsuarios(usuario);
		objetosSink1.setMomentoDaUltimaAtualizacao(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation()
				.create();
		String objetosSinkGson = gson.toJson(objetosSink1);
		return objetosSinkGson;
		/*
		 * } else { ObjetosSink objetosSink1 = new ObjetosSink(); Gson gson =
		 * new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").
		 * excludeFieldsWithoutExposeAnnotation() .create(); String
		 * objetosSinkGson = gson.toJson(objetosSink1); return objetosSinkGson;
		 * }
		 */

	}

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
	public String cadastrarUsuario(@RequestBody Usuario usuario)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		usuario.setSenha(GenerateHashPasswordUtil.generateHash(usuario.getSenha()));
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation()
				.create();

		Usuario u = usuatioDAO.gravar(usuario);
		String usuarioGson = gson.toJson(usuario);
		return usuarioGson;
	}

	@RequestMapping(value = "/editarUsuario", method = RequestMethod.POST)
	public String editarUsuario(@RequestBody Usuario usuario)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		usuario.setSenha(GenerateHashPasswordUtil.generateHash(usuario.getSenha()));
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation()
				.create();
		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = new MomentoDaUltimaAtualizacao();
		momentoDaUltimaAtualizacaoDAO.gravar(momentoDaUltimaAtualizacao);
		List<Usuario> u = new ArrayList<>();
		u.add(usuario);
		u = usuatioDAO.merge(u);
		String usuarioGson = gson.toJson(u.get(0));
		return usuarioGson;

	}

	@RequestMapping(value = "/estoqueAtual", method = RequestMethod.POST)
	public void estoqueAtual(HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {

		//
		String lojaEscolhidaId = request.getParameter("loja");
		String nome = request.getServletContext().getRealPath("/WEB-INF/jasper/estoqueAtual.jasper");
		Map<String, Object> parametros = new HashMap<String, Object>();

		List<LojaDAO.EstoqueAtual> estoque = lojaDAO.estoqueAtual(lojaEscolhidaId);
		JRDataSource dataSource = new JRBeanCollectionDataSource(estoque, false);
		JasperPrint print = JasperFillManager.fillReport(nome, parametros, dataSource);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// new
		// FileOutputStream(request.getServletContext().getRealPath("/WEB-INF/jasper/vendas.pdf")
		exporter.exportReport();

	}

}
