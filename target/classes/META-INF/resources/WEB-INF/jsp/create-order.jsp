<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>주문 추가</title>
</head>
<body>
    <h1>📝 새 주문 만들기</h1>
    <form action="/orders" method="post">
        <label for="item">주문 항목:</label>
        <input type="text" name="item" id="item" required />
        <button type="submit">등록</button>
    </form>
    <br>
    <a href="/orders">← 주문 목록으로</a>
</body>
</html>
