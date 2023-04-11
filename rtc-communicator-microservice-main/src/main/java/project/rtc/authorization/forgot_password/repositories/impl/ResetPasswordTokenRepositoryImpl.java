package project.rtc.authorization.forgot_password.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import project.rtc.authorization.forgot_password.repositories.ResetPasswordTokenRepository;
import project.rtc.authorization.forgot_password.entities.PasswordResetToken;

@Repository
public class ResetPasswordTokenRepositoryImpl implements ResetPasswordTokenRepository {
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public PasswordResetToken save(PasswordResetToken resetPasswordToken) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(resetPasswordToken);
		entityTransaction.commit();
		entityManager.close();
		return resetPasswordToken;
	}

	@Override
	public PasswordResetToken findByToken(String token) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		TypedQuery<PasswordResetToken> typedQuery = entityManager.createNamedQuery("PasswordResetToken.findByToken", PasswordResetToken.class);
		typedQuery.setParameter("token", token);
		entityTransaction.begin();
		PasswordResetToken passwordResetToken = typedQuery.getSingleResult();
		entityTransaction.commit();
		entityManager.close();
		return passwordResetToken;
	}

	@Override
	public int removeByToken(String token) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Query query = entityManager.createNamedQuery("PasswordResetToken.removeByToken");
		query.setParameter("token", token);
		entityTransaction.begin();
		int result = query.executeUpdate();
		entityTransaction.commit();
		entityManager.close();
		return result;
	}
}
