CREATE TABLE IF NOT EXISTS EVENT
(
    EVENT_ID SERIAL PRIMARY KEY,
    ACTION   VARCHAR(30) NOT NULL UNIQUE
)