package taskpractice.profile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JobCategoryDAO {
	
	private String URL;
	private String DB_USER;
	private String DB_PASSWORD; // 環境に合わせて変更
	
	 // コンストラクタでapplication.propertiesから読み込む
    public JobCategoryDAO() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.err.println("application.propertiesが見つかりません");
                return;
            }
            prop.load(input);
            URL = prop.getProperty("db.url");
            DB_USER = prop.getProperty("db.user");
            DB_PASSWORD = prop.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // JDBCドライバーの読み込み
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
    }
	
	
	public List<JobCategory> getJobCategory(){
		List<JobCategory> list = new ArrayList<>();
		String sql = "SELECT * FROM mst_jobCategory";
		
		  try (Connection conn = this.getConnection();
		       PreparedStatement ps = conn.prepareStatement(sql)) {
			  
			   ResultSet rs = ps.executeQuery();
               while(rs.next()) { 
            	   JobCategory jobCategory = new JobCategory();
            	   
            	   
            	   jobCategory.setJobName(rs.getString("job"));
            	   list.add(jobCategory);
            	   //TODO これで完成？？？
               }
			  
		  } catch (Exception e) {
              e.printStackTrace();
              return null;
          }
		return list;
	}
}
