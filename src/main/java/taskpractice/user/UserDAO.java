package taskpractice.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class UserDAO {
	// データベース接続情報（環境に合わせて変更）
	private String URL;
	private String DB_USER;
	private String DB_PASSWORD; // 環境に合わせて変更
	
	 // コンストラクタでapplication.propertiesから読み込む
	public UserDAO() {
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
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("MySQL JDBC driver loaded successfully");
		} catch (ClassNotFoundException e) {
			System.err.println("MySQL JDBC driver not found: " + e.getMessage());
			throw new RuntimeException("MySQL driver not found in classpath", e);
		}
	}
	
	/**
	 * 新規ユーザー登録
	 * 
	 * @param email_    ユーザー名
	 * @param password_ パスワード
	 * @return 登録成功時は影響を受けた行数、重複エントリ時は0、失敗時は-1
	 */
	public int newRegister(String email_, String password_) {
		String sql = "INSERT INTO mst_users (login_email, login_password) VALUES (?, ?)";
		int generatedId = -1;
		
		try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, email_);
			ps.setString(2, password_);

			int affectRows = ps.executeUpdate();
			if(affectRows == 0) {
				return 0; // 挿入に失敗した場合
			}

			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					generatedId = generatedKeys.getInt(1);
					System.out.println("New user ID: " + generatedId);
					return generatedId;
				}
			}
		}
		catch (SQLIntegrityConstraintViolationException e) {
			// 重複エントリのエラー処理
			System.err.println("Register error: Duplicate entry for email " + email_);
			return 0; // 既に存在する場合は0を返す
		}
		catch (SQLException e) {
			System.err.println("Register error: " + e.getMessage());// サーバーからエラーが返ってきたときのエラー内容の表示
			e.printStackTrace();
		}
		return -1; // その他のSQLエラーの場合は-1を返す
		
	}

	/**
	 * ユーザー認証
	 * 
	 * @param email_    ユーザー名
	 * @param password_ パスワード
	 * @return 認証成功時はUserオブジェクト、失敗時はnull
	 */
	public User findByEmail(String email_) {
		String sql = "SELECT id, login_email,login_password FROM mst_users WHERE login_email = ?";

		try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email_);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setEmail(rs.getString("login_email"));
					user.setPassword(rs.getString("login_password"));// データベースのカラム名と合わせる
					return user;
				}
			}
		} catch (SQLException e) {
			System.err.println("Authentication error: " + e.getMessage());// サーバーからエラーが返ってきたときのエラー内容の表示
			e.printStackTrace();
		}
		return null;
	}
	
	//全ユーザーの取得
	public List<User> getAllUsers() {// 全ユーザーの取得・ユーザーリストをreturnする。ダッシュボードで使ってる
		List<User> users = new ArrayList<>();
		String sql = "SELECT id, login_email FROM mst_users ORDER BY id";

		try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("login_email"));
				users.add(user);
			}
		} catch (SQLException e) {
			System.err.println("Get all users error: " + e.getMessage());
			e.printStackTrace();
		}
		return users;
	}
}
