package project.rtc.authorization.security;

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
public class CustomUserDetailsService implements UserDetailsService {
	
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	public void setCredentialsRepository(CredentialsRepositoryImpl credentialsRepositoryImpl) {
		this.credentialsRepository = credentialsRepositoryImpl;
	}

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
