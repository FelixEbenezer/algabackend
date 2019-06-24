package com.example.algamoney.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.algamoney.api.config.property.AlgamoneyApiProperty;


@Profile("oauth-security") // Também faltou o profile
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken>{
	
	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> arg1) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}
	
	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}
	
	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse res) {
		 // Definimos o nome do cookie para "refreshToken"
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(algamoneyApiProperty.getSeguranca().isEnableHttps());
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
		refreshTokenCookie.setMaxAge(2592000);
		res.addCookie(refreshTokenCookie);
	}
	

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body,
			MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req =  ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp =  ((ServletServerHttpResponse) response).getServletResponse();
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		String refreshToken = body.getRefreshToken().getValue();
		adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		removerRefreshTokenDoBody(token);
		
		return body;
	}
	

}

/*

@Profile("oauth-security")
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {
	//Repare que coloccamos <OAuth2AccessToken> como parametro de ResponseBodyAdvice
		//porque queremos capturar o o codigo token que é OAuth2AccessToken para mete lo em cookies
		//Se quisermos meter outra coisa em cookies como por exemplo uma categoria, entao
		// fariamos ResponseBodyAdvice<Categoria>, entao passariamos a guardar categoria
		//em cookies

	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty; 
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse res = ((ServletServerHttpResponse) response).getServletResponse();
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		String refreshToken = body.getRefreshToken().getValue(); //pegar a requisicao que esta no body e coloca no refreshToken
		adicionarResfreshTokenNoCookie(refreshToken, req, res);
		removerRefreshTokenNoBody(token);
		
		return body;
	}

	private void removerRefreshTokenNoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
		
	}

	private void adicionarResfreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse res) {
		// aqui criamos um cookie normal
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true); //para somente guardado no cookie http
		//refreshTokenCookie.setSecure(false);//deixamos aqui opção de ter um cookie seguro desativo prk ainda
		               //estamos em fase de desenvolvimento de API, quando estiver em producao
		              // tera que mudar para true para ter um cookie seguro
		
		refreshTokenCookie.setSecure(algamoneyApiProperty.getSeguranca().isEnableHttps());
		refreshTokenCookie.setPath(req.getContextPath()+"oauth/token");
		refreshTokenCookie.setMaxAge(2592000); //o tempo de vida de cookies, aqui e 30 dias
		res.addCookie(refreshTokenCookie);
		
	}
	
	

}
*/