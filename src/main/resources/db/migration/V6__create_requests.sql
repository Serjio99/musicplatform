CREATE TABLE requests
(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id),
    song_id UUID NOT NULL REFERENCES songs (id),
    license_type VARCHAR(50) NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    comment TEXT,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);