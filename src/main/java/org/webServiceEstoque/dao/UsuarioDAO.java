package org.webServiceEstoque.dao;

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
import org.webServiceEstoque.models.Produto;
import org.webServiceEstoque.models.Usuario;
import org.webServiceEstoque.models.Venda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
@Transactional
public class UsuarioDAO {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	MomentoDaUltimaAtualizacaoDAO momentoDaUltimaAtualizacaoDAO;

	// public Usuario loadUserByUsername(String nome) {
	// List<Usuario> usuarios = manager.createQuery("select u from Usuario u
	// where u.nome = :nome", Usuario.class)
	// .setParameter("nome", nome).getResultList();
	// System.out.println(usuarios);
	// System.out.println(usuarios.get(0).getNome());
	// System.out.println(usuarios.get(0).getSenha());
	//
	// if (usuarios.isEmpty()) {
	// throw new UsernameNotFoundException("O usuário " + nome + " não foi
	// encontrado");
	// }
	//
	// return usuarios.get(0);
	// }

	public Usuario loadUserByUsernameAndPass(String nome, String pass) {
		try {
			Usuario usuarios = manager
					.createQuery("select u from Usuario u where u.nome = :nome and u.senha = :pass", Usuario.class)
					.setParameter("nome", nome).setParameter("pass", pass).getSingleResult();
			return usuarios;
		} catch (Exception e) {
			return null;
		}

	}

	public List<Usuario> merge(List<Usuario> u) {
		List<Usuario> usuarios = new ArrayList<>();

		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = momentoDaUltimaAtualizacaoDAO
				.getMomentoDaUltimaAtualizacao();
		for (Usuario usuario : u) {
			usuario.setSincronizado(1);
			usuario.setMomentoDaUltimaAtualizacao(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao());
			usuarios.add(manager.merge(usuario));
		}

		return usuarios;

	}

	public List<Usuario> novosRegistro(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(date);
		String Stringquery = " from Usuario where momentoDaUltimaAtualizacao > '" + s + "'"
				+ " order by momentoDaUltimaAtualizacao desc ";
		Query query = manager.createQuery(Stringquery);
		List<Usuario> listaDeUsuario = query.getResultList();

		return listaDeUsuario;

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarTodos() {
		Query query = manager.createQuery("from " + "Usuario where desativado=0");
		List<Usuario> listaDeUsuarios = query.getResultList();
		return listaDeUsuarios;
	}

	public Usuario gravar(Usuario u) {
		try {
			manager.persist(u);
		} catch (Exception e) {
			System.out.println("t");
		}
		MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = new MomentoDaUltimaAtualizacao();
		momentoDaUltimaAtualizacaoDAO.gravar(momentoDaUltimaAtualizacao);
		u.setMomentoDaUltimaAtualizacao(
				momentoDaUltimaAtualizacaoDAO.getMomentoDaUltimaAtualizacao().getMomentoDaUltimaAtualizacao());
		u.setSincronizado(1);
		manager.merge(u);
		return u;

	}

}
