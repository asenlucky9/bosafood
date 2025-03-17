-- Add missing columns to orders table if they don't exist
DO $$
BEGIN
    -- Add customer_name column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' AND column_name = 'customer_name'
    ) THEN
        ALTER TABLE orders ADD COLUMN customer_name VARCHAR(255);
        -- Update existing rows with a default value from users table
        UPDATE orders o 
        SET customer_name = CONCAT(u.first_name, ' ', u.last_name)
        FROM users u 
        WHERE o.user_id = u.id AND o.customer_name IS NULL;
        -- Make it not null after setting default values
        ALTER TABLE orders ALTER COLUMN customer_name SET NOT NULL;
    END IF;

    -- Add customer_email column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' AND column_name = 'customer_email'
    ) THEN
        ALTER TABLE orders ADD COLUMN customer_email VARCHAR(255);
        -- Update existing rows with a default value from users table
        UPDATE orders o 
        SET customer_email = u.email
        FROM users u 
        WHERE o.user_id = u.id AND o.customer_email IS NULL;
        -- Make it not null after setting default values
        ALTER TABLE orders ALTER COLUMN customer_email SET NOT NULL;
    END IF;

    -- Add customer_phone column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' AND column_name = 'customer_phone'
    ) THEN
        ALTER TABLE orders ADD COLUMN customer_phone VARCHAR(20);
        -- Update existing rows with a default value from users table
        UPDATE orders o 
        SET customer_phone = u.phone_number
        FROM users u 
        WHERE o.user_id = u.id AND o.customer_phone IS NULL;
    END IF;

    -- Add created_at column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' AND column_name = 'created_at'
    ) THEN
        ALTER TABLE orders ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
        UPDATE orders SET created_at = order_date WHERE created_at IS NULL;
        ALTER TABLE orders ALTER COLUMN created_at SET NOT NULL;
    END IF;

    -- Add updated_at column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'orders' AND column_name = 'updated_at'
    ) THEN
        ALTER TABLE orders ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
        UPDATE orders SET updated_at = created_at WHERE updated_at IS NULL;
        ALTER TABLE orders ALTER COLUMN updated_at SET NOT NULL;
    END IF;

END $$; 