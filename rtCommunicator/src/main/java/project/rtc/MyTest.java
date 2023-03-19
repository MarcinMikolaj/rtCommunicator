package project.rtc;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import project.rtc.authorization.oauth2.provider.AuthProvider;
import project.rtc.communicator.invitations.services.InvitationService;
import project.rtc.communicator.messager.dto.Message;
import project.rtc.communicator.messager.repositories.MessageRepository;
import project.rtc.communicator.messager.services.MessageService;
import project.rtc.communicator.room.repositories.RoomRepository;
import project.rtc.communicator.room.response_service.RoomService;
import project.rtc.communicator.user.dto.User;
import project.rtc.communicator.user.repositories.UserRepository;
import project.rtc.registration.dto.ProfilePicture;
import project.rtc.registration.dto.RegistrationRequest;
import project.rtc.registration.services.impl.RegistrationServiceImpl;
import project.rtc.utils.FileUtils;

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

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public void invokeTests() {	
		
		userRepository.deleteAll();
		roomRepository.deleteAll();
		messageRepository.deleteAll();
		
		createTestAccounts();
		
		// test room 01
		String roomId01 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "mateusz87@gmail.com", "ewelina@gmail.com"), "Przyjaciele");
		//Fri Oct 07 2022 05:12:30 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId01, "mateusz86", "Hey how's your day going today ?", "1633489950000");
		createTestMessage(roomId01, "anastazja2", "I'm fine, I had a fantastic day at school today!", "1665112350000");
		
		
		createTestMessage(roomId01, "ewelina32", "Nice to hear it", "1665112350000");
		createTestMessage(roomId01, "ewelina32", "I have something cool to tell about my adventures at school today", "1633489950000");
		
		//Sun Oct 09 2022 23:23:42 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId01, "anastazja2", "I'd love to hear your story", "1665350589580");	
		//Sun Oct 09 2022 23:28:47 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId01, "ewelina32", "A lot happened at school yesterday. This morning, on my way to class, I tripped over my shoelace. I should have taken that as a sign that today wasn't going to be the best day, but I didn't think so at the time. I went further. There was an unannounced quiz in the first lesson. I wasn't prepared at the time, so I didn't do very well.", "1665350927257");	
		
		
		//Sun Oct 09 2022 23:35:11 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId01, "mateusz86", "Sounds amazing !", "1665351311701");	
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId01, "anastazja2", "that's right, you can finish it for us", "1665351681759");
		createTestMessage(roomId01, "ewelina32", "already finishing it..", "1665351681759");
		createTestMessage(roomId01, "ewelina32", "Then there was a long break and we went out onto the field. There I met Monika, who just wasn't in a very good mood. We had an argument about which one of us should be on duty now. I like going for chalk and I'm happy to be on duty. She always wants to be too. I was sure it was my turn now, she claimed it was hers now.", "1665351681759");
				
		// test roo 02
		String roomId02 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "mateusz87@gmail.com"), "pwsz");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId02, "mateusz86", "When do we have to pass the project in programming?", "1665351681759");
		
		// test room 03
		//String roomId03 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "ewelina@gmail.com"), "ewelina32");
				
		// test room 04
		String roomId04 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "ewelina@gmail.com", "miłosz.mad@o2.pl"), "mionszu2");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId04, "mionszu2", "How are you ?", "1665351681759");
		
				
		// test room 05
		String roomId05 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "kacper78@gov.pl"), "kacper78");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId05, "kacper78", "I'm glad you were able to pass the exam", "1665351681759");
				
		// test room 06
		String roomId06 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "kasia900@gmail.com"), "kasia");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId06, "anastazja2", "Do you want to go training tomorrow?", "1665351681759");
				
		// test room 07
		String roomId07 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "magda8920@yahoo.pl"), "magdaPiwowar");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId07, "magdaPiwowar", "We're going out for pizza tomorrow, would you like to come with us? ", "1665351681759");
		
		// test room 08
		String roomId08 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "paulina@mail.com"), "paulinka");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId08, "paulinka", "Have you heard about the new movie at the cinema ?", "1665351681759");
				
				
		// test room 09
		String roomId09 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "julia@gmail.com"), "JulkaFromFrance");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId09, "anastazja2", "Ok", "1665351681759");
				
		// test room 10
		String roomId10 = createTestRooms(List.of("marcin.mikolajczyk22@gmail.com", "asia2@gmail.com"), "AsIa");
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		createTestMessage(roomId10, "anastazja2", "Hey !", "1665351681759");
		
		
		// test invitations
		createTestInvitations();
	}
	
	
	// This method create test user accounts
	private void createTestAccounts() {
		
		String basicPath = "C:\\Users\\Hawke\\Desktop\\Praca inżynierska\\Disk\\Test\\";
		
		createAccount("marcin.mikolajczyk22@gmail.com", "anastazja2", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "anastazja2\\picture.bin");
		createAccount("mateusz87@gmail.com", "mateusz86", "#d2G@123423", AuthProvider.local.toString(), true, basicPath + "mateusz86\\picture.bin");
		
		createAccount("ewelina@gmail.com", "ewelina32", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "ewelina32\\picture.bin");
		createAccount("miłosz.mad@o2.pl", "mionszu2", "d1d2A@d12234", AuthProvider.local.toString(), true, basicPath + "mionszu2\\picture.bin");
		createAccount("kacper78@gov.pl", "kacper78", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "kacper78\\picture.bin");
		createAccount("kasia900@gmail.com", "kasia", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "kasia\\picture.bin");
		createAccount("magda8920@yahoo.pl", "magdaPiwowar", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "magdaPiwowar\\picture.bin");
		createAccount("paulina@mail.com", "paulinka", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "paulinka\\picture.bin");
		createAccount("julia@gmail.com", "JulkaFromFrance", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "JulkaFromFrance\\picture.bin");
		createAccount("asia2@gmail.com", "AsIa", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "AsIa\\picture.bin");
		
	}
	
	private void createAccount(String email, String nick, String password, String authProvider, boolean statements, String pathToImg) {
			RegistrationRequest registrationRequest = new RegistrationRequest(email, nick, password, authProvider, statements, loadPicture(pathToImg));
			registrationServiceImpl.registerAccount(registrationRequest);
	}
	
	// Imitacja dołanczania zdjęcia podczas rejestracji
	public ProfilePicture loadPicture(String pathToImg) {
		String pictureInBase64 = FileUtils.deserializeObjectAndGetFromDirectory(pathToImg);
		ProfilePicture profilePicture = new ProfilePicture("profile.bin", "bin", 0, pictureInBase64);
		return profilePicture;
	}
	
	// Create test message
	private void createTestMessage(String roomId, String owner, String content, String dateMilisecondsUTC) {
		Message message = new Message(roomId, owner, content, dateMilisecondsUTC);
		messageService.save(message);
	}
	
	// Create test room, retur roomId
	private String createTestRooms(List<String> emails, String roomName) {
		
		List<User> users = new ArrayList<User>();
		
        for(String email: emails) {
        	users.add(userRepository.findByEmail(email).get());
        }
        
		return roomService.createRoom(roomName, users).getRoomId();
	}
	
	
	private void createTestInvitations() {
		
		invitationService.create("mionszu2", "anastazja2");
		invitationService.create("mateusz86", "anastazja2");
		invitationService.create("mateusz86", "anastazja2");
	}
	
}
