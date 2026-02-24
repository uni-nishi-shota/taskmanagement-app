<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<!-- ヘッダー -->
<header class="header">
	<h1>Task Manager</h1>
	<c:if test="${not empty user}">
	 <span>
		 
        <strong>${empty profile.nickname ? user.email : profile.nickname}</strong>さん
		<img src="${pageContext.request.contextPath}/image?path=${profile.iconImagePath}" class="profile-icon">
                                                                            
     </span>
	</c:if>
</header>
<!-- 現状は、ヘッダーにアイコンとニックネームを出したいけど、 -->
<!-- プロフィールのクラスにうまくアクセスできていない -->
<!-- プロフィール画面でしかprofileのデータが参照できていない。なんで？（究明） -->
<!-- 解決策：セッションスコープにプロフィール情報を保存しておく。 -->
<!-- そして、header.jspでセッションスコープからプロフィール情報を取得する。 -->
<!--↑2行はコパイロット君の提案、かなりあり。 -->
<!-- で、セッションが存在しないと、ニックネーム、アイコンを表示しない -->
<!-- という風に正業すれば、ログインした時に表示するとか可能。 -->
