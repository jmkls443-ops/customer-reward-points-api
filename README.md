# Customer Reward Points API

A Spring Boot application that calculates customer reward points based on purchase transactions.  
This project was built as part of a developer homework assignment.

---

## 📌 Features

- RESTful API endpoint for reward points summary
- Reward points calculation:
    - 2 points for every dollar spent over $100
    - 1 point for every dollar spent between $50 and $100
- Monthly and total reward summary per customer
- In-memory H2 database for demo purposes
- JPA/Hibernate integration
- Unit tests with JUnit 5 + Mockito

---

## 🛠 Tech Stack

- Java 8
- Spring Boot 2.7.18
- Spring Data JPA
- H2 Database
- JUnit 5 + Mockito for testing
- Jackson JSR310 for Java Time serialization

---

## 📂 Project Structure
customer-reward-points-api/
├── src/
│   ├── main/java/com/customerrewardpointsapi/
│   │    ├── CustomerRewardPointsApiApplication.java
│   │    ├── controller/RewardPointsController.java
│   │    ├── service/RewardPointsService.java
│   │    ├── repository/RewardPointsRepository.java
│   │    ├── dto/CustomerRewardPointsSummaryDTO.java
│   │    └── entity/RewardPointsTransaction.java
│   └── test/java/com/customerrewardpointsapi/
│        ├── controller/RewardPointsControllerTest.java
│        └── service/RewardPointsServiceTest.java
├── docs/
│   ├── request-sample.json
│   ├── response-sample.json
│   └── results.json
└── pom.xml

---

## 🚀 Endpoint Details

### `GET /rewards`

**Description:**  
Returns each customer’s per‑month reward points and total for a given three‑month period.

**Request Parameters:**

- `startDate` (ISO date, required)
- `endDate` (ISO date, required)

GET /rewards?startDate=2026-04-01&endDate=2026-06-30

**Example Response:**

```json
[
  {
    "customerId": "CUST001",
    "customerName": "Alice",
    "monthlyRewards": {
      "APRIL": 91.5,
      "MAY": 25.9
    },
    "totalRewards": 117.4
  }
]


🚀 Getting Started
1.Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/customer-reward-points-api.git
   cd customer-reward-points-api
   
2.Build and run:

mvn clean install
mvn spring-boot:run

3.Access API at:

http://localhost:8080/rewards?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD




