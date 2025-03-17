-- Update categories table
UPDATE categories SET updated_at = created_at WHERE updated_at IS NULL;
ALTER TABLE categories ALTER COLUMN updated_at SET NOT NULL;

-- Update special_offers table
UPDATE special_offers SET active = true WHERE active IS NULL;
UPDATE special_offers SET updated_at = created_at WHERE updated_at IS NULL;
ALTER TABLE special_offers ALTER COLUMN active SET NOT NULL;
ALTER TABLE special_offers ALTER COLUMN updated_at SET NOT NULL;

-- Update users table
UPDATE users SET first_name = 'Unknown' WHERE first_name IS NULL;
UPDATE users SET last_name = 'User' WHERE last_name IS NULL;
ALTER TABLE users ALTER COLUMN first_name SET NOT NULL;
ALTER TABLE users ALTER COLUMN last_name SET NOT NULL; 