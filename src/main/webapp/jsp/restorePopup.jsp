<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>復元確認</title>
<script src="${pageContext.request.contextPath}/js/popup.js"></script>
</head>
<body>

	<h3>このタスクを復元しますか？</h3>

	<button onclick="restoreTask()">復元する</button>
	<!-- ここまでは来ているが、restoreTask()後がうまく作用しておらず、復元処理ができていない模様 -->
	
	<button onclick="window.close()">キャンセル</button>
	<!-- ここは問題なさそう -->
	

</body>
</html>