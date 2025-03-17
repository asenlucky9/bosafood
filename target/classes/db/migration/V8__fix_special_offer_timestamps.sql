-- Create the table if it doesn't exist
CREATE TABLE IF NOT EXISTS special_offers (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    discount_percentage DECIMAL(5,2) NOT NULL,
    start_date TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date TIMESTAMP WITH TIME ZONE NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    terms_and_conditions VARCHAR(1000),
    minimum_order_value INTEGER,
    maximum_discount INTEGER,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add updated_at column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public'
        AND table_name = 'special_offers' 
        AND column_name = 'updated_at'
    ) THEN
        ALTER TABLE special_offers 
        ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE;
        
        -- Set initial values for updated_at based on created_at
        UPDATE special_offers 
        SET updated_at = created_at;
        
        -- Make it not null after setting initial values
        ALTER TABLE special_offers 
        ALTER COLUMN updated_at SET NOT NULL;
        
        -- Add default value for new rows
        ALTER TABLE special_offers 
        ALTER COLUMN updated_at SET DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$; 