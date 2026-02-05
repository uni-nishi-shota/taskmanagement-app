<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ログイン - Task Practice Web App</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.style.css">

</head>

<body>
<div class="page-wrapper">
	<!-- ヘッダー -->
	 <!-- ヘッダー -->
	<jsp:include page="header.jsp"/>
	
	<!-- メイン -->
	<main class="centered">
		<div class="container">

			<!-- フラッシュメッセージ表示 -->
			<%
			String message = (String) session.getAttribute("message");//セッションからmessageというキーから、値を取り出す。
			String messageType = (String) session.getAttribute("messageType");
			if (message != null) {
				session.removeAttribute("message");// 一度表示したらセッションから削除
				session.removeAttribute("messageType");// 一度表示したらセッションから削除
			}
			%>
			<p class="<%=messageType != null ? messageType : ""%>">
				<%=message != null ? message : ""%>
			</p>
			<!-- -->


			<h2>ログイン画面</h2>

			<!-- エラーメッセージ表示 -->
			<%
			if (request.getAttribute("error") != null) {
			%>
			<div class="error">
				<%=request.getAttribute("error")%>
			</div>
			<%
			}
			%>

			<!-- ログインフォーム -->
			<form method="post" action="${pageContext.request.contextPath}/login">
				<!--ー＞@WebServlet("/login") -->
				<div class="form-group">
					<label for="email">メールアドレス</label> 
					<input type="email" id="email" name="email"
						value="<%=request.getParameter("email") != null ? request.getParameter("email") : ""%>"
						placeholder="メールアドレスを入力" required>
				</div>


				<div class="form-group">
					<label for="password">パスワード</label>
					 <input type="password" id="password" name="password" placeholder="パスワードを入力" required>
				</div>


				<div class="btn-group center">
					<button type="submit" class="btn btn-primary">ログイン</button>
				</div>
				
				<div class="link-button">
					アカウントをお持ちでないですか？ <a href="${pageContext.request.contextPath}/jsp/register.jsp">新規登録</a>
				</div>
			</form>



		</div>
	</main>

	<!-- フッター -->
   <jsp:include page="footer.jsp"/>
</div>
</body>
</html>