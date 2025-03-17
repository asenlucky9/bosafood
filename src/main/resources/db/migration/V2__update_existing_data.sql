-- Drop existing columns if they exist with NOT NULL constraint
ALTER TABLE categories DROP COLUMN IF EXISTS updated_at;
ALTER TABLE special_offers DROP COLUMN IF EXISTS active;
ALTER TABLE special_offers DROP COLUMN IF EXISTS updated_at;
ALTER TABLE users DROP COLUMN IF EXISTS first_name;
ALTER TABLE users DROP COLUMN IF EXISTS last_name;

-- Add columns as nullable first
ALTER TABLE categories ADD COLUMN IF NOT EXISTS updated_at timestamp;
ALTER TABLE special_offers ADD COLUMN IF NOT EXISTS active boolean;
ALTER TABLE special_offers ADD COLUMN IF NOT EXISTS updated_at timestamp;
ALTER TABLE users ADD COLUMN IF NOT EXISTS first_name varchar(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS last_name varchar(255);

-- Update data with default values
UPDATE categories SET updated_at = created_at WHERE updated_at IS NULL;
UPDATE special_offers SET active = true WHERE active IS NULL;
UPDATE special_offers SET updated_at = created_at WHERE updated_at IS NULL;
UPDATE users SET first_name = 'Unknown' WHERE first_name IS NULL;
UPDATE users SET last_name = 'User' WHERE last_name IS NULL;

-- Add not null constraints
ALTER TABLE categories ALTER COLUMN updated_at SET NOT NULL;
ALTER TABLE special_offers ALTER COLUMN active SET NOT NULL;
ALTER TABLE special_offers ALTER COLUMN updated_at SET NOT NULL;
ALTER TABLE users ALTER COLUMN first_name SET NOT NULL;
ALTER TABLE users ALTER COLUMN last_name SET NOT NULL; 