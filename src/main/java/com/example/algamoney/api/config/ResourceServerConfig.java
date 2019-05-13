package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Profile("oauth-security")
@Configuration //Isto é opcional ....porque ja esta dentro de EnableSecurity
@EnableWebSecurity
@EnableResourceServer
//para configurar permissoes , habilitar segurança nos metodos:
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService; 
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	      //definir senha e usuario para autenticacao basica em memoria e nao em BDD
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
		// TODO Auto-generated method stub
		return new OAuth2MethodSecurityExpressionHandler();
	}



	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()  //Definir as autorizacoes
		    .antMatchers("/categorias").permitAll()   //usar recurso categorias sem se autenticar nem senha nem user
		    .anyRequest().authenticated()           //todos os outros requisitos para o resto de recurso deve se autenticar
		    .and()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		    .and().csrf().disable();
		//nao é mais httpBasic, é por isso eliminamo lo
		    
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}

}
