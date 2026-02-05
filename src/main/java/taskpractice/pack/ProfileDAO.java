package taskpractice.pack;

import java.sql.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;


public class ProfileDAO {

	
	private String URL;
	private String DB_USER;
	private String DB_PASSWORD; // 環境に合わせて変更
	
	 // コンストラクタでapplication.propertiesから読み込む
    public ProfileDAO() {
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
	
	

	//profile取得
	public Profile getProfiles(int userid_) {
		String sql = "SELECT * FROM mst_profiles WHERE user_id = ?"; // 仮にuser_idが1のプロフィールを取得する場合

		try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(sql)) {
				
				
				ps.setInt(1, userid_); // ここではuser_idを1に設定しています。実際のアプリケーションでは動的に設定する必要があります。

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Profile profile = new Profile();
					profile.setId(rs.getInt("id"));
					profile.setUserId(rs.getInt("user_id"));
					profile.setNickname(rs.getString("nickname"));
					profile.setBirth(rs.getDate("birth"));
					profile.setAge(rs.getInt("age"));
					profile.setJobCategory(rs.getString("job_category"));
					profile.setHobby(rs.getString("hobby"));
					profile.setMemo(rs.getString("memo"));
					profile.setIconPath(rs.getString("icon_image"));
					profile.setCreatedAt(rs.getString("created_at"));
					profile.setUpdatedAt(rs.getString("updated_at"));
					return profile;
					
				}
			}

		} catch (SQLException e) {
			System.err.println("error: " + e.getMessage());// サーバーからエラーが返ってきたときのエラー内容の表示
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	
	
	
	
	
	
	

}
