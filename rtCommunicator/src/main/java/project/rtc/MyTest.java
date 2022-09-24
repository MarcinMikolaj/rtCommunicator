package project.rtc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.rtc.authorization.oauth2.provider.AuthProvider;
import project.rtc.communicator.room.RoomRepository;
import project.rtc.registration.ProfilePicture;
import project.rtc.registration.RegistrationRequest;
import project.rtc.registration.RegistrationService;
import project.rtc.test.user.UserRepository;
import project.rtc.utils.FileUtils;

@RestController
public class MyTest {
	
	private RegistrationService registrationService;
	private UserRepository userRepository;
	private RoomRepository roomRepository;
	
	public MyTest(UserRepository userRepository, RoomRepository roomRepository, RegistrationService registrationService) {
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
		this.registrationService = registrationService;
	}
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public void invokeTests() {	
		
		userRepository.deleteAll();
		roomRepository.deleteAll();
		createTestAccounts();
	}
	
	
	private void createTestAccounts() {
		
		String basicPath = "C:\\Users\\Hawke\\Desktop\\Praca inżynierska\\Disk\\Test\\";
		
		createAccount("marcin.mikolajczyk22@gmail.com", "anastazja2", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "anastazja2\\picture.bin");
		createAccount("ewelina@gmail.com", "ewelina32", "d2A@1234", AuthProvider.local.toString(), true, basicPath + "ewelina32\\picture.bin");
		
		createAccount("mateusz87@gmail.com", "mateusz86", "#d2G@123423", AuthProvider.local.toString(), true, basicPath + "mateusz86\\picture.bin");
//		createAccount("miłosz.mad@o2.pl", "mionszu2", "d1d2A@d12234", AuthProvider.local.toString(), true, basicPath + "mionszu2\\picture.bin");
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
	
	
}
