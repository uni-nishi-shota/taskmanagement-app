<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Cache-Control"
		content="no-cache, no-store, must-revalidate">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<title>メニュー - Task Practice Web App</title>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/general.style.css">
</head>

<body>
	<div class="page-wrapper">
		<!-- ヘッダー -->
		<jsp:include page="header.jsp"/>

		<!-- メイン -->
		<main class="centered">
			<div class="container menu-container">

				<h2>メニュー</h2>
				<h3>各種リンク</h3>
				<div class="btn-group center row">
					<a href="${pageContext.request.contextPath}/profile" class="btn btn-primary">プロフィール</a>
					<a href="${pageContext.request.contextPath}/dashbord" class="btn btn-primary">ダッシュボード</a> 
					<a href="${pageContext.request.contextPath}/tasklist" class="btn btn-primary">タスクリスト</a>

					<form method="post" action="${pageContext.request.contextPath}/logout" class="btn-wrapper">
						<button type="submit" class="btn btn-danger ">ログアウト</button>
					</form>
				</div>

			</div>
		</main>

		<!-- フッター -->
		<jsp:include page="footer.jsp"/>
	</div>
</body>

</html>