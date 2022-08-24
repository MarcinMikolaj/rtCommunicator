package project.rtc.authorization.forgot_password.reset_password_token;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import project.rtc.utils.ConsoleColors;

@Repository
public class ResetPasswordTokenRepositoryImpl implements ResetPasswordTokenRepository{
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public PasswordResetToken save(PasswordResetToken resetPasswordToken) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		try {
			entityTransaction.begin();
			entityManager.persist(resetPasswordToken);
			entityTransaction.commit();
			entityManager.close();
			return resetPasswordToken;
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
	public PasswordResetToken findByToken(String token) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		TypedQuery<PasswordResetToken> typedQuery = entityManager.createNamedQuery("PasswordResetToken.findByToken", PasswordResetToken.class);
		typedQuery.setParameter("token", token);
		
		try {
			entityTransaction.begin();
			PasswordResetToken passwordResetToken = typedQuery.getSingleResult();
			entityTransaction.commit();
			entityManager.close();
			return passwordResetToken;
		} catch (NoResultException noResultException) {
			System.out.println(ConsoleColors.BLUE + "ResetPasswordTokenRepositoryImpl.findByToken: " +  noResultException.getMessage() + ConsoleColors.RESET);
			entityManager.close();
			return null;
		}
	}

	@Override
	public int removeByToken(String token) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Query query = entityManager.createNamedQuery("PasswordResetToken.removeByToken");
		query.setParameter("token", token);
		
		try {
			entityTransaction.begin();
			int result = query.executeUpdate();
			entityTransaction.commit();
			entityManager.close();
			return result;
		} catch (NoResultException noResultException) {
			System.out.println(ConsoleColors.BLUE + "ResetPasswordTokenRepositoryImpl.findByToken: " +  noResultException.getMessage() + ConsoleColors.RESET);
			entityManager.close();
			return 0;
		}
	}
	
	
}
