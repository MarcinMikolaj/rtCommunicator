package project.rtc.communicator.invitations.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import project.rtc.communicator.invitations.dto.Invitation;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InvitationRepositoryImpl implements InvitationRepository {
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	// The method enables entering the invitation in the database
	// Returns the invitation to be saved in the database
	@Override
	public Invitation save(Invitation invitation) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		entityManager.persist(invitation);
		entityTransaction.commit();
		entityManager.close();
		
		return invitation;
	}

	
	// Makes it possible to find an invitation in the database by the identifier.
	// Returns the found invitation.
	@Override
	public Invitation findByIdentificator(String identificator) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		TypedQuery<Invitation> typedQuery = entityManager.createNamedQuery("Invitation.findByIdentificator", Invitation.class);
		typedQuery.setParameter("identificator", identificator);
		
		entityTransaction.begin();
		
		Invitation invitation = typedQuery.getSingleResult();
		
		entityTransaction.commit();
		entityManager.close();
		
		return invitation;
	}
	

	// The method allows you to download all invitations assigned to a given user.
	// The invited parameter specifies the nickname of the user for whom invitations will be retrieved.
	// Returns a list of all invitations assigned to the user.
	@Override
	public List<Invitation> findByInvited(String invited) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		TypedQuery<Invitation> typedQuery = entityManager.createNamedQuery("Invitation.findByInvited", Invitation.class);
		typedQuery.setParameter("invited", invited);
		
		entityTransaction.begin();
		
		List<Invitation> invitations = typedQuery.getResultList();
		
		entityTransaction.commit();
		entityManager.close();
		
		return invitations;
	}


	// Removes an invitation found with an indentyficator.
	// Returns the number of deleted invitations.
	@Override
	public int removeByIdentificator(String identyficator) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		Query query = entityManager.createNamedQuery("Invitation.removeByIdentificator");
		query.setParameter("identyficator", identyficator);
		
		entityTransaction.begin();
		int result = query.executeUpdate();
		entityTransaction.commit();
		entityManager.close();
		
		return result;
	}
	
}
