-- Create function to update timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create trigger for special_offers table
DROP TRIGGER IF EXISTS update_special_offers_updated_at ON special_offers;

CREATE TRIGGER update_special_offers_updated_at
    BEFORE UPDATE ON special_offers
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column(); 