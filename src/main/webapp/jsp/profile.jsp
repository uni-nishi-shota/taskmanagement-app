<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<title>プロフィール- Task Practice Web App</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/general.style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/button.style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/profile.style.css">


</head>

<body>
	<!-- ヘッダー -->
	<jsp:include page="header.jsp" />

	<!-- メイン -->
	<div class="page-wrapper">
		<main class="centered">
			<div class="container">

				<h2>プロフィール画面</h2>

				<!-- フラッシュメッセージ表示 -->
				<c:if test="${not empty message}">
					<div class="${messageType}">
						<p>${message}</p>
					</div>
				</c:if>
				<c:remove var="message" scope="session" />
				<c:remove var="messageType" scope="session" />
				<!-- 上の更新成功したとき、うまく表示できてない -->


				<!-- プロフィールフォーム -->
				<form method="post"
					action="${pageContext.request.contextPath}/profile"
					enctype="multipart/form-data">
					<div class="form-group">
						<label for="nickname">ニックネーム（50文字以内）</label> <input type="text"
							id="nickname" name="nickname" placeholder="ニックネームを入力"
							maxlength="50" value="${profile.nickname}">
					</div>

					<div class="form-group">
						<label for="birth">誕生日</label> <input type="date" id="birth"
							name="birth" placeholder="誕生日を入力" value="${profile.birth}">
					</div>

					<div>
						<p>年齢:${profile.age}</p>
					</div>

					<div class="form-group">
						<label for="job-category">職種</label> <select id="job-category"
							name="job-category">
							<c:forEach var="job" items="${jobCategoryList}">
								<option value="${job.jobName}"
									<c:if test="${job.jobName == profile.jobCategory}">selected</c:if>>
									${job.jobName}
								</option>
							</c:forEach>
						</select>
					</div>

					<div class="form-group">
						<label for="hobby">趣味・好きなこと</label>
						<textarea id="hobby" name="hobby" placeholder="趣味・好きなことを入力"
							rows="7" cols="45">${profile.hobby}</textarea>

					</div>

					<div class="form-group">
						<label for="memo">memo</label>
						<textarea id="memo" name="memo" placeholder="メモ..." rows="7"
							cols="45">${profile.memo}</textarea>

					</div>

					<div class="form-group">
						<label for="icon-image">プロフィール画像（PNG、JPEG）</label> <input
							type="file" id="icon-image" name="icon-image"
							accept="image/png, image/jpeg">
					</div>

					<div>
						<img
							src="${pageContext.request.contextPath}/image?path=${profile.iconImagePath}"
							class="profile-icon">
					</div>

					<div>
						<p>更新日時:${profile.updatedAt}</p>
					</div>

					<div class="btn-group center">
						<button type="submit" class="btn btn-primary">更新</button>
						<a href="${pageContext.request.contextPath}/jsp/menu.jsp"
							class="btn btn-primary">メニューへ</a>
					</div>
				</form>

			</div>
		</main>

		<!-- フッター -->
		<jsp:include page="footer.jsp" />
	</div>
</body>

</html>