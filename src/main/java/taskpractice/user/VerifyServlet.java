package taskpractice.user;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String token = request.getParameter("token");
		UserTempDAO tempDao = new UserTempDAO();

		try {
			// DBにトークンで検索して、存在するかチェック
			UserTemp temp = tempDao.findByToken(token);

			// 存在チェック
			if (temp == null) {
				request.setAttribute("error", "無効なURLです");
				request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
				return;
			}
			// 有効期限チェック
			if (temp.getExpiresAt().before(new Timestamp(System.currentTimeMillis()))) {
				request.setAttribute("error", "有効期限が切れています");
				request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
				return;
			}

			// 本登録
			UserDAO userDao = new UserDAO();
			// userDao.insertFromTemp(temp); // ←後で作る

			// 仮データ削除
			tempDao.deleteByToken(token);

			// 成功画面
			request.getRequestDispatcher("/jsp/verify_success.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "システムエラーが発生しました");
			request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
