package taskpractice.pack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/image")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request_, HttpServletResponse response_)
			throws ServletException, IOException {

		String path = request_.getParameter("path");
		if (path == null || path.isEmpty()) {
			response_.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String realPath = getServletContext().getRealPath(path);
		File file = new File(realPath);

		if (!file.exists()) {
			response_.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// キャッシュ無効化（これが効く）
		response_.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response_.setHeader("Pragma", "no-cache");
		response_.setDateHeader("Expires", 0);

		// Content-Type 設定（ブラウザに画像だと伝える）
		String mime = getServletContext().getMimeType(file.getName());//拡張子からMIMEタイプを取得(ブラウザに何のデータか伝えてる）
		response_.setContentType(mime != null ? mime : "application/octet-stream");//画像かどうか判断し、違ったらバイナリデータとして扱う

		// inでファイル読み取り、outでファイルの内容をレスポンスに書き出す
		try (InputStream in = new FileInputStream(file); 
				OutputStream out = response_.getOutputStream()) {

			byte[] buffer = new byte[8192];//8KBのバッファ
			int len;
			while ((len = in.read(buffer)) != -1) {//8192バイトずつ分割して読み書きしてる
				out.write(buffer, 0, len);
			}
		}
	}

}
