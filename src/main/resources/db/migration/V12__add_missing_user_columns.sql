-- Add missing columns to users table if they don't exist
DO $$
BEGIN
    -- Add first_name column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'users' AND column_name = 'first_name'
    ) THEN
        ALTER TABLE users ADD COLUMN first_name VARCHAR(100);
        -- Update existing rows with a default value
        UPDATE users SET first_name = split_part(email, '@', 1) WHERE first_name IS NULL;
        -- Make it not null after setting default values
        ALTER TABLE users ALTER COLUMN first_name SET NOT NULL;
    END IF;

    -- Add last_name column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'users' AND column_name = 'last_name'
    ) THEN
        ALTER TABLE users ADD COLUMN last_name VARCHAR(100);
        -- Update existing rows with a default value
        UPDATE users SET last_name = 'User' WHERE last_name IS NULL;
        -- Make it not null after setting default values
        ALTER TABLE users ALTER COLUMN last_name SET NOT NULL;
    END IF;

    -- Add phone_number column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'users' AND column_name = 'phone_number'
    ) THEN
        ALTER TABLE users ADD COLUMN phone_number VARCHAR(20);
    END IF;

    -- Add address column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'users' AND column_name = 'address'
    ) THEN
        ALTER TABLE users ADD COLUMN address TEXT;
    END IF;

    -- Add role column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'users' AND column_name = 'role'
    ) THEN
        ALTER TABLE users ADD COLUMN role VARCHAR(20) DEFAULT 'USER';
        UPDATE users SET role = 'USER' WHERE role IS NULL;
        ALTER TABLE users ALTER COLUMN role SET NOT NULL;
    END IF;

    -- Add created_at column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'users' AND column_name = 'created_at'
    ) THEN
        ALTER TABLE users ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
        UPDATE users SET created_at = CURRENT_TIMESTAMP WHERE created_at IS NULL;
        ALTER TABLE users ALTER COLUMN created_at SET NOT NULL;
    END IF;

    -- Add updated_at column if it doesn't exist
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'users' AND column_name = 'updated_at'
    ) THEN
        ALTER TABLE users ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
        UPDATE users SET updated_at = CURRENT_TIMESTAMP WHERE updated_at IS NULL;
        ALTER TABLE users ALTER COLUMN updated_at SET NOT NULL;
    END IF;

END $$; 