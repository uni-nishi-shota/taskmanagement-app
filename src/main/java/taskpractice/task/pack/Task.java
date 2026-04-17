package taskpractice.task.pack;


import java.sql.Timestamp;

public class Task {
	
	private int id;
	private int userId;
	private String taskName;
	private String place;
	private Timestamp beginDate;
	private Timestamp endDate;
	private int importance;
	private String content;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private int status;
	private String category;
	private boolean deleteFlag;
	private Timestamp deletedAt;
	
	// デフォルトコンストラクタ
	public Task() {}
	
	// コンストラクタ
	public Task(int userId_) {
		this.userId = userId_;
	}
	
	// ゲッター・セッター
	public int getId() {return id;}
	public void setId(int id_) {this.id = id_;}
	
	public int getUserId() {return userId;}
	public void setUserId(int userId_) {this.userId = userId_;}
	
	public String getTaskName() {return taskName;}
	public void setTaskName(String taskName_) {this.taskName = taskName_;}
	
	public String getPlace() {return place;}
	public void setPlace(String place_) {this.place = place_;}
	
	public Timestamp getBeginDate() {return beginDate;}
	public void setBeginDate(Timestamp beginDate_) {this.beginDate = beginDate_;}
	
	public Timestamp getEndDate() {return endDate;}
	public void setEndDate(Timestamp endDate_) {this.endDate = endDate_;}
	
	public int getImportance() {return importance;}
	public void setImportance(int importance_) {this.importance = importance_;}
	
	public String getContent() {return content;}
	public void setContent(String content_) {this.content = content_;}
	
	public Timestamp getCreatedAt() {return createdAt;}
	public void setCreatedAt(Timestamp createdAt_) {this.createdAt = createdAt_;}
	
	public Timestamp getUpdatedAt() {return updatedAt;}
	public void setUpdatedAt(Timestamp updatedAt_) {this.updatedAt = updatedAt_;}
	
	//今後追加予定（SQL）
	public int getStatus() {return status;}
	public void setStatus(int status_) {this.status = status_;}
	
	//今後追加予定（SQL）
	public String getCategory() {return category;}
	public void setCategory(String category_) {this.category = category_;}
	
	public boolean isDeleteFlag() {return deleteFlag;}
	public void setDeleteFlag(boolean deleteFlag_) {this.deleteFlag = deleteFlag_;}
	
	public Timestamp getDeletedAt() {return deletedAt;}
	public void setDeletedAt(Timestamp deletedAt_) {this.deletedAt = deletedAt_;}
	
	
	
	

}
