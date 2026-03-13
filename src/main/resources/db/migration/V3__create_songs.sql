CREATE TABLE songs
(
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image_file_id UUID,
    audio_file_id UUID,
    license_price NUMERIC(12, 2),
    economy_price NUMERIC(12, 2),
    standard_price NUMERIC(12, 2),
    business_price NUMERIC(12, 2),
    premium_price NUMERIC(12, 2),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);