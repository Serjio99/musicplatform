CREATE TABLE payments
(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id),
    request_id UUID NOT NULL UNIQUE REFERENCES requests (id),
    provider VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    amount NUMERIC(12,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    external_payment_id VARCHAR(255),
    confirmation_url VARCHAR(2000),
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);