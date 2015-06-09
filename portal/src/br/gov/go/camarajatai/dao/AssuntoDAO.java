package br.gov.go.camarajatai.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.internal.jpa.querydef.OrderImpl;

import br.gov.go.camarajatai.dto.Assunto;

public class AssuntoDAO {
	
	private EntityManager em;
	
	private CriteriaBuilder cb;
	
	public AssuntoDAO() {
		cb = (em = Persistence.createEntityManagerFactory("portal").createEntityManager()).getCriteriaBuilder();
		
	}

	public void inserir(Assunto assunto) {
		
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		
		em.persist(assunto);
		
		et.commit();
	}

	public void atualizar(Assunto assunto) {
		
		EntityTransaction et = em.getTransaction();
		
		
		assunto.setDescricao(assunto.getDescricao()+"-"+assunto.getId());
		et.begin();
		
		em.merge(assunto);
		
		et.commit();
	}
	
	public List<Assunto> list() {
		
		CriteriaQuery<Assunto> cq = cb.createQuery(Assunto.class);
		
		List<Assunto> result = null;
		
        if (cq != null) {
            Root<Assunto> rAssunto = cq.from(Assunto.class);
                
            cq.orderBy(cb.asc(rAssunto.get("id")));
            cq.select(rAssunto);

            TypedQuery<Assunto> q = em.createQuery(cq);
            result = q.getResultList();
        }

		
		return result;
		
		
	}

}
