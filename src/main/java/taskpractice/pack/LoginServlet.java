package taskpractice.pack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import taskpractice.profile.Profile;
import taskpractice.profile.ProfileDAO;
import taskpractice.user.User;
import taskpractice.user.UserDAO;

//import javax.servlet.http.*; 上の奴などをまとめてインポートしてる
/**
 * Servlet implementation class LoginServlet
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// GET: 表示
	protected void doGet(HttpServletRequest request_, HttpServletResponse response_)
			throws ServletException, IOException {
		request_.getRequestDispatcher("jsp/login.jsp").forward(request_, response_);
	}

	// POST: 処理
	protected void doPost(HttpServletRequest request_, HttpServletResponse response_)
			throws ServletException, IOException {

		String email = request_.getParameter("email");
		String password = request_.getParameter("password");

		// 入力値チェック
		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			request_.setAttribute("massage", "ユーザー名とパスワードを入力してください");
			request_.setAttribute("messageType", "error");
			request_.getRequestDispatcher("jsp/login.jsp").forward(request_, response_);
			return;
		}

		// データベースでの認証
		UserDAO userDAO = new UserDAO();
		User user = userDAO.findByEmail(email.trim());

		// 入力パスワードのハッシュ値照合
		if (user != null && BCrypt.checkpw(password, user.getPassword())) {
			// ログイン成功: セッションにユーザー情報を保存
			HttpSession oldSession = request_.getSession(false);
			if (oldSession != null) {
			    oldSession.invalidate();// 既存のセッションがあれば無効化して新しいセッションを作成 <=既存の奴は何処で作ってる？
			}

			HttpSession session = request_.getSession();
			session.setAttribute("user", user);// user変数にuserっていう名前の属性を設定
			session.setMaxInactiveInterval(60 * 30); // 30分でタイムアウト(server側より優先)

			// プロフィール情報も取得してセッションに保存
			ProfileDAO profileDAO = new ProfileDAO();
			Profile profile = profileDAO.getProfiles(user.getId());
			if (profile != null) {
				session.setAttribute("profile", profile);
			}
			System.out.println("Login successful: " + user.getEmail());
			response_.sendRedirect("logincomplete");// ここに描かれたページに跳ぶ
		} else {
			user = null; // 認証失敗
			// ログイン失敗
			System.out.println("Login failed for username: " + email);
			request_.setAttribute("message", "ユーザー名またはパスワードが間違っています");
			request_.setAttribute("messageType", "error");
			request_.getRequestDispatcher("jsp/login.jsp").forward(request_, response_);
		}
	}
}
