package taskpractice.pack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request_, HttpServletResponse response_) 
            throws ServletException, IOException {
        
        // セッション取得と無効化
        HttpSession session = request_.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                System.out.println("Logout: " + user.getUsername());
            }
            session.invalidate(); // セッション破棄 sessionのisValidはfalseになる
            System.out.println("セッションは破棄されました"); //sessionが消えたかどうかまでは分からない
        }
        
        HttpSession newSession = request_.getSession(true);
        newSession.setAttribute("message", "ログアウトしました");
        newSession.setAttribute("messageType", "error-message");
        
        // ログインページにリダイレクト
        response_.sendRedirect("login");
    }
}