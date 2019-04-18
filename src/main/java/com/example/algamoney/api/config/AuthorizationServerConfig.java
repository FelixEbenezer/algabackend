package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager; //que vai gerar autenticacao de usuario pegando senha e nome
	
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//O usuario autoriza o cliente a acessar o nosso ResourceServer
		clients.inMemory()  //configurar o cliente em memoria e nao em BDD
		.withClient("angular")  // o cliente que vai acessar o nosso ResourceServer ou API
		.secret("angu")    // a senha do cliente
		.scopes("read", "write")  //definir scopes ou direito do cliente, nao confundir cliente com usuario
		 // aqui estamos a definir como o usuario de nosso API da acesso ao ResourceServer ao
		// um determinado cliente
		.authorizedGrantTypes("password", "refresh_token")  //adicionar o refresch token que sera usado para nos dar o novo acces token
		.accessTokenValiditySeconds(120)//minutos durante o qual o token do cliente sera valido
		                                     //neste caso é 30min, 1800/60
		.refreshTokenValiditySeconds(3600*24)// o tempo de vida do refresh token, posemos 1 dia (24horas)
		//adicionamos um segundo cliente
		.and()
		.withClient("mobile")
		.secret("mobi")
		.scopes("read")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(60)
		.refreshTokenValiditySeconds(1800/24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.reuseRefreshTokens(false)  //sempre que pedir um novo refresh token, um outro novo refresh token sera mais criado para caso vir precisar mais e sucessivamente durante 1 dia
		                           // para que o cliente nao possa se desligar inesperadamente
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
	
	
}