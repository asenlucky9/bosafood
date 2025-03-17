ALTER TABLE special_offers
ADD COLUMN terms_and_conditions VARCHAR(1000),
ADD COLUMN minimum_order_value INTEGER,
ADD COLUMN maximum_discount INTEGER; 