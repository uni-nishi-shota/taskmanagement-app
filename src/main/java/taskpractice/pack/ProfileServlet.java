package taskpractice.pack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request_, HttpServletResponse response_)
			throws ServletException, IOException {
		// キャッシュ無効化設定
		response_.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response_.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response_.setDateHeader("Expires", 0); // 過去の日付を設定して即時期限切れにする
		// この行以降は、ブラウザバック時に処理されない

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
		ProfileDAO profileDAO = new ProfileDAO();
		Profile profile = profileDAO
				.getProfiles(session.getAttribute("userId") != null ? (int) session.getAttribute("userId") : 0);
		request_.setAttribute("profile", profile);

		if (profile != null) {
			System.out.println("profile情報、入ってる！" + profile.getNickname());
		} else {
			System.out.println("profile情報、入ってない！");
		}
		request_.getRequestDispatcher("jsp/profile.jsp").forward(request_, response_);
	}

	protected void doPost(HttpServletRequest request_, HttpServletResponse response_)
			throws ServletException, IOException {

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

	}

}
