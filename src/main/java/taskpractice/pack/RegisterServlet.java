package taskpractice.pack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import taskpractice.profile.ProfileDAO;
import taskpractice.user.UserDAO;

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
		// 文字化け対策
		request_.setCharacterEncoding("UTF-8");

		String email = request_.getParameter("email");
		String password = request_.getParameter("password");

		// 入力値チェック
		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			request_.setAttribute("message", "ユーザー名とパスワードを入力してください");
			request_.setAttribute("messageType", "error");
			request_.getRequestDispatcher("register.jsp").forward(request_, response_);
			return;
		}

		List<String> errors = new ArrayList<>();
		// メアドが英数字かどうか（ドメインの日本語入力はスルー）
		if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			errors.add("正しいメールアドレス形式で入力してください");
		}
		
		// パスワードの複雑さチェック（例: 大文字、小文字、数字、特殊文字を含む）
		if (password == null || password.length() < 8) {
			errors.add("パスワードは8文字以上で入力してください");
		}

		int count = 0;
		if (password.matches(".*[A-Z].*"))
			count++; // 大文字
		if (password.matches(".*[a-z].*"))
			count++; // 小文字
		if (password.matches(".*[0-9].*"))
			count++; // 数字
		if (password.matches(".*[^a-zA-Z0-9].*"))
			count++; // 記号

		if (count < 2) {
			errors.add("パスワードは2種類以上の文字種（大文字・小文字・数字・記号）を含めてください");
		}

		if (!errors.isEmpty()) {
			request_.setAttribute("errors", errors);
			request_.getRequestDispatcher("/jsp/register.jsp").forward(request_, response_);
			return;
		}

		// パスワードのハッシュ化
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		// データベースでの認証
		UserDAO userDAO = new UserDAO();
		int resultNum = userDAO.newRegister(email.trim(), hashedPassword);

		if (resultNum == 0) {
			// 既にアカウントが存在している場合
			request_.setAttribute("message", "既にアカウントが存在しています");
			request_.setAttribute("messageType", "error");
			request_.getRequestDispatcher("jsp/register.jsp").forward(request_, response_);

		} else {
			// アカウント作成処理
			if (resultNum > 0) {

				HttpSession session = request_.getSession();
				session.setAttribute("message", "登録完了しました。");
				session.setAttribute("messageType", "complete");

				ProfileDAO profileDAO = new ProfileDAO();
				int affectRows = profileDAO.createProfile(resultNum);
				if (affectRows > 0) {
					System.out.println("プロフィールの初期登録に成功しました");
				} else {
					System.out.println("プロフィールの初期登録に失敗しました");
				}

				// ログインページにリダイレクト
				response_.sendRedirect("login");
			} else {
				request_.setAttribute("message", "登録に失敗しました。再度お試しください");
				request_.setAttribute("messageType", "error");
				request_.getRequestDispatcher("jsp/register.jsp").forward(request_, response_);
			}
		}
	}
}
