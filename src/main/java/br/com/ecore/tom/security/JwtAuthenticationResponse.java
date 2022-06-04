package br.com.ecore.tom.security;

public class JwtAuthenticationResponse {

	private String accessToken;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getTokenType() {
		return "Bearer";
	}

}
