package taskpractice.task.pack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TaskHardDeleteServlet
 */
@WebServlet("/harddeletetask")
public class TaskHardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request_, HttpServletResponse response_)
			throws ServletException, IOException {
		int id = Integer.parseInt(request_.getParameter("id"));

		TaskDAO dao = new TaskDAO();
		Task task = new Task();
		dao.hardDeleteTask(id,task.getUserId());
		
		HttpSession session = request_.getSession();
		session.setAttribute("message", "タスクを完全削除しました");
		session.setAttribute("messageType", "complete-message");
		response_.sendRedirect("tasklist");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
	}

}

