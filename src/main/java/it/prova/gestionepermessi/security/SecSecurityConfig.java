package it.prova.gestionepermessi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
    private CustomAuthenticationSuccessHandlerImpl successHandler;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
         .userDetailsService(customUserDetailsService);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.authorizeRequests()
         .antMatchers("/assets/**").permitAll()
         .antMatchers("/login").permitAll()
         .antMatchers("/**").hasAnyRole("ADMIN", "BO_USER", "DIPENDENTE_USER")
         .anyRequest().authenticated()
         .and().exceptionHandling().accessDeniedPage("/accessDenied")
         .and()
         	.formLogin()
         	.loginPage("/login")
         	.defaultSuccessUrl("/home",true)
         	.successHandler(successHandler)
         	.failureUrl("/login?error=true")
         	.permitAll()
         .and()
         	.logout()
         	.logoutSuccessUrl("/executeLogout")
            .invalidateHttpSession(true)
            .permitAll()
         .and()
            .csrf()
            .disable();
    }
}
