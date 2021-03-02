CREATE TABLE IF NOT EXISTS PAYLOAD
(
    PAYLOAD_ID    SERIAL PRIMARY KEY,
    ACTION        VARCHAR(30) NOT NULL,
    ISSUE_ID      INT UNIQUE  NOT NULL,
    REPOSITORY_ID INT UNIQUE  NOT NULL,
    SENDER_ID     INT UNIQUE  NOT NULL
)