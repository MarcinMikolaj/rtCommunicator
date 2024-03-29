package project.rtc;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.rtc.authorization.oauth2.provider.AuthProvider;
import project.rtc.communicator.invitations.repositories.InvitationRepository;
import project.rtc.communicator.invitations.services.InvitationService;
import project.rtc.communicator.messager.entities.Message;
import project.rtc.communicator.messager.repositories.MessageRepository;
import project.rtc.communicator.messager.services.MessageService;
import project.rtc.communicator.room.repositories.RoomRepository;
import project.rtc.communicator.room.service.RoomService;
import project.rtc.communicator.user.entities.User;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.infrastructure.exception.exceptions.RoomNotFoundException;
import project.rtc.infrastructure.exception.exceptions.UserNotFoundException;
import project.rtc.registration.dto.ProfilePicture;
import project.rtc.registration.dto.RegistrationRequestDto;
import project.rtc.registration.services.impl.RegistrationServiceImpl;
import project.rtc.infrastructure.utils.FileUtils;

import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class MyTest {
	private final RegistrationServiceImpl registrationServiceImpl;
	private final MessageRepository messageRepository;
	private final MessageService messageService;
	private final UserRepository userRepository;
	private final RoomRepository roomRepository;
	private final RoomService roomService;
	private final InvitationService invitationService;
	private final InvitationRepository invitationRepository;
	private final Environment environment;
	@Value("${app.file.user.pictures.test.path}")
	private String pathTestPictures;
	private static Map<String, User> testUsers = new HashMap<>();

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public void invokeTests() throws UserNotFoundException, RoomNotFoundException, MessagingException {
		userRepository.deleteAll();
		roomRepository.deleteAll();
		messageRepository.deleteAll();
		invitationRepository.deleteAll();
		createTestAccounts();

		// test room 01
		String roomId01 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "mateusz87@gmail.com", "ewelina@gmail.com"), "Przyjaciele");
		//Fri Oct 07 2022 05:12:30 GMT+0200 (Central European Summer Time)

		createTestMessage(roomId01, testUsers.get("mateusz86").getUserId(), "mateusz86", "Lorem ipsum dolor sit amet.  ?", "1533489950001");
		createTestMessage(roomId01, testUsers.get("anastazja2").getUserId(), "anastazja2", "Contrary to popular belief", "1533489950002");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "In hac habitasse platea dictumst.", "1533489950003");
		createTestMessage(roomId01, testUsers.get("mateusz86").getUserId(), "mateusz86", "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. ", "1533489950004");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "Fusce porttitor egestas pharetra.", "1533489950005");
		createTestMessage(roomId01, testUsers.get("anastazja2").getUserId(), "anastazja2", "Proin fringilla nulla turpis, eget efficitur orci scelerisque eget. ", "1533489950006");
		createTestMessage(roomId01, testUsers.get("mateusz86").getUserId(), "mateusz86", "Curabitur congue velit ut metus fringilla, quis tristique est malesuada. Cras fermentum tortor at gravida luctus. Fusce elementum vulputate efficitur.", "1533489950007");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "Pellentesque dictum tempor justo et malesuada.", "1533489950008");
		createTestMessage(roomId01, testUsers.get("mateusz86").getUserId(), "mateusz86", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "1533489950009");
		createTestMessage(roomId01, testUsers.get("anastazja2").getUserId(), "anastazja2", "Lorem ipsum dolor sit amet.  ?", "1533489950010");
		createTestMessage(roomId01, testUsers.get("mateusz86").getUserId(), "mateusz86", "Contrary to popular belief", "1533489950011");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "In hac habitasse platea dictumst.", "1533489950012");
		createTestMessage(roomId01, testUsers.get("mateusz86").getUserId(), "mateusz86", "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. ", "1533489950013");
		createTestMessage(roomId01, testUsers.get("anastazja2").getUserId(), "anastazja2", "Fusce porttitor egestas pharetra.", "1533489950014");
		createTestMessage(roomId01, testUsers.get("anastazja2").getUserId(), "anastazja2", "Proin fringilla nulla turpis, eget efficitur orci scelerisque eget. ", "1533489950015");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "Curabitur congue velit ut metus fringilla, quis tristique est malesuada. Cras fermentum tortor at gravida luctus. Fusce elementum vulputate efficitur.", "1533489950016");
		createTestMessage(roomId01, testUsers.get("mateusz86").getUserId(), "mateusz86", "Pellentesque dictum tempor justo et malesuada.", "1533489950017");
		createTestMessage(roomId01, testUsers.get("anastazja2").getUserId(), "anastazja2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "1533489950018");
		createTestMessage(roomId01, testUsers.get("mateusz86").getUserId(), "mateusz86", "Hey how's your day going today ?", "1533489950019");
		createTestMessage(roomId01, testUsers.get("anastazja2").getUserId(), "anastazja2", "I'm fine, I had a fantastic day at school today!", "1533489950020");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "Nice to hear it", "1533489950021");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "I have something cool to tell about my adventures at school today", "1533489950022");
		createTestMessage(roomId01, testUsers.get("anastazja2").getUserId(), "anastazja2", "I'd love to hear your story", "1533489950023");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "A lot happened at school yesterday. This morning, on my way to class, I tripped over my shoelace. I should have taken that as a sign that today wasn't going to be the best day, but I didn't think so at the time. I went further. There was an unannounced quiz in the first lesson. I wasn't prepared at the time, so I didn't do very well.", "1533489950024");
		createTestMessage(roomId01, testUsers.get("mateusz86").getUserId(), "mateusz86", "Sounds amazing !", "1533489950025");
		createTestMessage(roomId01, testUsers.get("anastazja2").getUserId(), "anastazja2", "that's right, you can finish it for us", "1533489950026");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "already finishing it..", "1533489950027");
		createTestMessage(roomId01, testUsers.get("ewelina32").getUserId(), "ewelina32", "Then there was a long break and we went out onto the field. There I met Monika, who just wasn't in a very good mood. We had an argument about which one of us should be on duty now. I like going for chalk and I'm happy to be on duty. She always wants to be too. I was sure it was my turn now, she claimed it was hers now.", "1533489950028");
				
		// test roo 02
		String roomId02 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "mateusz87@gmail.com"), "pwsz");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId02, testUsers.get("mateusz86").getUserId(), "mateusz86", "When do we have to pass the project in programming?", "1665351681759");
		
		// test room 03
		//String roomId03 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "ewelina@gmail.com"), "ewelina32");
				
		// test room 04
		String roomId04 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "ewelina@gmail.com", "miłosz.mad@o2.pl"), "mionszu2");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId04, testUsers.get("mionszu2").getUserId(), "mionszu2", "How are you ?", "1665351681759");

		// test room 05
		String roomId05 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "kacper78@gov.pl"), "kacper78");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId05, testUsers.get("kacper78").getUserId(), "kacper78", "I'm glad you were able to pass the exam", "1665351681759");
				
		// test room 06
		String roomId06 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "kasia900@gmail.com"), "kasia");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId06, testUsers.get("anastazja2").getUserId(), "anastazja2", "Do you want to go training tomorrow?", "1665351681759");
				
		// test room 07
		String roomId07 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "magda8920@yahoo.pl"), "magdaPiwowar");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId07, testUsers.get("magdaPiwowar").getUserId(), "magdaPiwowar", "We're going out for pizza tomorrow, would you like to come with us? ", "1665351681759");
		
		// test room 08
		String roomId08 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "paulina@mail.com"), "paulinka");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId08, testUsers.get("paulinka").getUserId(), "paulinka", "Have you heard about the new movie at the cinema ?", "1665351681759");

		// test room 09
		String roomId09 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "julia@gmail.com"), "JulkaFromFrance");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId09, testUsers.get("anastazja2").getUserId(), "anastazja2", "Ok", "1665351681759");
				
		// test room 10
		String roomId10 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "asia2@gmail.com"), "AsIa");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId10, testUsers.get("anastazja2").getUserId(), "anastazja2", "Hey !", "1665351681759");

		// test invitations
		createTestInvitations(roomId06, "mateusz86", "anastazja2");
		createTestInvitations(roomId07, "mateusz86", "magdaPiwowar");
		createTestInvitations(roomId08, "mateusz86", "anastazja2");
	}

	// This method create test user accounts.
	private void createTestAccounts() throws MessagingException {

		User anastazja2 = null;
		User mateusz86 = null;
		User ewelina32 = null;
		User mionszu2 = null;
		User kacper78 = null;
		User kasia = null;
		User magdaPiwowar = null;
		User paulinka = null;
		User JulkaFromFrance = null;
		User AsIa = null;

		if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
			anastazja2 = createAccount("marcin.mikolajczyk22@gmail.com", "anastazja2", "d2A@1234"
				, AuthProvider.local.toString(), true, pathTestPictures + "anastazja2\\picture.bin");
			mateusz86 = createAccount("mateusz87@gmail.com", "mateusz86", "#d2G@123423"
				, AuthProvider.local.toString(), true, pathTestPictures + "mateusz86\\picture.bin");
			ewelina32 = createAccount("ewelina@gmail.com", "ewelina32", "d2A@1234"
				, AuthProvider.local.toString(), true, pathTestPictures + "ewelina32\\picture.bin");
			mionszu2 = createAccount("miłosz.mad@o2.pl", "mionszu2", "d1d2A@d12234"
				, AuthProvider.local.toString(), true, pathTestPictures + "mionszu2\\picture.bin");
			kacper78 = createAccount("kacper78@gov.pl", "kacper78", "d2A@1234"
				, AuthProvider.local.toString(), true, pathTestPictures + "kacper78\\picture.bin");
			kasia = createAccount("kasia900@gmail.com", "kasia", "d2A@1234"
				, AuthProvider.local.toString(), true, pathTestPictures + "kasia\\picture.bin");
			magdaPiwowar = createAccount("magda8920@yahoo.pl", "magdaPiwowar", "d2A@1234"
				, AuthProvider.local.toString(), true, pathTestPictures + "magdaPiwowar\\picture.bin");
			paulinka = createAccount("paulina@mail.com", "paulinka", "d2A@1234"
				, AuthProvider.local.toString(), true, pathTestPictures + "paulinka\\picture.bin");
			JulkaFromFrance = createAccount("julia@gmail.com", "JulkaFromFrance", "d2A@1234"
				, AuthProvider.local.toString(), true, pathTestPictures + "JulkaFromFrance\\picture.bin");
			AsIa = createAccount("asia2@gmail.com", "AsIa", "d2A@1234"
				, AuthProvider.local.toString(), true, pathTestPictures + "AsIa\\picture.bin");
		} else if (Arrays.asList(environment.getActiveProfiles()).contains("docker")) {
			anastazja2 = createAccount("marcin.mikolajczyk22@gmail.com", "anastazja2", "d2A@1234"
					, AuthProvider.local.toString(), true, pathTestPictures + "anastazja2/picture.bin");
			mateusz86 = createAccount("mateusz87@gmail.com", "mateusz86", "#d2G@123423"
					, AuthProvider.local.toString(), true, pathTestPictures + "mateusz86/picture.bin");
			ewelina32 = createAccount("ewelina@gmail.com", "ewelina32", "d2A@1234"
					, AuthProvider.local.toString(), true, pathTestPictures + "ewelina32/picture.bin");
			mionszu2 = createAccount("miłosz.mad@o2.pl", "mionszu2", "d1d2A@d12234"
					, AuthProvider.local.toString(), true, pathTestPictures + "mionszu2/picture.bin");
			kacper78 = createAccount("kacper78@gov.pl", "kacper78", "d2A@1234"
					, AuthProvider.local.toString(), true, pathTestPictures + "kacper78/picture.bin");
			kasia = createAccount("kasia900@gmail.com", "kasia", "d2A@1234"
					, AuthProvider.local.toString(), true, pathTestPictures + "kasia/picture.bin");
			magdaPiwowar = createAccount("magda8920@yahoo.pl", "magdaPiwowar", "d2A@1234"
					, AuthProvider.local.toString(), true, pathTestPictures + "magdaPiwowar/picture.bin");
			paulinka = createAccount("paulina@mail.com", "paulinka", "d2A@1234"
					, AuthProvider.local.toString(), true, pathTestPictures + "paulinka/picture.bin");
			JulkaFromFrance = createAccount("julia@gmail.com", "JulkaFromFrance", "d2A@1234"
					, AuthProvider.local.toString(), true, pathTestPictures + "JulkaFromFrance/picture.bin");
			AsIa = createAccount("asia2@gmail.com", "AsIa", "d2A@1234"
					, AuthProvider.local.toString(), true, pathTestPictures + "AsIa/picture.bin");
		} else{
			throw new IllegalWriteException("You can't create test accounts if no profile (dev,docker) was choose");
		}

		testUsers.put("anastazja2", anastazja2);
		testUsers.put("mateusz86",  mateusz86);
		testUsers.get("mateusz86").getUserId();
		testUsers.put("ewelina32", ewelina32);
		testUsers.put("mionszu2", mionszu2);
		testUsers.put("kacper78", kacper78);
		testUsers.put("kasia", kasia);
		testUsers.put("magdaPiwowar", magdaPiwowar);
		testUsers.put("paulinka", paulinka);
		testUsers.put("JulkaFromFrance", JulkaFromFrance);
		testUsers.put("AsIa", AsIa);
	}

	// Creat test account.
	private User createAccount(String email, String nick, String password, String authProvider, boolean statements, String pathToImg)
			throws MessagingException {
		return registrationServiceImpl.registerAccount(
				new RegistrationRequestDto(email, nick, password, authProvider, statements, loadPicture(pathToImg)));
	}
	
	// Imitation setting picture.
	public ProfilePicture loadPicture(String pathToImg) {
		String pictureInBase64 = FileUtils.deserializeObjectAndGetFromDirectory(pathToImg);
		ProfilePicture profilePicture = new ProfilePicture("profile.bin", "bin", 0, pictureInBase64);
		return profilePicture;
	}
	
	// Create test message.
	private Message createTestMessage(String roomId, String userId, String owner, String content, String dateMilisecondsUTC)
			throws RoomNotFoundException, UserNotFoundException {
		return messageService.create(new Message(roomId, userId, owner, content, dateMilisecondsUTC));
	}

	// Create test room, return roomId.
	private String createTestRooms(List<String> emails, String roomName) throws UserNotFoundException {
		List<String> usersId = new ArrayList<>();
        for(String email: emails)
			usersId.add(userRepository.findByEmail(email).get().getUserId());
		return roomService.create(roomName, usersId).getRoomId();
	}

	private void createTestInvitations(String roomId, String invited, String inviting) throws UserNotFoundException {
		invitationService.create(roomId, invited, inviting);
	}

}
