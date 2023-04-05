package project.rtc.communicator.invitations.repositories.impl;

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
import project.rtc.communicator.invitations.entities.Invitation;
import project.rtc.communicator.invitations.repositories.InvitationRepository;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InvitationRepositoryImpl implements InvitationRepository {
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

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

	@Override
	public Invitation findByInvitationId(String invitationId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		TypedQuery<Invitation> typedQuery = entityManager.createNamedQuery("Invitation.findByInvitationId", Invitation.class);
		typedQuery.setParameter("invitationId", invitationId);
		entityTransaction.begin();
		Invitation invitation = typedQuery.getSingleResult();
		entityTransaction.commit();
		entityManager.close();
		return invitation;
	}

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

	@Override
	public int removeByInvitationId(String invitationId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Query query = entityManager.createNamedQuery("Invitation.removeByInvitationId");
		query.setParameter("invitationId", invitationId);
		entityTransaction.begin();
		int result = query.executeUpdate();
		entityTransaction.commit();
		entityManager.close();
		return result;
	}
	
}
