package project.rtc.authorization.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.rtc.authorization.basic_login.credentials.dto.Credentials;
import project.rtc.authorization.basic_login.credentials.repositories.CredentialsRepository;
import project.rtc.authorization.basic_login.credentials.repositories.impl.CredentialsRepositoryImpl;
import project.rtc.utils.ConsoleColors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final CredentialsRepository credentialsRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Credentials credentials = credentialsRepository.findByEmail(username);
		
		if(credentials != null) {
			return credentials;
		} else {
			throw new UsernameNotFoundException(ConsoleColors.BLUE +
					"ustomUserDetailsService.loadUserByUsername: not found credentials for username: " + username
					+ ConsoleColors.RESET);
		}
		
		
	}

}
