# Currency Converter API Integration

## Project Overview
This project is a Spring Boot-based REST API for currency conversion, utilizing the Exchange Rates API. It provides endpoints for fetching exchange rates and converting amounts between different currencies.

## Features
- Fetches real-time exchange rates from Exchange Rates API.
- Converts currency based on user input.
- Error handling for API failures and invalid requests.
- Unit tests using JUnit and Mockito.

## Technologies Used
- Java
- Spring Boot
- RestTemplate
- Exchange Rates API
- JUnit & Mockito (for testing)

## API Endpoints
### 1. Get Exchange Rates
**Endpoint:** `GET /api/rates`

**Query Parameters:**
- `base` (optional, default: `USD`) - The base currency for conversion.

**Example Request:**
```sh
GET http://localhost:8080/api/rates?base=USD
```

**Example Response:**
```json
{
  "base": "USD",
  "rates": {
    "EUR": 0.85,
    "INR": 74.5,
    "GBP": 0.75
  }
}
```

### 2. Convert Currency
**Endpoint:** `POST /api/rates/convert`

**Request Body:**
```json
{
  "from": "USD",
  "to": "INR",
  "amount": 100.0
}
```

**Example Request:**
```sh
POST http://localhost:8080/api/rates/convert
Content-Type: application/json
```

**Example Response:**
```json
{
  "from": "USD",
  "to": "INR",
  "amount": 100.0,
  "convertedAmount": 7450.0
}
```

## Setup and Installation
### Prerequisites
- Java 11+
- Maven
- Exchange Rates API key

### Steps to Run the Project
1. Clone the repository:
   ```sh
   git clone https://github.com/ESWARKORADA/CurrencyConverterApi.git
   ```
2. Navigate to the project directory:
   ```sh
   cd CurrencyConverterApi
   ```
3. Build the project:
   ```sh
   mvn clean install
   ```
4. Run the application:
   ```sh
   mvn spring-boot:run
   ```
5. The API will be available at `http://localhost:8080`

## Running Tests
To run unit tests, use:
```sh
mvn test
```

## Error Handling
- **401 Unauthorized:** Invalid API key.
- **500 Internal Server Error:** API connection failure or invalid currency codes.
- **400 Bad Request:** Invalid input data.

