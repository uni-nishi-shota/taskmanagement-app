package taskpractice.task.pack;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import taskpractice.pack.User;

/**
 * Servlet implementation class TasklistServlet
 */
@WebServlet("/tasklist")
public class TaskListServlet extends HttpServlet {
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
        
        User user = (User) session.getAttribute("user");
        TaskDAO dao = new TaskDAO();
        
        List<Task> list = dao.findActiveByUserId(user.getId());
        String mode = request_.getParameter("mode");
        if("trash".equals(mode)) {
			 list = dao.findDeletedByUserId(user.getId());
		}
        else if("active".equals(mode)) {
        	 list = dao.findActiveByUserId(user.getId());
        }
       
        request_.setAttribute("mode", mode);
        request_.setAttribute("taskList", list);
        request_.getRequestDispatcher("jsp/tasklist.jsp").forward(request_, response_);
     
	}
}