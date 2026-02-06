package taskpractice.pack;

import java.sql.Date;

public class Profile {
	private int id;
	private int userId;
	private String nickname;
	private Date birth;
	private int age;
	private String jobCategory;
	private String hobby;
	private String memo;
	private String iconImagePath;
	private Date createdAt;
	private Date updatedAt;
	
	// デフォルトコンストラクタ
    public Profile() {}
	
	//コンストラクタ
	public Profile(int userId_) {
		this.userId = userId_;
		
	}
	
	//ゲッター・セッター
	public int getId() {return id;}
	public void setId(int id_) {this.id = id_;}
	
	public int getUserId() {return userId;}
	public void setUserId(int userId_) {this.userId = userId_;}
	
	public String getNickname() {return nickname;}
	public void setNickname(String nickname_) {this.nickname = nickname_;}
	
	public Date getBirth() {return birth;}
	public void setBirth(Date birth_) {this.birth = birth_;}
	
	public int getAge() {return age;}
	public void setAge(int age_) {this.age = age_;}
	
	public String getJobCategory() {return jobCategory;}
	public void setJobCategory(String jobCategory_) {this.jobCategory = jobCategory_;}
	
	public String getHobby() {return hobby;}
	public void setHobby(String hobby_) {this.hobby = hobby_;}
	
	public String getMemo() {return memo;}
	public void setMemo(String memo_) {this.memo = memo_;}
	
	public String getIconPath() {return iconImagePath;}
	public void setIconPath(String iconPath_) {this.iconImagePath = iconPath_;}
	
	public Date getCreatedAt() {return createdAt;}
	public void setCreatedAt(Date createdAt_) {this.createdAt = createdAt_;}
	
	public Date getUpdatedAt() {return updatedAt;}
	public void setUpdatedAt(Date updatedAt_) {this.updatedAt = updatedAt_;}

}
