CREATE TABLE comments
(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users (id),
    song_id UUID NOT NULL REFERENCES songs (id),
    text TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);