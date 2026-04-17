package taskpractice.profile.pack;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import taskpractice.pack.User;

@WebFilter("/*")
public class ProfileLoadFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request_, ServletResponse response_, FilterChain chain_)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request_;
		HttpSession session = req.getSession(false);

		// ログイン済みなら毎回最新プロフィールをDBから取得
		if (session != null && session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");

			ProfileDAO profileDAO = new ProfileDAO();
			Profile profile = profileDAO.getProfiles(user.getId());

			// JSP で${profile} で参照できるようにする
			request_.setAttribute("profile", profile);
		}

		// 次のフィルター or Servlet へ処理を渡す
		chain_.doFilter(request_, response_);
	}
}
