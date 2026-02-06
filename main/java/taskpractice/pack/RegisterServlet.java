package taskpractice.pack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class registerServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// GET: 表示
	protected void doGet(HttpServletRequest request_, HttpServletResponse response_)
			throws ServletException, IOException {
		request_.getRequestDispatcher("jsp/register.jsp").forward(request_, response_);
	}

	// POST: 処理
	protected void doPost(HttpServletRequest request_, HttpServletResponse response_)
			throws ServletException, IOException {

		String email = request_.getParameter("email");
		String password = request_.getParameter("password");

		// 入力値チェック
		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			request_.setAttribute("error", "ユーザー名とパスワードを入力してください");
			request_.getRequestDispatcher("register.jsp").forward(request_, response_);
			return;
		}

		// データベースでの認証
		UserDAO userDAO = new UserDAO();
		int affectRows = userDAO.newRegister(email.trim(), password);

		if (affectRows == 0) {
			// 既にアカウントが存在している場合
			request_.setAttribute("error", "既にアカウントが存在しています");
			request_.getRequestDispatcher("jsp/register.jsp").forward(request_, response_);

		} else {
			// アカウント作成処理
			if (affectRows > 0) {
				HttpSession newSession = request_.getSession(true);
				newSession.setAttribute("message", "登録完了しました。");
				newSession.setAttribute("messageType", "complete-message");
				// ログインページにリダイレクト
				response_.sendRedirect("login");
				System.out.println("登録成功しました");
			} else {
				request_.setAttribute("error", "登録に失敗しました。再度お試しください");
				request_.getRequestDispatcher("jsp/register.jsp").forward(request_, response_);
			}
		}
	}
}
