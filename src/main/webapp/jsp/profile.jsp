<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<title>プロフィール- Task Practice Web App</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/general.style.css">
</head>

<body>
<!-- ヘッダー -->
	<jsp:include page="header.jsp" />
	
	<!-- メイン -->
	<div class="page-wrapper">
	<main class="centered">
		<div class="container">
		
		<h2>プロフィール画面</h2>
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
		
		
		<%
			String nickname = (String) request.getAttribute("nickname");
		
		    
		%>
		
		

        <!-- プロフィールフォーム -->
        <form method="post" action="${pageContext.request.contextPath}/profile">
			<div class="form-group">
				<label for="nickname">ニックネーム（50文字以内）</label> 
				<input type="text" id="nickname" name="nickname" placeholder= "ニックネームを入力"  maxlength="50" value="${nickname}" >
			</div>
			
			<div class="form-group">
				<label for="birth">誕生日</label> 
				<input type="date" id="birth" name="birth" placeholder="誕生日を入力">
			</div>
			
			<div>
				<p>年齢:${age}</p> 
			</div>
				
			<div class="form-group">
				<label for="job-category">職種</label> 
				<input type="search" id="job-category" name="job-category" placeholder="職種を選択" list="job-categories">
				<datalist id="job-categories">
                    <option value="学生"></option>
                    <option value="会社員"></option>
                    <option value="公務員"></option>
                    <option value="自営業"></option>
                    <option value="フリーランス"></option>
                    <option value="無職"></option>
                    <option value="主婦・主夫"></option>
                    <option value="アルバイト・パート"></option>
                    <option value="その他"></option>
                </datalist>
			</div>
			
			<div class="form-group">
				<label for="hobby">趣味・好きなこと</label> 
				<input type="text" id="hobby" name="hobby" placeholder="趣味・好きなことを入力">
			</div>
			
			<div class="form-group">
				<label for="memo">memo</label> 
				<input type="text" id="memo" name="memo" placeholder="メモ...">
			</div>
			
			<div class="form-group">
                <label for="icon-mage">プロフィール画像（PNG、JPEG）</label>
  				<input type="file" id="icon-mage" name="icon-mage" accept="image/png, image/jpeg">
			</div>
			
			<div>
				<p> 更新日時:${profile.updatedAt}</p> 
			</div>
			
			<div class="btn-group center">
                <button type="submit" class="btn btn-primary">更新</button>	
                <a href="${pageContext.request.contextPath}/jsp/menu.jsp" class="btn btn-primary">メニューへ</a>
            </div>
		</form>
		
		</div>
		</main>
		
        
       
		
		<!-- フッター -->
		<jsp:include page="footer.jsp" />
	</div>
</body>

</html>