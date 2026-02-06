<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="taskpractice.pack.User" %>
<!DOCTYPE html>
<html lang="ja">


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ダッシュボード - Task Practice Web App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashbord.style.css">
</head>


<body>
    <div class="header">
        <h1>ダッシュボード</h1>
        <div class="user-info">
            <span class="welcome-text">
                ようこそ、<strong><%= ((User)session.getAttribute("user")).getUsername() %></strong>さん
            </span>
            <a href="${pageContext.request.contextPath}/jsp/menu.jsp" class="menu-btn">メニューへ</a>
        </div>
    </div>
    
    <div class="content">
        <h2 class="section-title">ユーザー管理</h2>
        
        <%
            @SuppressWarnings("unchecked")
            List<User> users = (List<User>) request.getAttribute("users");
            User currentUser = (User) session.getAttribute("user");
        %>
        
        <div class="user-count">
            登録ユーザー数: <%= users != null ? users.size() : 0 %>名
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ユーザーID</th>
                    <th>ユーザーアドレス</th>
                    <th>ステータス</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (users != null) {
                        for (User user : users) {
                            boolean isCurrentUser = currentUser != null && 
                                                  currentUser.getId() == user.getId();
                %>
                    <tr <%= isCurrentUser ? "class=\"current-user\"" : "" %>>
                        <td><%= user.getId() %></td>
                        <td><%= user.getUsername() %></td>
                        <td>
                            <%= isCurrentUser ? "ログイン中" : "オフライン" %>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="3" style="text-align: center; color: #666;">
                            ユーザーデータがありません
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>