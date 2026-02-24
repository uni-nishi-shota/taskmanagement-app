<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログインコンプリート - Task Practice Web App</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.style.css">
</head>

<body>
	<div class="page-wrapper">
		 <!-- ヘッダー -->
	<jsp:include page="header.jsp"/>

		<!-- メイン -->
		<main class="centered">
			<div class="container">

				<h2>ログイン完了しました</h2>
				<div class="btn-group center">
					<form method="get" action="menu">
						<button type="submit" class="btn btn-primary">メニューへ</button>
					</form>
				</div>
			</div>
		</main>

		<!-- フッター -->
   <jsp:include page="footer.jsp"/>
	</div>
</body>

</html>