CREATE TABLE ARTICLES (
    id INT GENERATED ALWAYS AS IDENTITY,
    key TEXT NOT NULL UNIQUE,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    source TEXT,
    latest_version_timestamp TIMESTAMP,
    redirect_target TEXT,
    PRIMARY KEY(id)
);

