package project.rtc.communicator.user.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.rtc.communicator.invitations.dto.Invitation;
import project.rtc.communicator.user.dto.User;
import project.rtc.communicator.user.dto.UserResponseBody;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;
import project.rtc.registration.dto.ProfilePicture;

public interface UserService {
	
	// Returns the user with a registered and saved account, otherwise he throws UserNotFoundException
	public User getUser(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;
	
	// Returns the user with a registered and saved account, it also loads the photo to the returned user.
	// Throw UserNotFoundException if user not found.
	public User getUserAndLoadPicture(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException;
	
	// The method allows you to delete a user account. Ensures that the user will be removed from any room they were previously assigned to.
	public UserResponseBody deleteUser(String password , HttpServletRequest httpServletRequest, HttpServletResponse response);
	
	public UserResponseBody updateUserNick(String nick, HttpServletRequest httpServletRequest);
	public UserResponseBody updateUserPicture(ProfilePicture profilePicture, HttpServletRequest httpServletRequest);
	public UserResponseBody updateUserEmail(String email, HttpServletRequest httpServletRequest);
	public UserResponseBody updateUserPassword(String email, String password, HttpServletRequest httpServletRequest);
	

	// Allows you to load a photo into the user class instance if this instance has a path to the photo.
	// Returns the user with the photo loaded.
	public User loadUserProfileImg(User user);
	
	// The method enables adding a new friend request to the list of invitations of a given user.
	// Returns the user whose invitation list has changed.
	public User addInvitations(String nick, Invitation invitation);
	
	// Lets you remove an invitation assigned to a given user.
	// It accepts the invitation to be deleted as a parameter.
	// Returns the user for whom the actions were performed.
	public User removeInvitation(Invitation invitation);
	

}
