package project.rtc.communicator.invitations.repositories;

import project.rtc.communicator.invitations.entities.Invitation;

import java.util.List;

public interface InvitationRepository {
	
	// The method enables entering the invitation in the database
	// Returns the invitation to be saved in the database
	Invitation save(Invitation invitation);
	
	// The method allows you to download all invitations assigned to a given user.
	// The invited parameter specifies the nickname of the user for whom invitations will be retrieved.
	// Returns a list of all invitations assigned to the user.
	List<Invitation> findByInvited(String invited);
	
	// Makes it possible to find an invitation in the database by the unique identifier.
	// Returns the found invitation.
	Invitation findByInvitationId(String invitationId);
	
	// Removes an invitation found with an invitationId.
	// Returns the number of deleted invitations.
	int removeByInvitationId(String invitationId);

}
