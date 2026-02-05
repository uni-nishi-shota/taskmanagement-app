package taskpractice.pack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request_.setAttribute("error", "ユーザー名とパスワードを入力してください");
            request_.getRequestDispatcher("jsp/login.jsp").forward(request_, response_);
            return;
        }
        
        // データベースでの認証
        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticate(email.trim(), password);
      
        if (user != null) {
            // ログイン成功: セッションにユーザー情報を保存
            HttpSession session = request_.getSession();
            session.setAttribute("user", user);//user変数にuserっていう名前の属性を設定
            session.setMaxInactiveInterval(60*30); // 30分でタイムアウト(server側より優先)
            //ログイン成功時に、成功した旨の画面を出す。
            System.out.println("Login successful: " + user.getUsername());
            response_.sendRedirect("logincomplete");//ここに描かれたページに跳ぶ
        } else {
            // ログイン失敗
            System.out.println("Login failed for username: " + email);
            request_.setAttribute("error", "ユーザー名またはパスワードが間違っています");
            request_.getRequestDispatcher("jsp/login.jsp").forward(request_, response_);
        }
    }
}


