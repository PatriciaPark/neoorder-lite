# 🧱 NeoOrder Lite - Order Management System | 주문 관리 시스템

**NeoOrder Lite** is a lightweight Java web application built with Spring Boot.  
It simulates a basic order management system with features like creation, listing, status update, and deletion.  
Designed for API testing, frontend integration, portfolio demonstrations, and modern REST-based architecture.

**NeoOrder Lite**는 Spring Boot 기반의 간소화 주문 관리 시스템입니다.  
주문 등록, 조회, 상태 변경, 삭제 기능을 포함하며, REST API와 HTML 기반 프론트엔드로 구성된 프로젝트입니다.

---

## 🌐 Live Demo

📎 [https://neoorder-lite.onrender.com/]

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.1.0
- Spring Web (REST API)
- Spring Data JPA
- H2 In-Memory Database
- Maven
- HTML + JavaScript (for frontend)
- Chart.js (for statistics visualization)
- Swagger UI (for API documentation)
- Render (for cloud deployment)

---

## 🚀 Features | 주요 기능

- 📦 주문 목록 조회 | View all orders (`/orders.html`)
- ➕ 주문 등록 | Add new order (via HTML form)
- 🔁 상태 변경 (RECEIVED → SHIPPING → COMPLETED) | Status update
- ❌ 주문 삭제 | Delete order
- 📊 주문 통계 및 차트 | Order statistics and charts (`/statistics.html`)
- 🌐 REST API 제공 | REST endpoints under `/api/orders`
- 📚 Swagger UI 문서 | Public API documentation (`/swagger-ui/index.html`)
- 🌍 다국어 지원 (한국어/영어) | Multi-language support (Korean/English)

---

## 📄 Sample Endpoints | 경로 요약

```http
GET     /orders.html                # 주문 목록 보기 (HTML)
GET     /api/orders                 # 전체 주문 조회 (JSON)
GET     /api/orders/status/{status} # 상태별 조회
POST    /api/orders                 # 주문 등록
PUT     /api/orders/{id}/status     # 상태 변경
DELETE  /api/orders/{id}            # 주문 삭제
GET     /api/statistics             # 주문 통계 조회
GET     /swagger-ui/index.html      # API 문서
```

---

## 📁 Project Structure | 프로젝트 구조

```bash
neoorder-lite/
├── src/
│   └── main/
│       ├── java/com.example.neoorder/
│       │   ├── controller/
│       │   ├── model/
│       │   └── repository/
│       ├── resources/
│       │   └── static/
│       │       ├── index.html
│       │       ├── orders.html
│       │       ├── statistics.html
│       │       ├── js/
│       │       │   ├── i18n.js
│       │       │   └── auth.js
│       │       └── style.css
│       └── application.properties
├── pom.xml
├── README.md
```

---

## 💡 Motivation | 제작 동기
이 프로젝트는 아래 내용을 포트폴리오로 보여주기 위해 제작되었습니다.
This project was created to demonstrate the following skills:
- Java 기반 웹 백엔드 개발 경험 | Hands-on experience building backend services with Java and Spring Boot
- HTML + JavaScript를 활용한 프론트엔드 연동 | Frontend integration using vanilla HTML and JavaScript
- RESTful API 설계 및 응답 처리 | Building and consuming RESTful APIs effectively
- 상태값(enum)을 활용한 로직 제어 및 상태 전이(State Pattern) | Using Java enums to control order status transitions
- Spring Data JPA + H2 DB를 통한 간단한 데이터 관리 | Lightweight data persistence with JPA and in-memory H2
- Chart.js를 활용한 데이터 시각화 | Data visualization using Chart.js
- Swagger UI를 통한 API 문서화 | API documentation with Swagger UI
- 다국어 지원 구현 | Multi-language support implementation
- 클라우드 환경(Render)에 직접 배포해 본 경험 | Real-world deployment experience with Render
- 테스트 및 시연을 고려한 완성형 미니 백엔드 프로젝트 구현 | Complete backend project ready for demo and portfolio presentation

---

## 🧑‍💻 Author
Patricia Park (Young-Jee Park)
- 📫 pyjee8@gmail.com
- 🔗 https://patriciapark.github.io/portfolio