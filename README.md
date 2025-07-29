# Flight API Demo (Spring Boot)

🚀 A simple yet robust **Spring Boot REST API** for managing flight data.  
Built with **Java 17**, **Spring Boot 3**, and **Maven**, this demo showcases clean REST design, DTOs, service layer, exception handling, and AOP-based logging.

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/Java-17+-brightgreen.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-red.svg)](https://spring.io/projects/spring-boot)
[![Build Tool](https://img.shields.io/badge/Build-Maven-blue.svg)](https://maven.apache.org/)

---

## ✅ Features

- ✅ **RESTful CRUD API** for flight management
- 📦 Uses **DTO pattern** (`FlightDto`) for clean data transfer
- 🔁 **Service Layer** with `IFlightService` and `FlightServiceImpl`
- 🛢️ **Repository Layer** using Spring Data JPA
- 🧯 **Custom Exception Handling** with meaningful error messages
- 📝 **AOP-Based Logging**:
  - `GlobalLogger` logs all incoming HTTP requests
  - `DBSyncAspect` logs simulated data sync operations
- 💾 **In-Memory H2 Database** – no setup required
- 🧪 **Standardized Success Responses** using `FlightSuccessResponse`

---

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/AgeOfUltra/flight-api-demo.git
cd flight-api-demo
```

### 2. Build with Maven
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

> 🌐 The server starts on `http://localhost:8080` by default.

---

## 🛠️ API Endpoints

| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET`    | `/flights/getAllFlights`     | Get all flights |
| `POST`   | `/flights/addFlight`         | Add a new flight |
| `GET`    | `/flight/getFlightById/{flightID}` | Get a flight by ID |
| `PUT`    | `/flight/updateFlight`       | Update an existing flight |
| `DELETE` | `/flight/deleteFlight/{flightID}` | Delete a flight by ID |

> 🔐 All responses are structured and consistent.

---

## 📥 Example: Add a Flight (POST)

**Request**:
```http
POST /flights/addFlight
Content-Type: application/json
```
```json
{
  "flightNumber": "UL-101",
  "origin": "Colombo",
  "destination": "Maldives",
  "departureTime": "2025-04-05T10:00:00Z",
  "arrivalTime": "2025-04-05T12:00:00Z",
  "airline": "UltraAir"
}
```

**Response** (`201 Created`):
```json
{
  "timestamp": "2025-04-01T10:00:00",
  "message": "Flight ID 1 is saved successfully"
}
```

---

## 📤 Example: Get All Flights (GET)

**Request**:
```http
GET /flights/getAllFlights
```

**Response** (`200 OK`):
```json
[
  {
    "id": 1,
    "flightNumber": "UL-101",
    "origin": "Colombo",
    "destination": "Maldives",
    "departureTime": "2025-04-05T10:00:00.000Z",
    "arrivalTime": "2025-04-05T12:00:00.000Z",
    "airline": "UltraAir"
  }
]
```

---

## 🔍 Example: Get Flight by ID (GET)

**Request**:
```http
GET /flight/getFlightById/1
```

**Response**:
```json
{
  "id": 1,
  "flightNumber": "UL-101",
  "origin": "Colombo",
  "destination": "Maldives",
  "departureTime": "2025-04-05T10:00:00.000Z",
  "arrivalTime": "2025-04-05T12:00:00.000Z",
  "airline": "UltraAir"
}
```

---

## 🔄 Example: Update a Flight (PUT)

**Request**:
```http
PUT /flight/updateFlight
Content-Type: application/json
```
```json
{
  "id": 1,
  "flightNumber": "UL-102",
  "origin": "Colombo",
  "destination": "Bangkok",
  "departureTime": "2025-04-06T09:00:00Z",
  "arrivalTime": "2025-04-06T11:30:00Z",
  "airline": "UltraAir"
}
```

**Response**:
```json
{
  "timestamp": "2025-04-01T10:05:00",
  "message": "Flight updated successfully for flight ID 1"
}
```

---

## 🗑️ Example: Delete a Flight (DELETE)

**Request**:
```http
DELETE /flight/deleteFlight/1
```

**Response**:
```json
{
  "timestamp": "2025-04-01T10:10:00",
  "message": "Flight deleted successfully for flight ID 1"
}
```

---

## 🗃️ Database (H2 In-Memory)

Access the H2 console at:  
👉 [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Use:
- **JDBC URL**: `jdbc:h2:mem:flightdb`
- **Username**: `sa`
- **Password**: (leave empty)

> 💡 Data is lost on restart. Update `application.properties` to use PostgreSQL/MySQL for persistence.

---

## 📁 Project Structure

```
src/main/java/com/flight/api/
├── controller/
│   └── FlightController.java           # REST endpoints
├── entity/
│   └── Flight.java                     # JPA Entity
├── exception/
│   ├── FlightNotFoundException.java    # Thrown when flight not found
│   └── GlobalHandlerException.java     # @ControllerAdvice for global error handling
├── logger/
│   ├── DBSyncAspect.java               # Logs @Scheduled sync jobs
│   ├── GlobalLogger.java               # Logs all HTTP requests (AOP)
│   └── SyncFlightData.java             # Simulates periodic data sync
├── model/
│   └── FlightDto.java                  # Data Transfer Object
├── repository/
│   └── IFlightRepository.java          # Spring Data JPA repository
├── service/
│   ├── IFlightService.java             # Service interface
│   └── FlightServiceImpl.java          # Business logic
├── utils/
│   ├── FlightErrorResponseUtil.java    # Utility for error responses
│   ├── FlightSuccessResponse.java      # Standard success response
│   └── FlightApiDemoApplication.java   # Main Spring Boot class
└── config/
    └── ProjectConfig.java              # Configuration (e.g., Jackson)
```

---

## 📝 Logging (AOP)

- ✅ `GlobalLogger`: Logs every incoming HTTP request.
- ✅ `DBSyncAspect`: Logs when `SyncFlightData` runs (every 30 seconds).
- Example:
  ```
  [GLOBAL-LOGGER] Incoming request: POST /flights/addFlight
  [DB-SYNC] Syncing flight data...
  ```

---

## 🛑 Notes

- `FlightDto` is used for all request/response payloads.
- Success responses use `FlightSuccessResponse` (timestamp + message).
- No request validation yet — can be added using `@Valid` and `@NotBlank`.
- Uses **Lombok** — ensure your IDE supports it.

---

## 🤝 Contributing

1. Fork the repo
2. Create a feature branch: `git checkout -b feature/name`
3. Commit changes: `git commit -m 'Add feature'`
4. Push: `git push origin feature/name`
5. Open a Pull Request

---

## 📄 License

MIT © [AgeOfUltra](https://github.com/AgeOfUltra)  
See [LICENSE](LICENSE) for details.

---

## 🙏 Acknowledgements

- Built with **Spring Boot 3**, **Java 17**, **Maven**
- Great for learning REST APIs, AOP, DTOs, and service architecture
- Inspired by real-world flight systems

---

🚀 **Happy Coding!**  
— *From the AgeOfUltra*  
