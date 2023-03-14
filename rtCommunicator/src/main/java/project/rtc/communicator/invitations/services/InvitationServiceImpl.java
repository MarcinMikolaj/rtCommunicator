package project.rtc.communicator.invitations.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import project.rtc.communicator.invitations.dto.Invitation;
import project.rtc.communicator.invitations.dto.InvitationResponsePayload;
import project.rtc.communicator.invitations.dto.ReplyToInvitationRequest;
import project.rtc.communicator.invitations.repositories.InvitationRepository;
import project.rtc.communicator.invitations.repositories.InvitationRepositoryImpl;
import project.rtc.communicator.room.response_service.RoomService;
import project.rtc.communicator.room.response_service.impl.RoomServiceImpl;
import project.rtc.communicator.user.dto.User;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.communicator.user.services.UserService;
import project.rtc.communicator.user.services.UserServiceImpl;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {


	private final InvitationRepository invitationRepository;
	private final UserService userService;
	private final UserRepository userRepository;
	private final RoomService roomService;
	

	// The method returns the created invitation.
	// Accepts the nicknames of the inviter and the invitee as arguments.
	@Override
	public Invitation create(String inviting, String invited) {
			
		Invitation invitation;
		String identificator;
		Date sqlDate;
			
		identificator = UUID.randomUUID().toString();
		sqlDate = new Date(System.currentTimeMillis());
			
		invitation = new Invitation(identificator, inviting, invited, sqlDate);
			
		invitationRepository.save(invitation);
		userService.addInvitations(invited, invitation);
			
		return invitation;
		}
	

	@Override
	public List<Invitation> getAll(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
		
		User user = userService.getUser(httpServletRequest);
		
		return invitationRepository.findByInvited(user.getNick());
	}

	
	@Override
	public List<InvitationResponsePayload> getInvitationPayload(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
		
		List<InvitationResponsePayload> invitationResponsePayloadList = new ArrayList<InvitationResponsePayload>();
		List<Invitation> invitations =  getAll(httpServletRequest);
		
		invitations.stream()
		  .filter(i -> i != null)
		  .peek(i -> invitationResponsePayloadList.add(new InvitationResponsePayload(i, userRepository.findByNick(i.getInviting()).get())))
		  .collect(Collectors.toList())
		  .size();
		
		invitationResponsePayloadList.stream()
		   .filter(u -> u.getUser() != null)
		   .filter(u -> u.getUser().getPathToProfileImg() != null)
		   .peek(u -> userService.loadUserProfileImg(u.getUser()))
		   .collect(Collectors.toList())
		   .size();
		
		return invitationResponsePayloadList;
	}
	
	// Enables the execution of method accept or decline depending on the decision made by the user on the invitation.
	// As an argument, it accepts payload (ReplyToInvitationRequest.class) received from the customer.
	@Override
	public Invitation handleInvitation(ReplyToInvitationRequest replyToInvitationRequest) {
		
		boolean accepted;
		String identificator;
		Invitation invitation;
		
		accepted = replyToInvitationRequest.isAccepted();
		identificator = replyToInvitationRequest.getIdentyficator();
		
		invitation = invitationRepository.findByIdentificator(identificator);
		
		if(accepted)
			return accept(invitation);
		else
			return decline(invitation);
		
	}
	
	
	// Is responsible for accepting the invitation of a given user, which means removing the invitation and creating a room for users.
	// He accepts the invitation as an argument.
	@Override
	public Invitation accept(Invitation invitation) {
		
		invitationRepository.removeByIdentificator(invitation.getIdentificator());
		userService.removeInvitation(invitation);
		
		List<User> users = new ArrayList<User>();
		
		users.add(userRepository.findByNick(invitation.getInvited()).orElseThrow(() -> new NoSuchElementException()));
		users.add(userRepository.findByNick(invitation.getInviting()).orElseThrow(() -> new NoSuchElementException()));
		
		roomService.createRoom(invitation.getInvited(), users);
		
		return invitation;
	}

	
	// Responsible for removing the invitation and indicating it from the invited user after rejecting the invitation.
	// He accepts the invitation as an argument.
	@Override
	public Invitation decline(Invitation invitation) {
		
		invitationRepository.removeByIdentificator(invitation.getIdentificator());
		userService.removeInvitation(invitation);
		
		return invitation;
	}

}
