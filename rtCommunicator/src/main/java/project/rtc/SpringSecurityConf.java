package project.rtc;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {
	
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(userDetailServiceImpl);
	}
	
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		  .authorizeRequests()
		    .antMatchers("/").permitAll()
		    .antMatchers(HttpMethod.GET, "/app/login").permitAll()
		  .and()
		  .formLogin()
		  ;
	}

}
