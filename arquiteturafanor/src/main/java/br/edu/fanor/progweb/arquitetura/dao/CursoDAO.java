package br.edu.fanor.progweb.arquitetura.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.edu.fanor.progweb.arquitetura.entity.Curso;
import br.edu.fanor.progweb.arquitetura.entity.Usuario;
import br.edu.fanor.progweb.arquitetura.exceptions.DAOException;

/**
 * @author Samantha Silva
 **/

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class CursoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void salvar(Curso curso) {
		entityManager.persist(curso);
	}

	public void atualizar(Curso curso) {
		entityManager.merge(curso);
	}

	@SuppressWarnings("unchecked")
	public List<Curso> listarPorNome(String nome) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Curso> criteriaQuery = criteriaBuilder.createQuery(Curso.class);
		Root<Curso> curso = criteriaQuery.from(Curso.class);
		criteriaQuery.where(criteriaBuilder.equal(curso.<String> get("nome"), nome));

		Query query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public void excluir(Curso nome){
		
		entityManager.remove(nome);
			
		
	
	}
}
