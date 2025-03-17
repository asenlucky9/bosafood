-- Create a temporary table for backup if users table exists
DO $$ 
BEGIN
    IF EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'users') THEN
        CREATE TEMP TABLE users_backup AS SELECT * FROM users;
    END IF;
END $$;

-- Drop the existing users table
DROP TABLE IF EXISTS users CASCADE;

-- Recreate the users table with all required columns
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    address TEXT,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Restore data with default values for new columns if backup exists
DO $$
BEGIN
    IF EXISTS (SELECT FROM pg_tables WHERE schemaname = 'pg_temp' AND tablename = 'users_backup') THEN
        INSERT INTO users (
            id,
            email,
            password_hash,
            first_name,
            last_name,
            phone_number,
            address,
            role,
            created_at,
            updated_at
        )
        SELECT
            id,
            email,
            password_hash,
            COALESCE(first_name, split_part(email, '@', 1)),
            COALESCE(last_name, 'User'),
            phone_number,
            address,
            COALESCE(role, 'USER'),
            COALESCE(created_at, CURRENT_TIMESTAMP),
            COALESCE(updated_at, CURRENT_TIMESTAMP)
        FROM users_backup;
        
        -- Drop the temporary backup table
        DROP TABLE users_backup;
    END IF;
END $$;

-- Create index on email for faster lookups
CREATE INDEX idx_users_email ON users(email);

-- Insert a default admin user if it doesn't exist
INSERT INTO users (
    email,
    password_hash,
    first_name,
    last_name,
    role
)
VALUES (
    'admin@bosafood.com',
    '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG',
    'Admin',
    'User',
    'ADMIN'
)
ON CONFLICT (email) DO NOTHING; 