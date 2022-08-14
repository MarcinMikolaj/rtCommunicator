package project.rtc.authorization.credentials;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import project.rtc.ConsoleColors;

@Repository
public class CredentialsRepositoryImpl implements CredentialsRepository {
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Credentials save(Credentials credentials) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		try {
			entityTransaction.begin();
			entityManager.persist(credentials);
			entityTransaction.commit();
			entityManager.close();
			return credentials;
		} catch (EntityExistsException entityExistsException) {
			System.out.println(entityExistsException.getMessage().toString());
			entityManager.close();
			return null;
		} catch (IllegalArgumentException illegalArgumentException) {
			System.out.println(illegalArgumentException.getMessage().toString());
			entityManager.close();
			return null;
		}
		
	}

	@Override
	public boolean update(Credentials credentials) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		try {
			entityTransaction.begin();
			entityManager.merge(credentials);
			entityTransaction.commit();
			entityManager.close();
			return true;
		} catch (IllegalArgumentException illegalArgumentException) {
			System.out.println(illegalArgumentException.getMessage().toString());
			entityManager.close();
			return false;
		}
	}
	
	
	@Override
	public Credentials findByEmail(String email) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		TypedQuery<Credentials> typedQuery = entityManager.createNamedQuery("Credentials.findByEmail", Credentials.class);
		typedQuery.setParameter("email", email);
		
		try {
			entityTransaction.begin();
			Credentials credentials = typedQuery.getSingleResult();
			entityTransaction.commit();
			entityManager.close();
			return credentials;
		} catch (NoResultException noResultException) {
			System.out.println(ConsoleColors.BLUE + noResultException.getMessage() + ConsoleColors.RESET);
			entityManager.close();
			return null;
		}
	}

	@Override
	public void createQueryJPQL(String jpql) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Query query = entityManager.createQuery(jpql);
		entityTransaction.begin();
		query.executeUpdate();
		entityTransaction.commit();
		entityManager.close();		
	}

}
