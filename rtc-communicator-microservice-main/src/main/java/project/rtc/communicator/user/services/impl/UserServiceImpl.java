package project.rtc.communicator.user.services.impl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.credentials.entities.Credentials;
import project.rtc.authorization.basic_login.credentials.repositories.CredentialsRepository;
import project.rtc.authorization.basic_login.credentials.services.CredentialsService;
import project.rtc.communicator.invitations.entities.Invitation;
import project.rtc.communicator.room.service.RoomService;
import project.rtc.communicator.user.entities.User;
import project.rtc.communicator.user.entities.UserOperation;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.communicator.user.dto.UserResponseDto;
import project.rtc.communicator.user.services.UserService;
import project.rtc.infrastructure.exception.exceptions.NoAuthorizationTokenException;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;
import project.rtc.infrastructure.utils.token.JwtTokenProvider;
import project.rtc.registration.dto.ProfilePicture;
import project.rtc.infrastructure.utils.FileUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CredentialsRepository credentialsRepository;
    private final UserRepository userRepository;
    private final RoomService roomService;
    private final CredentialsService credentialsService;
	@Value("${app.security.jwt.secret_key}")
	private String jwtSecretKey;
	@Value("${app.file.user.pictures.path}")
    private String pathToProfilePictures;

	public User create(String nick, String email, ProfilePicture profilePicture) {
		String path = createPath(nick);
		savePicture(path, profilePicture);
		User user = new User(generateUniqueId(), nick, email, path);
		return userRepository.save(user);
	}

	@Override
    public User getUser(HttpServletRequest httpServletRequest) throws UserNotFoundException, NoAuthorizationTokenException {
	    String email = JwtTokenProvider.getTokenSubject(jwtSecretKey, JwtTokenProvider.getJwtTokenFromCookie(httpServletRequest));
		Optional<User> userOptional = Optional.of(userRepository
						.findByEmail(email)
						.orElseThrow(() -> new UserNotFoundException("UserService.getUser: User not found")));
		return userOptional.get();	
    }

	@Override
	public UserResponseDto getUserAndLoadPicture(HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		String email = JwtTokenProvider.getTokenSubject(jwtSecretKey, JwtTokenProvider.getJwtTokenFromCookie(httpServletRequest));
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		user = loadUserProfileImg(user);
		return prepareDto(UserOperation.GET_USER, user);
	}

	@Override
	public UserResponseDto deleteUser(String email, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws UserNotFoundException, NoAuthorizationTokenException {
		User user = getUser(httpServletRequest);
		deleteUser(user);
		return prepareDto(UserOperation.DELETE_ACCOUNT, loadUserProfileImg(userRepository.findById(user.getMongoId()).get()));
	}

	@Override
	public UserResponseDto updateUserNick(String nick, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		User user = getUser(httpServletRequest);
		user.setNick(nick);
		userRepository.save(user);
		return prepareDto(UserOperation.UPDATE_USER_NICK, loadUserProfileImg(userRepository.findById(user.getMongoId()).get()));
	}

	@Override
	public UserResponseDto updateUserEmail(String email, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		User user = getUser(httpServletRequest);
		Credentials credentials = credentialsRepository.findByEmail(user.getEmail());
		user.setEmail(email);
		userRepository.save(user);
		credentialsRepository.updateEmailById(email, credentials.getId());
		return prepareDto(UserOperation.UPDATE_USER_EMAIL, loadUserProfileImg(userRepository.findById(user.getMongoId()).get()));
	}

	@Override
	public UserResponseDto updateUserPassword(String email, String password, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		User user = getUser(httpServletRequest);
		credentialsService.updatePasswordByEmail(user.getEmail(), password);
		return prepareDto(UserOperation.UPDATE_USER_PASSWORD, loadUserProfileImg(userRepository.findById(user.getMongoId()).get()));
	}

	@Override
	public UserResponseDto updateUserPicture(ProfilePicture profilePicture, HttpServletRequest httpServletRequest)
			throws UserNotFoundException, NoAuthorizationTokenException {
		User user = getUser(httpServletRequest);
		// create path to profile picture
		String pathToUserDirectory = FileUtils.createSingleFolder(pathToProfilePictures + user.getNick());
		String path = pathToUserDirectory + "//" + "picture.bin";
		user.setPathToProfileImg(path);
		FileUtils.saveFileInDirectory(path, profilePicture.getFileInBase64());
		return prepareDto(UserOperation.UPDATE_USER_PICTURE, loadUserProfileImg(userRepository.findById(user.getMongoId()).get()));
	}

	public User deleteUser(User u) {

		// remove credentials
		credentialsRepository.deleteByEmail(u.getEmail());

		// remove user references to other rooms.
		u.getRoomsId().stream().filter(id -> id != null)
				.forEach(roomId -> {
					try {
						roomService.removeUserFromRoom(roomId, u.getUserId());
					} catch (RoomNotFoundException e) {
						throw new RuntimeException(e);
					} catch (UserNotFoundException e) {
						throw new RuntimeException(e);
					}
				});

		// remove user account information
		userRepository.deleteById(u.getMongoId());
		return u;
	}

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

	@Override
	public User addInvitations(String nick, String invitationId) throws UserNotFoundException {
		User user = userRepository.findByNick(nick).orElseThrow(UserNotFoundException::new);
		user.getInvitations().add(invitationId);
		userRepository.save(user);
		return user;
	}

	@Override
	public User removeInvitation(Invitation invitation) throws UserNotFoundException {
		User user = userRepository.findByNick(invitation.getInvited()).orElseThrow(UserNotFoundException::new);
		user.getInvitations().removeIf(i -> i.equals(invitation.getInvitationId()));
		userRepository.save(user);
		return user;
	}

	public User getUser(String userId) throws UserNotFoundException {
		return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
	}

	// Return path as string to user directory for pictures.
	private String createPath(String userNick){
		return FileUtils.createSingleFolder(pathToProfilePictures + userNick) + "//" + "picture.bin";
	}

	private void savePicture(String path, ProfilePicture profilePicture){
		FileUtils.saveFileInDirectory(path, profilePicture.getFileInBase64());
	}

	private String generateUniqueId(){return UUID.randomUUID().toString();}

	private UserResponseDto prepareDto(UserOperation operation, User user){
		return UserResponseDto.builder()
				.timestamp(new Date())
				.operation(operation)
				.user(user)
				.build();
	}
}
