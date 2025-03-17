-- Drop and recreate the table to ensure it matches our entity exactly
DROP TABLE IF EXISTS special_offers CASCADE;

CREATE TABLE special_offers (
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
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create the join table for menu items if it doesn't exist
CREATE TABLE IF NOT EXISTS special_offer_menu_items (
    special_offer_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    PRIMARY KEY (special_offer_id, menu_item_id),
    FOREIGN KEY (special_offer_id) REFERENCES special_offers(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id) ON DELETE CASCADE
); 