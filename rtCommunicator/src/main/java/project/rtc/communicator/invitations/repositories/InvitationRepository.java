package project.rtc.communicator.invitations.repositories;

import project.rtc.communicator.invitations.dto.Invitation;

import java.util.List;

public interface InvitationRepository {
	
	// The method enables entering the invitation in the database
	// Returns the invitation to be saved in the database
	public Invitation save(Invitation invitation);
	
	// The method allows you to download all invitations assigned to a given user.
	// The invited parameter specifies the nickname of the user for whom invitations will be retrieved.
	// Returns a list of all invitations assigned to the user.
	public List<Invitation> findByInvited(String invited);
	
	// Makes it possible to find an invitation in the database by the unique identifier.
	// Returns the found invitation.
	public Invitation findByIdentificator(String identificator);
	
	// Removes an invitation found with an indentyficator.
	// Returns the number of deleted invitations.
	public int removeByIdentificator(String identyficator);

}
