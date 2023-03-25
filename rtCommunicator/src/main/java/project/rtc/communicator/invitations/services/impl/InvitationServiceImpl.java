package project.rtc.communicator.invitations.services.impl;

import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import project.rtc.communicator.invitations.entities.Invitation;
import project.rtc.communicator.invitations.dto.InvitationOperation;
import project.rtc.communicator.invitations.dto.InvitationResponseDto;
import project.rtc.communicator.invitations.repositories.InvitationRepository;
import project.rtc.communicator.invitations.services.InvitationService;
import project.rtc.communicator.room.service.RoomService;
import project.rtc.communicator.user.entities.User;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.communicator.user.services.UserService;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

	private final InvitationRepository invitationRepository;
	private final UserService userService;
	private final UserRepository userRepository;
	private final RoomService roomService;

	@Override
	public Invitation create(String roomId, String invited, String inviting) throws UserNotFoundException {
		Invitation invitation = invitationRepository.save(new Invitation(generateId(), roomId, inviting, invited, new Date().toString()));
		// Assign invitation to invited user.
		userService.addInvitations(invitation.getInvited(), invitation.getInvitationId());
		return invitation;
	}

	@Override
	public List<InvitationResponseDto> getInvitations(HttpServletRequest httpServletRequest) throws UserNotFoundException
			, NoAuthorizationTokenException {
		List<InvitationResponseDto> invitationResponseDtoList = new ArrayList<>();
		List<Invitation> invitations =  getAllUserInvitations(httpServletRequest);
		
		invitations.stream()
				.filter(i -> i != null)
				.peek(i -> invitationResponseDtoList.add(prepareInvitationResponseDto(InvitationOperation.GET_ALL_INVITATIONS
								, i.getInvitationId(), i.getInviting(), i.getInvited(), i.getCreation_date()
						, userRepository.findByNick(i.getInviting()).get())))
				.collect(Collectors.toList())
				.size();

		invitationResponseDtoList.stream()
		   .filter(u -> u.getUser() != null)
		   .filter(u -> u.getUser().getPathToProfileImg() != null)
		   .peek(u -> userService.loadUserProfileImg(u.getUser()))
		   .collect(Collectors.toList())
		   .size();
		
		return invitationResponseDtoList;
	}

	private List<Invitation> getAllUserInvitations(HttpServletRequest httpServletRequest) throws UserNotFoundException
			, NoAuthorizationTokenException {
		return invitationRepository.findByInvited(userService.getUser(httpServletRequest).getNick());
	}

	public List<InvitationResponseDto> acceptInvitation(String invitationId, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, RoomNotFoundException, NoAuthorizationTokenException {
		Invitation invitation = invitationRepository.findByInvitationId(invitationId);
		roomService.addUserToRoom(invitation.getRoomId(), invitation.getInvited());
		invitationRepository.removeByInvitationId(invitationId);
		// Remove the invitation from the user's invitation list.
		userService.removeInvitation(invitation);
		return getInvitations(httpServletRequest);
	}

	public List<InvitationResponseDto> declineInvitation(String invitationId, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		Invitation invitation = invitationRepository.findByInvitationId(invitationId);
		invitationRepository.removeByInvitationId(invitationId);
		// Remove the invitation from the user's invitation list.
		userService.removeInvitation(invitation);
		return getInvitations(httpServletRequest);
	}

	public String generateId(){
		return UUID.randomUUID().toString();
	}

	private InvitationResponseDto prepareInvitationResponseDto(InvitationOperation operation, String invitationId
			, String inviting, String invited, String creation_date, User user){
		return InvitationResponseDto.builder()
				.timestamp(new Date())
				.operation(operation)
				.invitationId(invitationId)
				.inviting(inviting)
				.invited(invited)
				.creation_date(creation_date)
				.user(user)
				.build();
	}

}
