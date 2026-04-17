package taskpractice.task.pack;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import taskpractice.pack.User;

@WebServlet("/tasksave")
public class TaskSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request_, HttpServletResponse response_)
            throws ServletException, IOException {
    	
    	//文字化け対策
    			request_.setCharacterEncoding("UTF-8");
    			response_.setContentType("text/html; charset=UTF-8");

        //パラメータ取得
        String idStr = request_.getParameter("id");
        String taskName = request_.getParameter("taskName");
        String place = request_.getParameter("place");
        String beginDateStr = request_.getParameter("beginDate");
        String endDateStr = request_.getParameter("endDate");
        String importanceStr = request_.getParameter("importance");
        String content = request_.getParameter("content");
        
        //開始日が締切日よりあとになった場合のエラー処理
        if (beginDateStr != null && endDateStr != null && !beginDateStr.isEmpty() && !endDateStr.isEmpty()) {
			LocalDateTime beginLdt = LocalDateTime.parse(beginDateStr);
			LocalDateTime endLdt = LocalDateTime.parse(endDateStr);
			if (beginLdt.isAfter(endLdt)) {
				// エラーメッセージをセッションに保存
				request_.getSession().setAttribute("message", "開始日は締切日より前にしてください");
				request_.getSession().setAttribute("messageType", "error");
				// フォームにリダイレクト
				response_.sendRedirect("taskform" + (idStr != null ? "?id=" + idStr : ""));
				return;
				//ここには入ってきてる。。。メッセの種類が違う？
			}
		}

        //型変換
        Timestamp beginDate = null;
        if (beginDateStr != null && !beginDateStr.isEmpty()) {
            LocalDateTime ldt = LocalDateTime.parse(beginDateStr);
            beginDate = Timestamp.valueOf(ldt);
        }

        Timestamp endDate = null;
        if (endDateStr != null && !endDateStr.isEmpty()) {
            LocalDateTime ldt = LocalDateTime.parse(endDateStr);
            endDate = Timestamp.valueOf(ldt);
        }

        int importance = 0;
        if (importanceStr != null && !importanceStr.isEmpty()) {
            importance = Integer.parseInt(importanceStr);
        }

        //Taskに詰める
        Task task = new Task();
        task.setTaskName(taskName);
        task.setPlace(place);
        task.setBeginDate(beginDate);
        task.setEndDate(endDate);
        task.setImportance(importance);
        task.setContent(content);

        // 仮：ユーザーID（あとでセッションから）
        task.setUserId(1);

        TaskDAO dao = new TaskDAO();
        
        
        //insert or update 分岐
        if (idStr == null || idStr.isEmpty()) {
            // 新規
        	User user = (User) request_.getSession().getAttribute("user");
            dao.createTask(task, user.getId());
            request_.getSession().setAttribute("message", "タスクを作成しました");
			request_.getSession().setAttribute("messageType", "complete");
        } else {
            // 更新
            int id = Integer.parseInt(idStr);
            task.setId(id);

            // updated_at 更新
            task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            dao.updateTask(task);
            request_.getSession().setAttribute("message", "タスクを編集しました");
			request_.getSession().setAttribute("messageType", "complete");
        }

        //一覧へリダイレクト
        response_.sendRedirect("tasklist");
    }
}

