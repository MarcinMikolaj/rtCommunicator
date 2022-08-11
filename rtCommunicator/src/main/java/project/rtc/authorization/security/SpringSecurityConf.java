package project.rtc.authorization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import project.rtc.oauth2.CustomOAuth2UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
//@ComponentScan("project.rtc.authorization.credentials.CustomOidcUserService;")
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {
	
//	private CustomOidcUserService customOidcUserService;
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Autowired
//	public void setCustomOidcUserService(CustomOidcUserService customOidcUserService) {
//		this.customOidcUserService = customOidcUserService;
//	}
	
	@Autowired
	public void setCustomOAuth2UserService(CustomOAuth2UserService customOAuth2UserService) {
		this.customOAuth2UserService = customOAuth2UserService;
	}
	
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("pass1")).roles("USER");
		auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.encode("pass2")).roles("USER");
		
//		auth.userDetailsService(userDetailServiceImpl);
	}
	
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		  .authorizeRequests()
		    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		      .antMatchers("/resources/**").permitAll()
		      .antMatchers("static/LoginPage/**").permitAll()
		      .antMatchers("/").authenticated()
		      .antMatchers(HttpMethod.GET, "/app/panel").authenticated()
		      .antMatchers(HttpMethod.GET, "/test").permitAll()
		  .and()
		  .formLogin()
		      .loginPage("/app/login").permitAll()
		      .usernameParameter("username")
		      .passwordParameter("password")  
		      .defaultSuccessUrl("/app/panel")
		  .and()
		      .oauth2Login()
		        .redirectionEndpoint()
		            .baseUri("/login/oauth2/code/**")
		            .and()
		        .userInfoEndpoint()
		           .userService(customOAuth2UserService)
		           .and()
		   .and()
		   .logout().logoutSuccessUrl("/app/logout/success").permitAll()
		;  
		      
		      
//		        .redirectionEndpoint().baseUri("/login/oauth2/code/**")
//		      .and()
//		        .userInfoEndpoint()
//		           .oidcUserService(customOidcUserService)
//		      .and()
		   
	}
}
