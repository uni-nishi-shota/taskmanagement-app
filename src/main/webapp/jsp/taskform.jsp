<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>タスク作成・編集 - Task Practice Web App</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/general.style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/button.style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/task.style.css">
</head>

<body>

	<div class="page-wrapper">
		<!-- ヘッダー -->
		<jsp:include page="header.jsp" />

		<!-- メイン -->
		<main class="centered">
			<div class="container">
				<h2>${task.id == null ? "タスク作成" : "タスク編集"}</h2>

				<!-- フラッシュメッセージ表示 -->
				<c:if test="${not empty message}">
					<div class="${messageType}">
						<p>${message}</p>
					</div>
				</c:if>
				<c:remove var="message" scope="session" />
				<c:remove var="messageType" scope="session" />

				<form action="tasksave" method="post">
					<input type="hidden" name="id" value="${task.id}">

					<!-- タスク名 -->
					<div>
						<label>タスク名</label> <input type="text" name="taskName"
							value="${task != null ? task.taskName : ''}" required>
					</div>

					<!-- 場所 -->
					<div>
						<label>場所</label> <input type="text" name="place"
							value="${task != null ? task.place : ''}">
					</div>

					<!-- 開始日時 -->
					<div>
						<label>開始日時</label>
						<fmt:formatDate value="${task.beginDate}"
							pattern="yyyy-MM-dd'T'HH:mm" var="formattedBeginDate" />
						<input type="datetime-local" name="beginDate"
							value="${formattedBeginDate}">
					</div>

					<!-- 締切日時 -->
					<div>
						<label>締切日時</label>
						<fmt:formatDate value="${task.endDate}"
							pattern="yyyy-MM-dd'T'HH:mm" var="formattedEndDate" />
						<input type="datetime-local" name="endDate"
							value="${formattedEndDate}">
					</div>

					<!-- 重要度 -->
					<div>
						<label>重要度</label> <select name="importance">
							<option value="1" ${task.importance == 1 ? "selected" : ""}>低</option>
							<option value="2" ${task.importance == 2 ? "selected" : ""}>中</option>
							<option value="3" ${task.importance == 3 ? "selected" : ""}>高</option>
						</select>
					</div>

					<!-- 内容 -->
					<div>
						<label>内容</label>
						<textarea name="content">${task.content}</textarea>
					</div>

					<!-- 作成日時（表示のみ） -->
					<c:if test="${task != null && task.createdAt != null}">
						<div>
							<label>作成日時</label>
							<fmt:formatDate value="${task.createdAt}"
								pattern="yyyy-MM-dd HH:mm" />
						</div>
					</c:if>

					<!-- 更新日時（表示のみ） -->
					<c:if test="${task != null && task.updatedAt != null}">
						<div>
							<label>更新日時</label>
							<fmt:formatDate value="${task.updatedAt}"
								pattern="yyyy-MM-dd HH:mm" />
						</div>
					</c:if>

					<button type="submit" class="btn btn-primary">保存</button>
					<a href="tasklist" class="btn btn-secondary">戻る</a>
				</form>
			</div>
		</main>
		<!-- フッター -->
		<jsp:include page="footer.jsp" />
	</div>
</body>

</html>