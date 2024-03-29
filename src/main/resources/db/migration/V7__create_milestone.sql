CREATE TABLE IF NOT EXISTS MILESTONE
(
    MILESTONE_ID  SERIAL PRIMARY KEY,
    URL           VARCHAR(80)        NOT NULL,
    HTML_URL      VARCHAR(255)       NOT NULL,
    LABELS_URL    VARCHAR(255)       NOT NULL,
    NODE_ID       VARCHAR(50) UNIQUE NOT NULL,
    NUMBER        INT UNIQUE         NOT NULL,
    TITLE         VARCHAR(80)        NOT NULL,
    DESCRIPTION   VARCHAR(2500),
    OPEN_ISSUES   INT                NOT NULL,
    CLOSED_ISSUES INT                NOT NULL,
    STATE         VARCHAR(20)        NOT NULL,
    CREATED_AT    TIMESTAMP        NOT NULL,
    UPDATED_AT    TIMESTAMP,
    DUE_ON        TIMESTAMP,
    CLOSED_AT     TIMESTAMP,
    CREATOR_ID    INT                NOT NULL
);