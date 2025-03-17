-- Backup existing menu_items data
CREATE TEMP TABLE menu_items_backup AS SELECT * FROM menu_items;

-- Drop the existing menu_items table
DROP TABLE IF EXISTS menu_items CASCADE;

-- Recreate the menu_items table with all required columns
CREATE TABLE menu_items (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    category_id BIGINT REFERENCES categories(id),
    image_url TEXT,
    is_vegetarian BOOLEAN DEFAULT false,
    is_spicy BOOLEAN DEFAULT false,
    is_available BOOLEAN DEFAULT true,
    preparation_time INTEGER,
    calories INTEGER,
    allergens TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Restore data with default values for new columns
INSERT INTO menu_items (
    id,
    name,
    description,
    price,
    category_id,
    image_url,
    is_vegetarian,
    is_spicy,
    is_available,
    preparation_time,
    calories,
    allergens,
    created_at,
    updated_at
)
SELECT
    id,
    name,
    description,
    price,
    category_id,
    image_url,
    COALESCE(is_vegetarian, false),
    COALESCE(is_spicy, false),
    COALESCE(is_available, true),
    preparation_time,
    calories,
    allergens,
    COALESCE(created_at, CURRENT_TIMESTAMP),
    COALESCE(updated_at, CURRENT_TIMESTAMP)
FROM menu_items_backup;

-- Create indexes for better performance
CREATE INDEX idx_menu_items_category ON menu_items(category_id);

-- Drop the temporary backup table
DROP TABLE menu_items_backup; 