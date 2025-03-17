-- Rename active column to is_active if it exists
ALTER TABLE special_offers 
    RENAME COLUMN active TO is_active; 