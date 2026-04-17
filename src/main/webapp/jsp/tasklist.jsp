<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>タスク一覧 - Task Practice Web App</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/general.style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/button.style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/task.style.css">

<script src="${pageContext.request.contextPath}/js/popup.js"></script>
</head>

<body>
	<div class="page-wrapper">
		<!-- ヘッダー -->
		<jsp:include page="header.jsp" />

		<!-- メイン -->

		<h2>タスク一覧</h2>

		<!-- フラッシュメッセージ表示 -->
		<c:if test="${not empty message}">
			<div class="${messageType}">
				<p>${message}</p>
			</div>
		</c:if>
		<c:remove var="message" scope="session" />
		<c:remove var="messageType" scope="session" />

		<p>タスク数:${taskList.size()}</p>
		<div class="btn-group">
			<a href="${pageContext.request.contextPath}/taskform"
				class="btn btn-primary">作成</a> <a href="tasklist?mode=active"
				class="btn btn-primary">通常タスク</a> <a href="tasklist?mode=trash"
				class="btn btn-primary">最近削除した項目</a> <a
				href="${pageContext.request.contextPath}/menu"
				class="btn btn-secondary">メニューへ</a>

		</div>

		<!-- 各タスクをすべて表示 -->
		<c:choose>
			<c:when test="${empty taskList}">
				<p>タスクがありません</p>
			</c:when>
			<c:otherwise>
				<c:forEach var="task" items="${taskList}">
					<div class="task-item">
						<p>タスク名: ${task.taskName}</p>
						<p>開始: ${task.beginDate}</p>
						<p>締切: ${task.endDate}</p>
						<c:choose>
							<c:when test="${task.importance == 3}">
								<p>重要度: 高</p>
							</c:when>
							<c:when test="${task.importance == 2}">
								<p>重要度: 中</p>
							</c:when>
							<c:otherwise>
								<p>重要度: 低</p>
							</c:otherwise>
						</c:choose>


						<a href="taskform?id=${task.id}" class="btn-edit">編集</a>
						<c:choose>
							<c:when test="${mode eq 'trash'}">
								<button onclick="restoreConfirmFunction(${task.id})"
									class="btn-restore">復元</button>

								<button onclick="deleteConfirmFunction(${task.id})"
									class="btn-harddelete">完全削除</button>
							</c:when>
							<c:otherwise>
								<button onclick="confirmFunction(${task.id})" class="btn-delete">削除</button>
							</c:otherwise>
						</c:choose>

					</div>
					<hr>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<!-- フッター -->
		<jsp:include page="footer.jsp" />
	</div>
</body>

</html>