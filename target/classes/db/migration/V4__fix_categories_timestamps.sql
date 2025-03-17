-- Drop and recreate updated_at column in categories table
ALTER TABLE categories DROP COLUMN IF EXISTS updated_at;
ALTER TABLE categories ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
UPDATE categories SET updated_at = created_at WHERE updated_at IS NULL;
ALTER TABLE categories ALTER COLUMN updated_at SET NOT NULL; 