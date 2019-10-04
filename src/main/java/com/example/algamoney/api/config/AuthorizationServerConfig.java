package com.example.algamoney.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.example.algamoney.api.config.token.CustomTokenEnhancer;

@Profile("oauth-security")
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager; //que vai gerar autenticacao de usuario pegando senha e nome
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//O usuario autoriza o cliente a acessar o nosso ResourceServer
		clients.inMemory()  //configurar o cliente em memoria e nao em BDD
		.withClient("angular")  // o cliente que vai acessar o nosso ResourceServer ou API
		.secret("$2a$10$dqF909jo27aldh/lyVVj3ujKhn8SEjL9OazVqTcoIxkQU1jWu.Zsu") //angu   // a senha do cliente
		.scopes("read", "write")  //definir scopes ou direito do cliente, nao confundir cliente com usuario
		 // aqui estamos a definir como o usuario de nosso API da acesso ao ResourceServer ao
		// um determinado cliente
		.authorizedGrantTypes("password", "refresh_token")  //adicionar o refresch token que sera usado para nos dar o novo acces token
		.accessTokenValiditySeconds(240)//minutos durante o qual o token do cliente sera valido
		                                     //neste caso é 30min, 1800/60
		.refreshTokenValiditySeconds(960)// o tempo de vida do refresh token, posemos 1 dia (24horas)
		//adicionamos um segundo cliente
		.and()
		.withClient("mobile")
		.secret("$2a$10$4qoEDtZshyDTA0j6dVd7u.9Cgn8MBR9LL8O76pTMygXkXuqXhMmde") //mobi
		.scopes("read")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(60)
		.refreshTokenValiditySeconds(1800/24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		
		endpoints
		.tokenStore(tokenStore())
		.tokenEnhancer(tokenEnhancerChain)
		.reuseRefreshTokens(false)  //sempre que pedir um novo refresh token, um outro novo refresh token sera mais criado para caso vir precisar mais e sucessivamente durante 1 dia
		                           // para que o cliente nao possa se desligar inesperadamente
		.userDetailsService(userDetailsService)
		.authenticationManager(authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks"); //para validar a assinatura
		return accessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() 
	{
		return new JwtTokenStore(accessTokenConverter());
	}
	

	@Bean
	public TokenEnhancer tokenEnhancer() 
	{
		return new CustomTokenEnhancer();
	}

	
	
}
