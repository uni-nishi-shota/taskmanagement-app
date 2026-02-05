package taskpractice.pack;

public class User {
	private int id;
    private String email;
    private String password;
    
    // デフォルトコンストラクタ
    public User() {}
    
    // コンストラクタ
    public User(String username, String password) {
        this.email = username;
        this.password = password;
    }
    
    // ゲッター・セッター
    public int getId() { return this.id; }
    public void setId(int id_) { this.id = id_; }
    
    public String getUsername() { return this.email; }
    public void setUsername(String email_) { this.email = email_; }
    
    public String getPassword() { return this.password; }
    public void setPassword(String password_) { this.password = password_; }
    
}
