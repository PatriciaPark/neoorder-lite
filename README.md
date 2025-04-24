# 🧱 NeoOrder Lite - Order Management System | 주문 관리 시스템

**NeoOrder Lite** is a lightweight Java web application built with Spring Boot and JSP.  
It simulates a basic order management system with features like creation, listing, status update, and deletion.  
Designed for local testing, portfolio demonstration, and classic Java MVC experience.

**NeoOrder Lite**는 Spring Boot와 JSP 기반의 경량 웹 애플리케이션입니다. 
주문 등록, 목록 조회, 상태 변경, 삭제 기능을 포함하며, Java MVC 아키텍처와 REST API를 함께 시연할 수 있도록 설계되었습니다.

---

## 🌐 Live Demo

📎 [https://neoorder-lite.onrender.com/orders]

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.1.0
- Spring MVC (JSP-based)
- Spring Data JPA
- H2 In-Memory Database
- Maven
- JSTL (for JSP rendering)

---

## 🚀 Features | 주요 기능

- 📦 주문 목록 조회 | View all orders (`/orders`)
- ➕ 주문 등록 | Add new order (`/orders/new`)
- 🔁 상태 변경 (RECEIVED → SHIPPING → COMPLETED) | Status update
- ❌ 주문 삭제 | Delete order
- 🌐 REST API 지원 | REST endpoints under `/api/orders`

---

## 📄 Sample Endpoints | 경로 요약

```http
GET     /orders                # 주문 목록 페이지 | Order list (JSP)
GET     /orders/new            # 주문 등록 폼 | New order form (JSP)
POST    /orders                # 신규 주문 등록 | Submit new order
POST    /orders/{id}/status    # 상태 변경 | Update order status
POST    /orders/{id}/delete    # 삭제 | Delete order

GET     /api/orders            # 주문 목록 (JSON) | API: list all orders
GET     /api/orders/status/RECEIVED   # 상태별 조회 | Filter by status
```

---

## 📁 Project Structure | 프로젝트 구조

'''bash
neoorder-lite/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.neoorder/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       └── repository/
│   │   ├── resources/
│   │   └── webapp/
│   │       └── WEB-INF/jsp/
├── pom.xml
├── README.md
'''

---

## 💡 Motivation | 제작 동기
이 프로젝트는 아래 내용을 포트폴리오로 보여주기 위해 제작되었습니다.
This project was created to demonstrate the following skills:
- Java 기반 전통적인 웹 아키텍처 구현 | Classic Java MVC architecture
- REST와 JSP 뷰 분리 설계 | Separation of REST and View
- 열거형(enum)을 활용한 상태 관리 | Enum-based state logic
- HTML 폼에서 메서드 오버라이드 처리 | Method override (PUT/DELETE) in forms
- 자가설계 및 구현한 미니 백엔드 프로젝트 | Self-designed full backend project

---

## 🧑‍💻 Author
Patricia Park (Young-Jee Park)
- 📫 pyjee8@gmail.com
- 🔗 https://patriciapark.github.io/portfolio

