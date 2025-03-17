-- Create Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    address TEXT,
    role VARCHAR(20) DEFAULT 'USER',
    enabled BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert Test User
INSERT INTO users (first_name, last_name, email, password, phone_number, address, role, enabled)
VALUES ('Test', 'User', 'test@example.com', 'password123', '1234567890', '123 Test St', 'USER', true); 