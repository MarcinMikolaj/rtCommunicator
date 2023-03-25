package project.rtc.authorization.oauth2;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.credentials.entities.Credentials;
import project.rtc.authorization.basic_login.credentials.repositories.CredentialsRepository;
import project.rtc.authorization.oauth2.provider.AuthProvider;
import project.rtc.authorization.oauth2.provider.OAuth2UserInfo;
import project.rtc.authorization.oauth2.provider.OAuth2UserInfoFactory;
import project.rtc.infrastructure.utils.ConsoleColors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService{
	
	private final CredentialsRepository credentialsRepository;
	private final PasswordEncoder passwordEncoder;


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
				throw new OAuth2AuthenticationException("You already have an account with this email address !");
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
		credentials.setPassword(passwordEncoder.encode(generatePassword()));
		credentials.setEmail(oAuth2UserInfo.getEmail());
		credentials.setName(oAuth2UserInfo.getName());
		credentials.setProvider(userRequest.getClientRegistration().getRegistrationId());
		
		
		return credentialsRepository.save(credentials);
		
	}
	
	// Allows you to generate a password
	private String generatePassword() {
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
		String password = RandomStringUtils.random(10, characters);
		System.out.println("Wygenerowałem chasło: " + password);
		
		return password;
	}

}
