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
