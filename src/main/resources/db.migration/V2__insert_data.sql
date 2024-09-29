-- Insert into User table
INSERT INTO users (id, username, password, email, created_at, updated_at)
VALUES
    ('a3bde567-8b94-4b93-bb59-01ad4a94589e', 'johndoe', 'encryptedpassword', 'john@example.com', NOW(), NOW()),
    ('b4cdf645-9f68-4373-b5cc-34be38fd5861', 'janedoe', 'encryptedpassword', 'jane@example.com', NOW(), NOW());

-- Insert into Account table
INSERT INTO accounts (id, number, name, balance, created_at, updated_at, user_id)
VALUES
    ('5013eb36-24cd-4e70-92e2-c8495f562e6b', '1234567890', 'John Account', 1000.00, NOW(), NOW(), 'a3bde567-8b94-4b93-bb59-01ad4a94589e'),
    ('a9ffaf42-74f1-4d09-89a1-3dcfb4d9e50e', '9876543210', 'Jane Account', 2000.00, NOW(), NOW(), 'b4cdf645-9f68-4373-b5cc-34be38fd5861');

-- Insert into Transaction table
INSERT INTO transactions (from_account, to_account, amount, transaction_date, status)
VALUES
    ('5013eb36-24cd-4e70-92e2-c8495f562e6b', 'a9ffaf42-74f1-4d09-89a1-3dcfb4d9e50e', 100.00, NOW(), 'SUCCESS');
