<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
	<meta charset="UTF-8">
	<title>トップページ- Task Practice Web App</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.style.css">
</head>

<body>
    <!-- ヘッダー -->
	<jsp:include page="header.jsp"/>
	
	 <!-- メイン -->
	<div class="page-wrapper">
    <main class="centered">
        <div class="top-container">
            <h2>タスク管理WEBアプリ</h2>
            <p class="subtitle">シンプルに、確実に、タスクを管理。</p>

            <div class="btn-group.column">
                <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="btn btn-primary">ログイン</a>
                <a href="${pageContext.request.contextPath}/jsp/register.jsp" class="btn btn-secondary">新規登録</a>
            </div>
        </div>
    </main>
    
     <!-- フッター -->
   <jsp:include page="footer.jsp"/>
	
	</div>
</body>

</html>