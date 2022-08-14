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

import project.rtc.authorization.oauth2.CustomOAuth2UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {
	
	private CustomOAuth2UserService customOAuth2UserService;
	private CustomUserDetailsService customUserDetailsService;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}
    
	@Autowired
	public void setCustomOAuth2UserService(CustomOAuth2UserService customOAuth2UserService) {
		this.customOAuth2UserService = customOAuth2UserService;
	}
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("root").password(passwordEncoder.encode("root")).roles("ROOT");
		auth.userDetailsService(customUserDetailsService);
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
		   .logout().logoutSuccessUrl("/app/logout/success").permitAll();  
		      		 
	}
}
