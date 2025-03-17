-- Check if updated_at column exists and add it if it doesn't
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'special_offers'
        AND column_name = 'updated_at'
    ) THEN
        ALTER TABLE special_offers
        ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Update any null values in updated_at
UPDATE special_offers
SET updated_at = created_at
WHERE updated_at IS NULL;

-- Make updated_at not null
ALTER TABLE special_offers
ALTER COLUMN updated_at SET NOT NULL; 