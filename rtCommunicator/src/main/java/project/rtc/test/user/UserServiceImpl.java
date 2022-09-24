package project.rtc.test.user;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import project.rtc.communicator.room.Statement;
import project.rtc.communicator.room.StatementType;
import project.rtc.exceptions.NoAuthorizationTokenException;
import project.rtc.exceptions.UserNotFoundException;
import project.rtc.registration.ProfilePicture;
import project.rtc.registration.RegistrationRequest;
import project.rtc.utils.FileUtils;
import project.rtc.utils.jwt.JwtTokenProvider;
import project.rtc.utils.jwt.JwtTokenProviderImpl;

@Service
public class UserServiceImpl implements UserService {
	
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    
    private String pathToProfilePictures = "C:\\Users\\Hawke\\Desktop\\Praca inżynierska\\Disk\\ProfilePictures\\";
    
    
    public UserServiceImpl(UserRepository userRepository, JwtTokenProviderImpl jwtTokenProviderImpl) {
    	this.userRepository = userRepository;
    	this.jwtTokenProvider = jwtTokenProviderImpl;
    }
	
	
	
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
		
			
		return null;
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
    
    
    


	@Override
	public UserResponseBody deleteUser(UserRequestBody userRequestBody, HttpServletRequest httpServletRequest) {
		
		User user;
		UserResponseBody userResponseBody = new UserResponseBody();
		userResponseBody.setAction(UserAction.UPDATE_USER_NICK);
		userResponseBody.setSuccess(false);
		
		
		try {
			user = getUser(httpServletRequest);
			userRepository.delete(user);
			userResponseBody.setSuccess(true);
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_NICK, "Account deleted", StatementType.SUCCES_STATEMENT));
			return userResponseBody;
		} catch (UserNotFoundException | NoAuthorizationTokenException | IllegalArgumentException e) {
			userResponseBody.getStatements().add(new Statement<UserAction>(UserAction.UPDATE_USER_NICK, "The account could not be deleted", StatementType.ERROR_STATEMENT));
			return userResponseBody;
		}
		
	}



	@Override
	public UserResponseBody updateUserNick(UserRequestBody userRequestBody, HttpServletRequest httpServletRequest) {
		
		User user;
		UserResponseBody userResponseBody = new UserResponseBody();
		userResponseBody.setSuccess(false);
		
		try {
			user = getUser(httpServletRequest);
			userResponseBody.setSuccess(true);
		} catch (UserNotFoundException | NoAuthorizationTokenException e) {

			e.printStackTrace();
		}
		
		
		return userResponseBody;
	}


	@Override
	public UserResponseBody updateUserPicture(UserRequestBody userRequestBody, HttpServletRequest httpServletRequest) {
		return null;
	}
	
}
