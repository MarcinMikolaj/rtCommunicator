package project.rtc.communicator.invitations.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.rtc.communicator.invitations.entities.Invitation;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends MongoRepository<Invitation, String> {
	Optional<Invitation> findByInvitationId(String invitationId);
	Optional<List<Invitation>> findByInvited(String invited);
	int removeByInvitationId(String invitationId);
}
