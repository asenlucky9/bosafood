-- Drop the table if it exists
DROP TABLE IF EXISTS special_offer_menu_items;
DROP TABLE IF EXISTS special_offers;

-- Create special_offers table
CREATE TABLE special_offers (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    discount_percentage DECIMAL(5,2) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create join table for special offers and menu items
CREATE TABLE special_offer_menu_items (
    special_offer_id BIGINT REFERENCES special_offers(id),
    menu_item_id BIGINT REFERENCES menu_items(id),
    PRIMARY KEY (special_offer_id, menu_item_id)
);

-- Create indexes
CREATE INDEX idx_special_offers_dates ON special_offers(start_date, end_date);
CREATE INDEX idx_special_offers_active ON special_offers(is_active); 