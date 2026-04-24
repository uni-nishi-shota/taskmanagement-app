package taskpractice.user;

import java.sql.Timestamp;

public class UserTemp {
	private int id;
	private String email;
	private String passwordHash;
	private String token;
	private Timestamp expiresAt;
	private Timestamp createdAt;

	public UserTemp() {

	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	public String getPasswordHash() {return passwordHash;}
	public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}

	public String getToken() {return token;}
	public void setToken(String token) {this.token = token;}
	
	public Timestamp getExpiresAt() {return expiresAt;}
	public void setExpiresAt(Timestamp expiresAt) {this.expiresAt = expiresAt;}

	public Timestamp getCreatedAt() {return createdAt;}
	public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}

}
