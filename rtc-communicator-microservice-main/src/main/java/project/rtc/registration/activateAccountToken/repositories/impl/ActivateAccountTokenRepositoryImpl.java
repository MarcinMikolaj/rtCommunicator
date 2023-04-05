package project.rtc.registration.activateAccountToken.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import project.rtc.registration.activateAccountToken.entities.ActivateAccountToken;
import project.rtc.registration.activateAccountToken.repositories.ActivateAccountTokenRepository;

@Repository
public class ActivateAccountTokenRepositoryImpl implements ActivateAccountTokenRepository {
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public ActivateAccountToken create(ActivateAccountToken activateAccountToken) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(activateAccountToken);
		entityTransaction.commit();
		entityManager.close();
		return activateAccountToken;
	}
	
	@Override
	public ActivateAccountToken findByToken(String token) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		TypedQuery<ActivateAccountToken> typedQuery = entityManager.createNamedQuery("ActivateAccountToken.findByToken", ActivateAccountToken.class);
		typedQuery.setParameter("token", token);
		entityTransaction.begin();
		ActivateAccountToken activateAccountToken = typedQuery.getSingleResult();
		entityTransaction.commit();
		entityManager.close();
		return activateAccountToken;
	}

	@Override
	public int deleteByEmail(String email) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
	    Query query = entityManager.createNamedQuery("ActivateAccountToken.delete");
	    query.setParameter("email", email);
		entityTransaction.begin();
		int result = query.executeUpdate();
		entityTransaction.commit();
		entityManager.close();
		return result;
	}

}
