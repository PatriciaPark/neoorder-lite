# 🧱 NeoOrder Lite

**NeoOrder Lite** is a lightweight Spring Boot backend application that simulates an order processing API.  
This project is designed for deployment and live testing via [Render](https://render.com), and is suitable for portfolio and interview demonstrations.

<br>

## 🌐 Live Demo

📎 [https://neoorder-lite.onrender.com/api/orders](https://neoorder-lite.onrender.com/api/orders)

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.1.0
- Spring Web
- Spring Data JPA (in-memory only)
- H2 Database
- Maven
- Render (for deployment)

---

## 🚀 API Endpoints

### 🔹 `GET /api/orders`

Returns a list of sample order items.

**Response Example:**
```json
[
  { "item": "Brick", "id": "1" },
  { "item": "Laser", "id": "2" }
]

```
---

## 📦 Project Structure
```bash
neoorder-lite/
├── src/
│   ├── main/
│   │   ├── java/...
│   │   └── resources/...
├── target/
├── pom.xml
├── Dockerfile
├── README.md

```

## 💡 Motivation
This project was created as part of a full backend deployment demo for portfolio use, highlighting:
- API development with Spring Boot
- Build automation using Maven
- Live deployment with Docker + Render
- Clean code and endpoint documentation

## 🧑‍💻 Author
Patricia Park (Young-Jee Park)
📫 pyjee8@gmail.com
🔗 patriciapark.github.io/portfolio
