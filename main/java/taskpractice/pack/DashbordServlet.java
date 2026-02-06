package taskpractice.pack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Servlet implementation class DashbordServlet
 */
@WebServlet("/dashbord")
public class DashbordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 protected void doGet(HttpServletRequest request_, HttpServletResponse response_) 
	            throws ServletException, IOException {
		// キャッシュ無効化設定
			response_.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
			response_.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response_.setDateHeader("Expires", 0); // 過去の日付を設定して即時期限切れにする
		
		 
		// セッション認証チェック
	        HttpSession session = request_.getSession(false);
	        if (session == null || session.getAttribute("user") == null) {
	            System.out.println("Unauthorized access attempt to dashboard");
	         // sessionが切れてますよっていう文字と文字の色指定を追加
	            HttpSession newSession = request_.getSession(true);
			    newSession.setAttribute("message", "セッションが切れました");
			    newSession.setAttribute("messageType", "error-message");
	            response_.sendRedirect("login");
	            return;
	        }

	        
		 // ログイン済みユーザーの処理
	        User currentUser = (User) session.getAttribute("user");
	        System.out.println("Dashboard accessed by: " + currentUser.getUsername());
	        
	        // ユーザー一覧をデータベースから取得
	        UserDAO userDAO = new UserDAO();
	        List<User> users = userDAO.getAllUsers();
	        
	        // JSPに渡すためのリクエスト属性設定
	        request_.setAttribute("users", users);
	        request_.setAttribute("currentUser", currentUser);
	        
	        // ダッシュボードページに転送
	        request_.getRequestDispatcher("jsp/dashbord.jsp").forward(request_, response_);
	        
	 }
}
   