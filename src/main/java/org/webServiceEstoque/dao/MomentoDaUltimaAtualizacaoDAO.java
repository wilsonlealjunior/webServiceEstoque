package org.webServiceEstoque.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.webServiceEstoque.models.Loja;
import org.webServiceEstoque.models.MomentoDaUltimaAtualizacao;

@Repository
@Transactional
public class MomentoDaUltimaAtualizacaoDAO {
	
	@PersistenceContext
    private EntityManager manager;

    public void gravar(MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao){
    	momentoDaUltimaAtualizacao.setMomentoDaUltimaAtualizacao(new Date());
    	 manager.persist(momentoDaUltimaAtualizacao);
    }
    
    
    public MomentoDaUltimaAtualizacao getMomentoDaUltimaAtualizacao() {
		String Stringquery = " from MomentoDaUltimaAtualizacao order by momentoDaUltimaAtualizacao desc";
		Query query = manager.createQuery(Stringquery).setMaxResults(1);
		try {
			MomentoDaUltimaAtualizacao momentoDaUltimaAtualizacao = (MomentoDaUltimaAtualizacao) query.getSingleResult();
			System.out.println(momentoDaUltimaAtualizacao.getMomentoDaUltimaAtualizacao().toString());
			return momentoDaUltimaAtualizacao;
		} catch (NoResultException e) {
			return null;
			// TODO: handle exception
		}
		
		
		
		
		
		
	}

}
