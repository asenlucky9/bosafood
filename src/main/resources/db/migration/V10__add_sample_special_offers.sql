-- Insert sample special offers
INSERT INTO special_offers (
    title,
    description,
    discount_percentage,
    start_date,
    end_date,
    is_active
) VALUES 
(
    'Weekend Special',
    'Get 20% off on all main courses this weekend!',
    20.00,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP + INTERVAL '2 days',
    true
),
(
    'Happy Hour',
    'Enjoy 15% off on all beverages between 4 PM and 7 PM',
    15.00,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP + INTERVAL '30 days',
    true
),
(
    'Lunch Special',
    'Get 10% off on all appetizers with any main course',
    10.00,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP + INTERVAL '15 days',
    true
); 