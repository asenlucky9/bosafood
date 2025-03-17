-- Create temporary tables for backup if tables exist
DO $$ 
BEGIN
    IF EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'orders') THEN
        CREATE TEMP TABLE orders_backup AS SELECT * FROM orders;
    END IF;
    
    IF EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'order_items') THEN
        CREATE TEMP TABLE order_items_backup AS SELECT * FROM order_items;
    END IF;
END $$;

-- Drop existing tables
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;

-- Create orders table with all required columns
CREATE TABLE orders (
    order_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    order_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    payment_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    delivery_method VARCHAR(50) NOT NULL,
    delivery_address TEXT,
    special_instructions TEXT,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(20),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create order_items table
CREATE TABLE order_items (
    order_item_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(order_id) ON DELETE CASCADE,
    item_id BIGINT NOT NULL REFERENCES menu_items(id),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    special_requests TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Restore orders data with default values for new columns if backup exists
DO $$
BEGIN
    IF EXISTS (SELECT FROM pg_tables WHERE schemaname = 'pg_temp' AND tablename = 'orders_backup') THEN
        INSERT INTO orders (
            order_id,
            user_id,
            order_date,
            total_amount,
            status,
            payment_status,
            payment_method,
            delivery_method,
            delivery_address,
            special_instructions,
            customer_name,
            customer_email,
            customer_phone,
            created_at,
            updated_at
        )
        SELECT
            order_id,
            user_id,
            COALESCE(order_date, CURRENT_TIMESTAMP),
            total_amount,
            COALESCE(status, 'PENDING'),
            COALESCE(payment_status, 'PENDING'),
            payment_method,
            COALESCE(delivery_method, 'DELIVERY'),
            delivery_address,
            special_instructions,
            COALESCE(customer_name, (
                SELECT CONCAT(u.first_name, ' ', u.last_name)
                FROM users u
                WHERE u.id = user_id
            )),
            COALESCE(customer_email, (
                SELECT u.email
                FROM users u
                WHERE u.id = user_id
            )),
            COALESCE(customer_phone, (
                SELECT u.phone_number
                FROM users u
                WHERE u.id = user_id
            )),
            COALESCE(created_at, CURRENT_TIMESTAMP),
            COALESCE(updated_at, CURRENT_TIMESTAMP)
        FROM orders_backup;
        
        -- Drop the temporary backup table
        DROP TABLE orders_backup;
    END IF;

    -- Restore order items data if backup exists
    IF EXISTS (SELECT FROM pg_tables WHERE schemaname = 'pg_temp' AND tablename = 'order_items_backup') THEN
        INSERT INTO order_items (
            order_item_id,
            order_id,
            item_id,
            quantity,
            unit_price,
            subtotal,
            special_requests,
            created_at,
            updated_at
        )
        SELECT
            order_item_id,
            order_id,
            item_id,
            quantity,
            unit_price,
            subtotal,
            special_requests,
            COALESCE(created_at, CURRENT_TIMESTAMP),
            COALESCE(updated_at, CURRENT_TIMESTAMP)
        FROM order_items_backup;
        
        -- Drop the temporary backup table
        DROP TABLE order_items_backup;
    END IF;
END $$;

-- Create indexes for better performance
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_item_id ON order_items(item_id); 