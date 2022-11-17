package messagingapp.ssd.Models;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private String role;
	private String token;

	public JwtResponse() {
	}

	public JwtResponse(String role, String token) {
		this.role = role;
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
