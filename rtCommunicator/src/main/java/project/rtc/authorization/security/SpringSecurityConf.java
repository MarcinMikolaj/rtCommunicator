package project.rtc.authorization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import project.rtc.authorization.oauth2.CustomOAuth2UserService;
import project.rtc.authorization.oauth2.OAuth2AuthenticationSuccessHandler;
import project.rtc.authorization.security.jwt.JwtTokenAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {
	
	private CustomOAuth2UserService customOAuth2UserService;
	private CustomUserDetailsService customUserDetailsService;
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
	
	@Autowired
	public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}
    
	@Autowired
	public void setCustomOAuth2UserService(CustomOAuth2UserService customOAuth2UserService) {
		this.customOAuth2UserService = customOAuth2UserService;
	}
	
	@Autowired
	public void setJwtTokenAuthenticationFilter(JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) {
		this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
	}
	
	@Autowired
	private void setAuthenticationSuccessHandler(OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) {
		this.authenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
	}
	
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(customUserDetailsService);
	}
	
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		   .csrf()
		       .disable()
		    .formLogin()
		        .disable()
		     .httpBasic()
                .disable()
		    .authorizeRequests()
			    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			       .antMatchers("/resources/**").permitAll()
			       .antMatchers("/error",
	                        "/favicon.ico",
	                        "/**/*.png",
	                        "/**/*.gif",
	                        "/**/*.svg",
	                        "/**/*.jpg",
	                        "/**/*.html",
	                        "/**/*.css",
	                        "/**/*.js")
	                        .permitAll()
	               .antMatchers("/").permitAll() //test 
	               .antMatchers("/app/register").permitAll() // GET register form
	               .antMatchers("/app/registration/create").permitAll() // POST create account 
	               .antMatchers("/app/registration/activate").permitAll() // POST activate account  
	               .antMatchers("/auth/**", "/oauth2/**").permitAll()	              
			       .antMatchers("/app/login").permitAll()
			       .antMatchers("/app/logout").permitAll()
			       .antMatchers("/app/forgot").permitAll()
			       .antMatchers("/app/forgot/send").permitAll()
			       .antMatchers("/app/forgot/password/tk/**").permitAll()
			       .antMatchers("/app/forgot/credentials/update").permitAll()
			       .anyRequest().authenticated()
			       .and()			       
		    .oauth2Login()
		        .loginPage("/app/login")
		            .permitAll()
		        .redirectionEndpoint()
		            .baseUri("/login/oauth2/code/**")
		            .and()
		        .userInfoEndpoint()
		           .userService(customOAuth2UserService)
		           .and()
		        .successHandler(authenticationSuccessHandler)
		       .and()
		   .logout()
		        .disable();
		
		// Add JWT token filter 
		http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
				      		 
	}
}
