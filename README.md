# Bosa Kitchen Backend

This is the backend application for Bosa Kitchen restaurant website. It provides REST APIs for managing menu items and orders.

## Prerequisites

- Java 17 or later
- Maven
- PostgreSQL
- pgAdmin 4

## Database Setup

1. Open pgAdmin 4
2. Create a new database named `bosaKitchenbackend`
3. The application will automatically create the necessary tables on startup

## Configuration

The application uses the following default configuration:
- Server port: 8081
- Database URL: jdbc:postgresql://localhost:5001/bosaKitchenbackend
- Database username: postgres
- Database password: mother4me

You can modify these settings in `src/main/resources/application.properties`

## Building the Application

```bash
mvn clean install
```

## Running the Application

```bash
mvn spring:boot run
```

## API Endpoints

### Menu Items

- GET `/api/menu` - Get all menu items
- GET `/api/menu/category/{category}` - Get menu items by category
- GET `/api/menu/available` - Get available menu items
- GET `/api/menu/{id}` - Get menu item by ID
- POST `/api/menu` - Create new menu item
- PUT `/api/menu/{id}` - Update menu item
- DELETE `/api/menu/{id}` - Delete menu item

### Orders

- GET `/api/orders` - Get all orders
- GET `/api/orders/{id}` - Get order by ID
- GET `/api/orders/customer/{email}` - Get orders by customer email
- GET `/api/orders/status/{status}` - Get orders by status
- POST `/api/orders` - Create new order
- PUT `/api/orders/{id}/status` - Update order status
- DELETE `/api/orders/{id}` - Delete order

## Project Structure

```
src/main/java/com/bosafood/
├── controller/    # REST controllers
├── model/        # Entity classes
├── repository/   # JPA repositories
├── service/      # Business logic
├── exception/    # Exception handling
└── RestaurantApplication.java
```

## Testing

Run the tests using:

```bash
mvn test
``` 