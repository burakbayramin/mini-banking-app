-- Create User table
CREATE TABLE IF NOT EXISTS users (
                                     id UUID PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

-- Create Account table
CREATE TABLE IF NOT EXISTS accounts (
                                        id UUID PRIMARY KEY,
                                        number VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE
    );

-- Create Transaction table
CREATE TABLE IF NOT EXISTS transactions (
                                            id BIGSERIAL PRIMARY KEY,
                                            from_account UUID REFERENCES accounts(id) ON DELETE CASCADE,
    to_account UUID REFERENCES accounts(id) ON DELETE CASCADE,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL
    );
