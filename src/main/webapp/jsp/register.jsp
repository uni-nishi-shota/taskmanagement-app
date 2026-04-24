<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>新規登録 - Task Practice Web App</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/general.style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/button.style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.style.css">
</head>

<body>
	<div class="page-wrapper">
		<!-- ヘッダー -->
		<jsp:include page="header.jsp" />

		<!-- メイン -->
		<main class="centered">
			<div class="container">
				<h2>新規登録画面</h2>

				<!-- フラッシュメッセージ表示 -->
				<c:if test="${not empty message}">
					<div class="${messageType}">
						<p>${message}</p>
					</div>
				</c:if>
				<c:remove var="message" scope="session" />
				<c:remove var="messageType" scope="session" />

				<c:if test="${not empty errors}">
					<ul>
						<c:forEach var="err" items="${errors}">
							<li style="color: red;">${err}</li>
						</c:forEach>
					</ul>
				</c:if>

				<!-- 登録フォーム -->
				<form method="post"
					action="${pageContext.request.contextPath}/register">
					<div class="form-group">
						<label for="email">メールアドレス</label> <input type="email" id="email"
							name="email"
							value="<%=request.getParameter("email") != null ? request.getParameter("email") : ""%>"
							placeholder="メールアドレスを入力" required>
					</div>

					<div class="form-group">
						<label for="password">パスワード</label> <input type="password"
							id="password" name="password" placeholder="パスワードを入力" required>
					</div>

					<div class="btn-group center">
						<button type="submit" class="btn btn-primary">登録</button>
					</div>



					<div class="link-button">
						既にアカウントをお持ちですか？ <a
							href="${pageContext.request.contextPath}/jsp/login.jsp">ログイン</a>
					</div>
				</form>
			</div>
		</main>

		<!-- フッター -->
		<jsp:include page="footer.jsp" />
	</div>
</body>

</html>