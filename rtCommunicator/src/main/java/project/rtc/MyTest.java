package project.rtc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.rtc.authorization.oauth2.provider.AuthProvider;
import project.rtc.communicator.invitations.InvitationRequestPayload;
import project.rtc.communicator.invitations.InvitationService;
import project.rtc.communicator.invitations.InvitationServiceImpl;
import project.rtc.communicator.messager.Message;
import project.rtc.communicator.messager.MessageRepository;
import project.rtc.communicator.messager.MessageService;
import project.rtc.communicator.messager.MessageServiceImpl;
import project.rtc.communicator.room.Room;
import project.rtc.communicator.room.RoomRepository;
import project.rtc.communicator.room.RoomService;
import project.rtc.communicator.user.User;
import project.rtc.communicator.user.UserRepository;
import project.rtc.communicator.user.UserService;
import project.rtc.communicator.user.UserServiceImpl;
import project.rtc.registration.ProfilePicture;
import project.rtc.registration.RegistrationRequest;
import project.rtc.registration.RegistrationService;
import project.rtc.utils.FileUtils;

@RestController
public class MyTest {
	
	private RegistrationService registrationService;
	private MessageRepository messageRepository;
	private MessageService messageService;
	private UserRepository userRepository;
	private RoomRepository roomRepository;
	private RoomService roomService;
	private InvitationService invitationService;
	
	
	public MyTest(UserRepository userRepository, RoomRepository roomRepository,RegistrationService registrationService,
			RoomService roomService, MessageRepository messageRepository, MessageServiceImpl messageServiceImpl, InvitationServiceImpl invitationServiceImpl) {
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
		this.registrationService = registrationService;
		this.roomService = roomService;
		this.messageRepository = messageRepository;
		this.messageService = messageServiceImpl;
		this.invitationService = invitationServiceImpl;
	}
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public void invokeTests() {	
		
		userRepository.deleteAll();
		roomRepository.deleteAll();
		messageRepository.deleteAll();
		createTestAccounts();
		createTestRooms();
		createTestInvitations();
	}
	
	
	// This method create test user accounts
	private void createTestAccounts() {
		
		String basicPath = "C:\\Users\\Hawke\\Desktop\\Praca inżynierska\\Disk\\Test\\";
		
		createAccount("marcin.mikolajczyk22@gmail.com", "anastazja2", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "anastazja2\\picture.bin");
		createAccount("ewelina@gmail.com", "ewelina32", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "ewelina32\\picture.bin");
		
		createAccount("mateusz87@gmail.com", "mateusz86", "#d2G@123423", AuthProvider.local.toString(), true, basicPath + "mateusz86\\picture.bin");
		createAccount("miłosz.mad@o2.pl", "mionszu2", "d1d2A@d12234", AuthProvider.local.toString(), true, basicPath + "mionszu2\\picture.bin");
//		createAccount("kacper78@gov.pl", "kacper78", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "kacper78\\picture.bin");
//	
//		
//		createAccount("kasia900@gmail.com", "kasia", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "kasia\\picture.bin");
//		createAccount("magda8920@yahoo.pl", "magdaPiwowar", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "magdaPiwowar\\picture.bin");
//		createAccount("paulina@mail.com", "paulinka", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "paulinka\\picture.bin");
//		createAccount("julia@gmail.com", "JulkaFromFrance", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "JulkaFromFrance\\picture.bin");
//		createAccount("asia2@gmail.com", "AsIa", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "AsIa\\picture.bin");
		
		
	}
	
	private void createAccount(String email, String nick, String password, String authProvider, boolean statements, String pathToImg) {
			RegistrationRequest registrationRequest = new RegistrationRequest(email, nick, password, authProvider, statements, loadPicture(pathToImg));
			registrationService.registerAccount(registrationRequest);
	}
	
	// Imitacja dołanczania zdjęcia podczas rejestracji
	public ProfilePicture loadPicture(String pathToImg) {
		String pictureInBase64 = FileUtils.deserializeObjectAndGetFromDirectory(pathToImg);
		ProfilePicture profilePicture = new ProfilePicture("profile.bin", "bin", 0, pictureInBase64);
		return profilePicture;
	}
	
	private void createTestRooms() {
		
//		private String roomId;
//		private String owner;
//		private String content;
		
		List<User> users1 = new ArrayList<User>();
		users1.add(userRepository.findByEmail("marcin.mikolajczyk22@gmail.com").get());
		users1.add(userRepository.findByEmail("mateusz87@gmail.com").get());
		users1.add(userRepository.findByEmail("ewelina@gmail.com").get());
		Room room = roomService.createRoom("Przyjaciele", users1);
		
		//Wed Oct 06 2021 05:12:30 GMT+0200 (Central European Summer Time)
		Message message01 = new Message(room.getRoomId(), "mateusz86", "Moje pierwsza wiadomość na chacie", "1633489950000");
		
		//Fri Oct 07 2022 05:12:30 GMT+0200 (Central European Summer Time)
		Message message0 = new Message(room.getRoomId(), "anastazja2", "Moje druga wiadomość na chacie", "1665112350000");
		Message message1 = new Message(room.getRoomId(), "anastazja2", "Hej co u cb słychać ?", "1665112350000");
		
		//Sun Oct 09 2022 23:23:42 GMT+0200 (Central European Summer Time)
		Message message3 = new Message(room.getRoomId(), "ewelina32", "Byłam zajęta kursem na prawo jazdy", "1665350589580");
		
		//Sun Oct 09 2022 23:28:47 GMT+0200 (Central European Summer Time)
		Message message2 = new Message(room.getRoomId(), "anastazja2", "Dawno się domnie nie odzywałaś.", "1665350927257");
		
		//Sun Oct 09 2022 23:35:11 GMT+0200 (Central European Summer Time)
		Message message4 = new Message(room.getRoomId(), "ewelina32", "Wiem, przepraszam cie !", "1665351311701");
		
		//Sun Oct 09 2022 23:41:21 GMT+0200 (Central European Summer Time)
		Message message5 = new Message(room.getRoomId(), "mateusz86", "Ma ktoś ochotę jutro wyjść ?", "1665351681759");
		
		messageService.save(message01);
		messageService.save(message0);
		messageService.save(message1);
		messageService.save(message2);
		messageService.save(message3);
		messageService.save(message4);
		messageService.save(message5);
		
		List<User> users2 = new ArrayList<User>();
		users2.add(userRepository.findByEmail("marcin.mikolajczyk22@gmail.com").get());
		users2.add(userRepository.findByEmail("mateusz87@gmail.com").get());
		roomService.createRoom("mateusz86", users2);
		
	}
	
	private void createTestInvitations() {
		
		// String inviting, String invited
		
		InvitationRequestPayload invitationRequestPayload01 = new InvitationRequestPayload("mionszu2", "anastazja2");
		invitationService.create(invitationRequestPayload01);
		
		InvitationRequestPayload invitationRequestPayload02 = new InvitationRequestPayload("mateusz86", "anastazja2");
		invitationService.create(invitationRequestPayload02);
		
		InvitationRequestPayload invitationRequestPayload03 = new InvitationRequestPayload("mateusz86", "anastazja2");
		invitationService.create(invitationRequestPayload03);
	}
	
}
