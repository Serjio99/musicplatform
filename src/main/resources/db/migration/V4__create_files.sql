CREATE TABLE files
(
    id UUID PRIMARY KEY,
    original_name VARCHAR(500) NOT NULL,
    storage_name VARCHAR(500) NOT NULL UNIQUE,
    content_type VARCHAR(255),
    size_bytes BIGINT NOT NULL,
    storage_path VARCHAR(1000) NOT NULL,
    public_url VARCHAR(1000),
    is_image BOOLEAN NOT NULL DEFAULT FALSE,
    is_audio BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);