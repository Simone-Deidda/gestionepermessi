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
         .antMatchers("/utente/**").hasRole("ADMIN")
         .antMatchers("/dipendente/", "/dipendente/list", "/dipendente/search", "/dipendente/show/**")
         					.hasAnyRole("ADMIN", "BO_USER")
		 .antMatchers("/dipendente/**").hasRole("BO_USER")
		 .antMatchers("/messaggio/**").hasRole("BO_USER")
		 .antMatchers("/richiestapermesso/cambiaStato/**").hasRole("BO_USER")
		 .antMatchers("/richiestapermesso/", "/richiestapermesso/search", "/richiestapermesso/list",
				 	  "/richiestapermesso/show/**")
		 					.hasAnyRole("BO_USER", "DIPENDENTE_USER")
		 .antMatchers("/richiestapermesso/**").hasRole("DIPENDENTE_USER")
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
