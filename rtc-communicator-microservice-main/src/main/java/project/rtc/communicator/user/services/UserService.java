package project.rtc.communicator.user.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.rtc.communicator.invitations.entities.Invitation;
import project.rtc.communicator.user.entities.User;
import project.rtc.communicator.user.dto.UserResponseDto;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;
import project.rtc.registration.dto.ProfilePicture;

public interface UserService {

	// Returns the user with a registered and saved account, it also loads the photo to the returned user.
	UserResponseDto getUserAndLoadPicture(HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException;

	// The method allows you to delete a user account. Ensures that the user will be removed from any room they were previously assigned to.
	UserResponseDto deleteUser(String password , HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws UserNotFoundException, NoAuthorizationTokenException;
	UserResponseDto updateUserNick(String nick, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException;
	UserResponseDto updateUserPicture(ProfilePicture profilePicture, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException;
	UserResponseDto updateUserEmail(String email, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException;
	UserResponseDto updateUserPassword(String password, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException;

	// Returns the user with a registered and saved account.
	User getUser(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;
	User getUser(String userId) throws UserNotFoundException;

	// Allows you to load a photo into the user class instance if this instance has a path to the photo.
	// Returns the user with the photo loaded.
	User loadUserProfileImg(User user);

	// The method enables adding a new friend request to the list of invitations of a given user.
	// Returns the user whose invitation list has changed.
	User addInvitations(String nick, String invitationId) throws UserNotFoundException;
	
	// Lets you remove an invitation assigned to a given user. It accepts the invitation to be deleted as a parameter.
	User removeInvitation(Invitation invitation) throws UserNotFoundException;
}
