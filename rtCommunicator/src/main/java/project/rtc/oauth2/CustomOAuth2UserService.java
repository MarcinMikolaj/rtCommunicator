package project.rtc.oauth2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import project.rtc.ConsoleColors;
import project.rtc.authorization.credentials.Credentials;
import project.rtc.authorization.credentials.CredentialsRepository;
import project.rtc.authorization.credentials.CredentialsRepositoryImpl;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{
	
	private CredentialsRepository credentialsRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setCredentialsRepository(CredentialsRepositoryImpl credentialsRepositoryImpl) {
		this.credentialsRepository = credentialsRepositoryImpl;
	}
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
	
		try {
			return processOAuth2User(userRequest, oAuth2User);
		} catch (AuthenticationException e) {
			throw e;
		} catch (Exception e) {
			System.out.print(ConsoleColors.RED_BACKGROUND + " " + e.getMessage() + ConsoleColors.RESET);
		}
		
		return super.loadUser(userRequest);
	}
	
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		
		Credentials credentials = credentialsRepository.findByEmail(oAuth2UserInfo.getEmail());
		
		if(credentials != null) {
			
			if(!credentials.getProvider().equals(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()).toString())) {
				System.out.println("Okazało sie że: " + credentials.getProvider() + " oraz " 
			+ AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()) + " są różne !!");
				throw new OAuth2AuthenticationProcess("You already have account with this email (google or fb)");
			}
			
			updateCredentials(userRequest, oAuth2UserInfo);
			
		} else {
			credentials = createNewCredentials(userRequest, oAuth2UserInfo);
		}
		
		
		return oAuth2User;
	}
	
	private Credentials updateCredentials(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
		return null;
	}
	
	private Credentials createNewCredentials(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
		
		Credentials credentials = new Credentials();
		credentials.setLogin(oAuth2UserInfo.getEmail());
		credentials.setPassword(passwordEncoder.encode("12345678"));
		credentials.setEmail(oAuth2UserInfo.getEmail());
		credentials.setName(oAuth2UserInfo.getName());
		credentials.setProvider(userRequest.getClientRegistration().getRegistrationId());
		
		
		return credentialsRepository.save(credentials);
		
	}

}
