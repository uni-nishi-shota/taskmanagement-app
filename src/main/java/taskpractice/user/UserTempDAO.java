package taskpractice.user;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserTempDAO {
	// データベース接続情報（環境に合わせて変更）
	private String URL;
	private String DB_USER;
	private String DB_PASSWORD; // 環境に合わせて変更

	// コンストラクタでapplication.propertiesから読み込む
	public UserTempDAO() {
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

	public void insert(UserTemp userTemp)throws SQLException {
		String sql = "INSERT INTO trn_user_temp (email, password, token, expire_at) VALUES (?, ?, ?, ?)";

		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, userTemp.getEmail());
			ps.setString(2, userTemp.getPasswordHash());
			ps.setString(3, userTemp.getToken());
			ps.setTimestamp(4, userTemp.getExpiresAt());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	public UserTemp findByToken(String token) {
		 String sql = "SELECT * FROM trn_user_temp WHERE token = ?";

		    try (Connection conn = getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {

		        ps.setString(1, token);
		        
		        try (ResultSet rs = ps.executeQuery()) {
		            if (rs.next()) {
		                UserTemp userTemp = new UserTemp();
		                userTemp.setId(rs.getInt("id"));
		                userTemp.setEmail(rs.getString("email"));
		                userTemp.setPasswordHash(rs.getString("password"));
		                userTemp.setToken(rs.getString("token"));
		                userTemp.setExpiresAt(rs.getTimestamp("expire_at"));
		                return userTemp;
		            }
		        }
		        
		    }catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		return null; // 仮の戻り値
	}

	public void deleteByToken(String token) {
		 String sql = "DELETE FROM trn_user_temp WHERE token = ?";

		    try (Connection conn = getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {

		        ps.setString(1, token);
		        ps.executeUpdate();
		    }catch (SQLException e) {
		        e.printStackTrace();
		    }
	}

}
