package project.rtc.communicator.user.services.impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.credentials.dto.Credentials;
import project.rtc.authorization.basic_login.credentials.repositories.CredentialsRepository;
import project.rtc.authorization.basic_login.credentials.services.CredentialsService;
import project.rtc.communicator.invitations.dto.Invitation;
import project.rtc.communicator.room.response_service.RoomService;
import project.rtc.communicator.room.dto.Statement;
import project.rtc.communicator.room.dto.StatementType;
import project.rtc.communicator.user.dto.User;
import project.rtc.communicator.user.dto.UserAction;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.communicator.user.dto.UserResponseBody;
import project.rtc.communicator.user.services.UserService;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;
import project.rtc.registration.dto.ProfilePicture;
import project.rtc.registration.dto.RegistrationRequest;
import project.rtc.utils.ConsoleColors;
import project.rtc.utils.FileUtils;
import project.rtc.utils.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
    private final CredentialsRepository credentialsRepository;
    private final UserRepository userRepository;
    
    private final RoomService roomService;
    private final CredentialsService credentialsService;
    private final JwtTokenProvider jwtTokenProvider;
    
    private final Validator validator;
    
    private final String pathToProfilePictures = "C:\\Users\\Hawke\\Desktop\\Praca inżynierska\\Disk\\ProfilePictures\\";
    
    

	// Allows you to create a new User object for a given user via ReqisterRequest
	public User createUserAndSaveInDataBase(RegistrationRequest registrationRequest) {
		
		ProfilePicture picture = registrationRequest.getPicture();
		User user = new User();
		User savedUser;
		
		user.setNick(registrationRequest.getNick());
		user.setEmail(registrationRequest.getEmail());
		
		// create path to profile picture
		String pathToUserDirectory = FileUtils.createSingleFolder(pathToProfilePictures + user.getNick());
		
		String path = pathToUserDirectory + "//" + "picture.bin";
		user.setPathToProfileImg(path);
		
		FileUtils.saveFileInDirectory
		(path, picture.getFileInBase64());
		
		try {
			savedUser = userRepository.save(user);
			return savedUser;
		} catch (Exception e) {
			System.out.print("UserService.createUserAndSaveInDataBase: " + e.getMessage());
		}
		
			
		return user;
	}
	
	
	
	// Returns the user with a registered and saved account, otherwise he throws UserNotFoundException
	@Override
    public User getUser(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
    	
	    String email = jwtTokenProvider.getTokenSubject(jwtTokenProvider.getJwtTokenFromCookie(httpServletRequest));
	    
		Optional<User> userOptional = Optional.of(userRepository
						.findByEmail(email)
						.orElseThrow(() -> new UserNotFoundException("UserService.getUser: User not found")));
		
		return userOptional.get();	
    }
	
    
	// Returns the user with a registered and saved account, otherwise he throws UserNotFoundException
	// It also loads the photo to the returned user.
	@Override
	public User getUserAndLoadPicture(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
	    	
		String email = jwtTokenProvider.getTokenSubject(jwtTokenProvider.getJwtTokenFromCookie(httpServletRequest));
		    
		Optional<User> userOptional = Optional.of(userRepository
							.findByEmail(email)
							.orElseThrow(() -> new UserNotFoundException("UserService.getUser: User not found")));
		
		User user = loadUserProfileImg(userOptional.get());
	    	
	    return user;		
	 }
    


	@Override
	public UserResponseBody deleteUser(String email, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		User user;
		UserResponseBody userResponseBody = new UserResponseBody(false, UserAction.DELETE_ACCOUNT);
			
		try {
			user = getUser(httpServletRequest);
		} catch (UserNotFoundException | NoAuthorizationTokenException e) {
			e.printStackTrace();
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.DELETE_ACCOUNT, "Account not found or you are not authorized.", StatementType.ERROR_STATEMENT));
			return userResponseBody;
		}
		
		// If emails not equals
		if(!user.getEmail().equals(email)) {
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.DELETE_ACCOUNT, "Login is incorrect.", StatementType.ERROR_STATEMENT));
			return userResponseBody;
		}
		
		
		try {		
			deleteUser(user);	
			userResponseBody.setSuccess(true);			
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.DELETE_ACCOUNT, "Account deleted.", StatementType.SUCCES_STATEMENT));
			return userResponseBody;
			
		} catch (Exception e) {
			e.printStackTrace();
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.DELETE_ACCOUNT, "Something went wrong.", StatementType.ERROR_STATEMENT));
			return userResponseBody;
		} 
		
	}

	// Allows you to completely remove a user from the site, including their account information, credentials, and references to them in other rooms.
	// Return deleted user.
	public User deleteUser(User u) {
		
		// remove credentials
		credentialsRepository.deleteByEmail(u.getEmail());
		
		// remove user references to other rooms.
		u.getRoomsId().stream().filter(id -> id != null).forEach(id -> roomService.deleteUserFromRoom(id, u.getNick()));
		
		// remove user account information
		userRepository.deleteById(u.getMongoId());
		
		return u;
	}
	
	
	@Override
	public UserResponseBody updateUserNick(String nick, HttpServletRequest httpServletRequest) {
		
		User user;
		UserResponseBody userResponseBody = new UserResponseBody();

		
		// try get user from db
		try {
			user = getUser(httpServletRequest);
			user.setNick(nick);
		} catch (UserNotFoundException | NoAuthorizationTokenException e) {
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_NICK, "Account not found or you are not authorized.", StatementType.ERROR_STATEMENT));
			e.printStackTrace();
			return userResponseBody;
		}
		

		Set<ConstraintViolation<User>> errors = validator.validateProperty(user, "nick");
		
		// validate 
		if(!errors.isEmpty()) {
			errors.forEach(error -> {
				userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_NICK, error.getMessage(), StatementType.ERROR_STATEMENT));
			});
			return userResponseBody;
		}
		
		
        // save updated entity
		userRepository.save(user);
		userResponseBody.setSuccess(true);
		
		User userForClient = loadUserProfileImg(userRepository.findById(user.getMongoId()).get());
		userResponseBody.setUser(userForClient);
		
		userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_NICK, "Updated !", StatementType.SUCCES_STATEMENT));
		
		return userResponseBody;
	}


	
	
	@Override
	public UserResponseBody updateUserEmail(String email, HttpServletRequest httpServletRequest) {
		
		User user;
		UserResponseBody userResponseBody = new UserResponseBody(); 
		
		try {
			user = getUser(httpServletRequest);
		} catch (UserNotFoundException | NoAuthorizationTokenException e) {
			e.printStackTrace();
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_EMAIL, "User not found", StatementType.ERROR_STATEMENT));
			return userResponseBody;
		}
		
		Credentials credentials = credentialsRepository.findByEmail(user.getEmail());
		
		

		user.setEmail(email);
        Set<ConstraintViolation<User>> errors = validator.validateProperty(user, "email");
		
		// validate 
		if(!errors.isEmpty()) {
			errors.forEach(error -> {
				userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_EMAIL, error.getMessage(), StatementType.ERROR_STATEMENT));
			});
			return userResponseBody;
		}
		
		userRepository.save(user);
		credentialsRepository.updateEmailById(email, credentials.getId());
		userResponseBody.setSuccess(true);
		
		
		User userForClient = loadUserProfileImg(userRepository.findById(user.getMongoId()).get());
		userResponseBody.setUser(userForClient);
		
		userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_EMAIL, "Updated !", StatementType.SUCCES_STATEMENT));
		
		return userResponseBody;
	}



	@Override
	public UserResponseBody updateUserPassword(String email, String password, HttpServletRequest httpServletRequest) {
		
		User user;
		UserResponseBody userResponseBody = new UserResponseBody();
		
		try {
			user = getUser(httpServletRequest);
		} catch (UserNotFoundException | NoAuthorizationTokenException e) {
			e.printStackTrace();
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_PASSWORD, "Account not found or you are not authorized.", StatementType.ERROR_STATEMENT));
			return userResponseBody;
		}
		
		// If login is incorrect
		if(!user.getEmail().equals(email)) {
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_PASSWORD, "You entered an incorrect login.", StatementType.ERROR_STATEMENT));
			return userResponseBody;
		}
		
		// If password is incorrect
		if(!validPassword(password)) {
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_PASSWORD, "Password must meet the rules.", StatementType.ERROR_STATEMENT));
			return userResponseBody;
		}
		
		
		credentialsService.updatePasswordByEmail(user.getEmail(), password);
		userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_PASSWORD, "Updated !", StatementType.SUCCES_STATEMENT));
		userResponseBody.setSuccess(true);
		
		User userForClient = loadUserProfileImg(userRepository.findById(user.getMongoId()).get());
		userResponseBody.setUser(userForClient);
		
		return userResponseBody;
	}
	
    private boolean validPassword(String password) {
		
		  if(password == null)
			  return false;
		
		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
		Matcher matcher = pattern.matcher(password);
		
		return matcher.matches();
	}



	@Override
	public UserResponseBody updateUserPicture(ProfilePicture profilePicture, HttpServletRequest httpServletRequest) {
		
		User user;
		UserResponseBody userResponseBody = new UserResponseBody();
		
		try {
			user = getUser(httpServletRequest);
		} catch (UserNotFoundException | NoAuthorizationTokenException e) {
			e.printStackTrace();
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_PICTURE, "Fail.", StatementType.ERROR_STATEMENT));
			return userResponseBody;
		}
		
		// create path to profile picture
		String pathToUserDirectory = FileUtils.createSingleFolder(pathToProfilePictures + user.getNick());
				
		String path = pathToUserDirectory + "//" + "picture.bin";
		user.setPathToProfileImg(path);
				
		FileUtils.saveFileInDirectory(path, profilePicture.getFileInBase64());
		
		
		userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_PICTURE, "Updated !", StatementType.SUCCES_STATEMENT));
		userResponseBody.setSuccess(true);
		
		User userForClient = loadUserProfileImg(userRepository.findById(user.getMongoId()).get());
		userResponseBody.setUser(userForClient);
		
		return userResponseBody;
	}
	
	
	// Allows you to load a photo into the user class instance if this instance has a path to the photo.
	// Returns the user with the photo loaded.
	public User loadUserProfileImg(User user) {
		
		String pictureInBase64;
		String pathToImg;
		ProfilePicture profilePicture;
		
		pathToImg = user.getPathToProfileImg();
		
		if(pathToImg == null || pathToImg.equals(""))
			throw new IllegalArgumentException("UserServiceImpl.loadUserProfileImg: the user does not have a profile picture path set");
		
		
		pictureInBase64 = FileUtils.deserializeObjectAndGetFromDirectory(pathToImg);
		profilePicture = new ProfilePicture("profile.jpg", "jpg", 0, pictureInBase64);

		user.setProfilePicture(profilePicture);

		return user;
	}
	

    // The method enables adding a new friend request to the list of invitations of a given user.
	// Returns the user whose invitation list has changed.
	@Override
	public User addInvitations(String nick, Invitation invitation) {
		
		User user;
		
		user = userRepository.findByNick(nick).orElseThrow(() 
				-> new NoSuchElementException(ConsoleColors.YELLOW + "UserServiceImpl.addInvitations: User not found" + ConsoleColors.RESET));
		
		user.getInvitations().add(invitation.getIdentificator());
		
		userRepository.save(user);
		
		return user;
	}


	// Lets you remove an invitation assigned to a given user.
	// It accepts the invitation to be deleted as a parameter.
	// Returns the user for whom the actions were performed.
	@Override
	public User removeInvitation(Invitation invitation) {
		
		User user;
		String nick;
		String identificator;
		
		
		nick = invitation.getInvited();
		identificator = invitation.getIdentificator();
		
		user = userRepository.findByNick(nick).orElseThrow(() -> new NoSuchElementException("UserServiceImpl.removeInvitation: User not found"));
		
		user.getInvitations().removeIf(i -> i.equals(identificator));
		
		userRepository.save(user);
		
		return user;
	}
	
}