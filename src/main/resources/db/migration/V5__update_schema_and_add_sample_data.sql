-- Drop existing tables if they exist
DROP TABLE IF EXISTS menu_items CASCADE;
DROP TABLE IF EXISTS menu_categories CASCADE;

-- Menu Categories Table
CREATE TABLE menu_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Menu Items Table
CREATE TABLE menu_items (
    id SERIAL PRIMARY KEY,
    category_id INTEGER REFERENCES menu_categories(id),
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(255),
    is_vegetarian BOOLEAN DEFAULT false,
    is_spicy BOOLEAN DEFAULT false,
    is_available BOOLEAN DEFAULT true,
    preparation_time INTEGER,
    calories INTEGER,
    allergens TEXT[],
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Index
CREATE INDEX idx_menu_items_category_id ON menu_items(category_id);

-- Insert Sample Data
-- Insert Menu Categories
INSERT INTO menu_categories (name, description) VALUES
('Appetizers', 'Start your meal with our delicious appetizers'),
('Main Courses', 'Signature dishes from our kitchen'),
('Desserts', 'Sweet endings to your meal'),
('Beverages', 'Refreshing drinks and cocktails');

-- Insert Menu Items
INSERT INTO menu_items (category_id, name, description, price, is_vegetarian, is_spicy) VALUES
(1, 'Spring Rolls', 'Fresh vegetables wrapped in rice paper', 5.99, true, false),
(1, 'Spicy Wings', 'Crispy chicken wings with our special sauce', 8.99, false, true),
(2, 'Grilled Salmon', 'Fresh salmon with herbs and lemon', 24.99, false, false),
(2, 'Vegetable Curry', 'Mixed vegetables in aromatic curry sauce', 16.99, true, true),
(3, 'Chocolate Cake', 'Rich chocolate layer cake', 6.99, true, false),
(4, 'Fresh Lemonade', 'Homemade lemonade with mint', 3.99, true, false); 