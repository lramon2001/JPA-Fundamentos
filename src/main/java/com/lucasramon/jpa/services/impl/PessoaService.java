package com.lucasramon.jpa.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.lucasramon.jpa.models.Pessoa;
import com.lucasramon.jpa.services.interfaces.PessoaBuscaPorNome;
import com.lucasramon.jpa.utils.JpaUtils;

public class PessoaService implements PessoaBuscaPorNome {

	@Override
	public List<Pessoa> all() {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			pessoas = em.createQuery("from Pessoa", Pessoa.class).getResultList();
			return pessoas;
		} finally {
			if (em != null) {
				em.close();
			}

		}
	}

	@Override
	public Pessoa byId(Integer id) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			return em.find(Pessoa.class, id);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public Pessoa insert(Pessoa entity) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return entity;
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public Pessoa update(Pessoa entity) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			em.getTransaction().begin();
			// em.unwrap(Session.class).update(entity);
			em.merge(entity);
			em.getTransaction().commit();
			return entity;
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public void delete(Pessoa entity) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
			Pessoa pessoaAserDeletada = em.find(Pessoa.class, id);
			if (pessoaAserDeletada != null) {
				em.getTransaction().begin();
				em.remove(pessoaAserDeletada);
				em.getTransaction().commit();
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public List<Pessoa> searchByName(String name) {
		EntityManager em = null;
		try {
			em = JpaUtils.getEntityManager();
//			List<Pessoa> pessoas = em
//					.createQuery("from Pessoa p where lower(p.nome) like lower(concat('%', :nome, '%'))", Pessoa.class)
//					.setParameter("nome", name).getResultList();
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Pessoa> buscaPorNomeCriteria = criteriaBuilder.createQuery(Pessoa.class);
			Root<Pessoa> raiz = buscaPorNomeCriteria.from(Pessoa.class);
			buscaPorNomeCriteria.where(
					criteriaBuilder.like(criteriaBuilder.lower(raiz.get("nome")), "%" + name.toLowerCase() + "%"));
			List<Pessoa> pessoas = em.createQuery(buscaPorNomeCriteria).getResultList();
			return pessoas;
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

}
