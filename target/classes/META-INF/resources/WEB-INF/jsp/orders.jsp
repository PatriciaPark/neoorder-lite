<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>주문 목록</title>
</head>

<body>
    <h1>📦 주문 목록</h1>
    <a href="/orders/new">
        <button>➕ 새 주문</button>
    </a>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Item</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.id}</td>
                <td>${order.item}</td>
                <td>${order.status}</td>
                <td>
                    <!-- 상태 변경 버튼 -->
                    <form action="/orders/${order.id}/status" method="post">
                        <c:choose>
                            <c:when test="${order.status eq 'COMPLETED'}">
                                <button type="submit" disabled>상태 완료</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit">상태 변경</button>
                            </c:otherwise>
                        </c:choose>
                    </form>                    

                    <!-- 삭제 버튼 -->
                    <form action="/orders/${order.id}/delete" method="post">
                        <button type="submit">삭제</button>
                    </form>
                </td>

            </tr>
        </c:forEach>
    </table>
</body>

</html>