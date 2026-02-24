package taskpractice.pack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
@MultipartConfig
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
		User user = (User) session.getAttribute("user");
		ProfileDAO profileDAO = new ProfileDAO();
		Profile profile = profileDAO.getProfiles(user.getId());
	
		//年齢計算
		Date birth = profile.getBirth(); // java.sql.Date
		if(birth != null) {
			long age = ChronoUnit.YEARS.between(birth.toLocalDate(), LocalDate.now());//戻り値がlong
			profile.setAge((int)age);
		}
		
		request_.setAttribute("profile", profile);
		request_.getRequestDispatcher("jsp/profile.jsp").forward(request_, response_);
	}
	
	protected void doPost(HttpServletRequest request_, HttpServletResponse response_)
			throws ServletException, IOException {
		//文字化け対策
		request_.setCharacterEncoding("UTF-8");
		response_.setContentType("text/html; charset=UTF-8");
		
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
		ProfileDAO profileDAO = new ProfileDAO();
		Profile profile = profileDAO.getProfiles(user.getId());
		
		profile.setNickname(request_.getParameter("nickname"));
		String birthStr = request_.getParameter("birth");
		profile.setBirth(Date.valueOf(birthStr));
		profile.setJobCategory(request_.getParameter("job-category"));
		profile.setHobby(request_.getParameter("hobby"));
		profile.setMemo(request_.getParameter("memo"));
		
		// アイコン画像アップロード処理
		Part part = request_.getPart("icon-image");//ファイルそのものを取得
		if(part != null && part.getSize() > 0) {//ファイルが選択されている場合
			 // 元ファイル名からパス成分を除去（セキュリティ対策）
		    String submittedFileName = part.getSubmittedFileName();// ファイル名取得
		    String originalFileName = Paths.get(submittedFileName).getFileName().toString();//画像ファイルへのパスのうち、最後の部分のみ切り取ってる。
		   
		    // 拡張子チェック（簡易）
		    String lower = originalFileName.toLowerCase();
		    if (!lower.endsWith(".png") && !lower.endsWith(".jpg") && !lower.endsWith(".jpeg")) {
		        throw new ServletException("画像ファイルのみアップロード可能です/.jpeg/.jpg/.png");
		    }
		    
		    // 拡張子だけ取り出す
		    String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
		    
		    // サーバー保存用の安全なファイル名を作る（日本語ゼロ）
		    String safeFileName = user.getId() + "_" + System.currentTimeMillis() + ext;
		   
		    // 保存先パス作成
		    String iconImagePath = "/images/" + safeFileName;
		    String realPath = getServletContext().getRealPath(iconImagePath);// サーバー上の絶対パスに変換
		   
		    // ディレクトリがなければ作成
		    File dir = new File(realPath).getParentFile();
		    if (!dir.exists()) {//ディレクトリが存在しない場合(imagesフォルダがない場合)
		        dir.mkdirs();//imagesフォルダを作成
		    }
		    
		    // 保存
		    part.write(realPath);
			profile.setIconImagePath(iconImagePath);
		}
		
		LocalDate currentDate = LocalDate.now();
		profile.setUpdatedAt(Date.valueOf(currentDate));
		
		int affectRows = profileDAO.setProfiles(profile,user.getId());
		
		if(affectRows > 0) {
			HttpSession newSession = request_.getSession(true);
			newSession.setAttribute("message", "更新完了しました。");
			newSession.setAttribute("messageType", "complete-message");
			request_.setAttribute("profile", profile);
		}else {
			request_.setAttribute("error", "更新に失敗しました。再度お試しください");
		}
		response_.sendRedirect("profile");
		
	}

}
