package taskpractice.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TaskDAO {
	
	private String URL;
	private String DB_USER;
	private String DB_PASSWORD; // 環境に合わせて変更
	
	 // コンストラクタでapplication.propertiesから読み込む
    public TaskDAO() {
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

    // ユーザーのタスク一覧取得
    public List<Task> findActiveByUserId(int userId_) {
        List<Task> list = new ArrayList<>();

        String sql = "SELECT * FROM trn_tasks WHERE user_id = ? AND del_flag = 0 ORDER BY end_date ASC";

        try (Connection conn = this.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId_);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task();

                task.setId(rs.getInt("id"));
                task.setUserId(rs.getInt("user_id"));
                task.setTaskName(rs.getString("task_name"));
                task.setPlace(rs.getString("place"));
                task.setBeginDate(rs.getTimestamp("begin_date"));
                task.setEndDate(rs.getTimestamp("end_date"));
                task.setImportance(rs.getInt("importance"));
                task.setContent(rs.getString("content"));
                task.setCreatedAt(rs.getTimestamp("created_at"));
                task.setUpdatedAt(rs.getTimestamp("updated_at"));
                task.setDeletedAt(rs.getTimestamp("deleted_at"));
                task.setDeleteFlag(rs.getBoolean("del_flag"));

                list.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    //論理削除したタスクの一覧
    public List<Task> findDeletedByUserId(int userId_) {
        List<Task> list = new ArrayList<>();

        String sql = "SELECT * FROM trn_tasks WHERE user_id = ? AND del_flag = 1 ORDER BY end_date ASC";

        try (Connection conn = this.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId_);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task();

                task.setId(rs.getInt("id"));
                task.setUserId(rs.getInt("user_id"));
                task.setTaskName(rs.getString("task_name"));
                task.setPlace(rs.getString("place"));
                task.setBeginDate(rs.getTimestamp("begin_date"));
                task.setEndDate(rs.getTimestamp("end_date"));
                task.setImportance(rs.getInt("importance"));
                task.setContent(rs.getString("content"));
                task.setCreatedAt(rs.getTimestamp("created_at"));
                task.setUpdatedAt(rs.getTimestamp("updated_at"));
                task.setDeletedAt(rs.getTimestamp("deleted_at"));
                task.setDeleteFlag(rs.getBoolean("del_flag"));

                list.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // IDでタスク取得（編集用）
    public Task findById(int id_) {
        Task task =  null;
        try (Connection conn = this.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM trn_tasks WHERE id = ?"
                )) {

               ps.setInt(1, id_);

               ResultSet rs = ps.executeQuery();

               if (rs.next()) {  // ←ここがポイント（whileじゃない）
                   task = new Task();
                   task.setId(rs.getInt("id"));
                   task.setUserId(rs.getInt("user_id"));
                   task.setTaskName(rs.getString("task_name"));
                   task.setPlace(rs.getString("place"));
                   task.setBeginDate(rs.getTimestamp("begin_date"));
                   task.setEndDate(rs.getTimestamp("end_date"));
                   task.setImportance(rs.getInt("importance"));
                   task.setContent(rs.getString("content"));
                   task.setCreatedAt(rs.getTimestamp("created_at"));
                   task.setUpdatedAt(rs.getTimestamp("updated_at"));
                   task.setDeletedAt(rs.getTimestamp("deleted_at"));
                   task.setDeleteFlag(rs.getBoolean("del_flag"));
               }

           } catch (Exception e) {
               e.printStackTrace();
           }

           return task;
    }
    //タスク作成
    public void createTask(Task task_,int userId_) {
    	try (Connection conn = this.getConnection();
    	     PreparedStatement ps = conn.prepareStatement(
    	     "INSERT INTO trn_tasks ("
    	     + "user_id, "
    	     + "task_name,"
    	     + "place,"
    	     + "begin_date,"
    	     + "end_date,"
    	     + "importance,"
    	     + "content) VALUES (?, ?, ?, ?, ?, ?, ?)")){

    	        ps.setInt(1, userId_);
    	        ps.setString(2, task_.getTaskName());
    	        ps.setString(3, task_.getPlace());
    	        ps.setTimestamp(4, task_.getBeginDate());
    	        ps.setTimestamp(5, task_.getEndDate());
    	        ps.setInt(6, task_.getImportance());
    	        ps.setString(7, task_.getContent());
    	        
    	        ps.executeUpdate();
    	      
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	       
    	    }
	}
    
    //タスク更新（編集）
    public void updateTask(Task task_) {
		try (Connection conn = this.getConnection();
		     PreparedStatement ps = conn.prepareStatement(
		     "UPDATE trn_tasks SET "
		     + "task_name = ?,"
		     + "place = ?,"
		     + "begin_date = ?,"
		     + "end_date = ?,"
		     + "importance = ?,"
		     + "content = ?,"
		     + "updated_at = ? "
		     + "WHERE id = ?")){

		        ps.setString(1, task_.getTaskName());
		        ps.setString(2, task_.getPlace());
		        ps.setTimestamp(3, task_.getBeginDate());
		        ps.setTimestamp(4, task_.getEndDate());
		        ps.setInt(5, task_.getImportance());
		        ps.setString(6, task_.getContent());
		        ps.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
		        ps.setInt(8, task_.getId());
		        
		        ps.executeUpdate();

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}
    //復元
    public void restoreTask(int id_) {
    	try(Connection conn = this.getConnection();
			PreparedStatement ps = conn.prepareStatement(
			"UPDATE trn_tasks SET del_flag = 0  WHERE id = ?")){

		
			ps.setInt(1, id_);
			
			ps.executeUpdate();
			System.out.println("タスクが復元されました");
			

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("タスクの復元に失敗しました");
		}
    }
    
    // 論理削除
    public void softDeleteTask(int id_) {
    	try(Connection conn = this.getConnection();
			PreparedStatement ps = conn.prepareStatement(
			"UPDATE trn_tasks SET del_flag = 1, deleted_at = ? WHERE id = ?")){

			ps.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setInt(2, id_);
			
			ps.executeUpdate();
			System.out.println("タスクが論理削除されました");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("タスクの論理削除に失敗しました");
		}
    }
    
    //完全削除
    public void hardDeleteTask(int id_, int userId_) {
    	try(Connection conn = this.getConnection();
    		PreparedStatement ps = conn.prepareStatement(
    		"DELETE FROM trn_tasks WHERE id = ? AND user_id" )){
    		
    		ps.setInt(1, id_);
    		ps.executeUpdate();
    	}catch (Exception e) {
			e.printStackTrace();
			System.err.println("タスクの完全削除に失敗しました");
		}
    }
}
    


