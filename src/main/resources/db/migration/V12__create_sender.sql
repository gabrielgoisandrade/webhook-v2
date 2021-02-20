CREATE TABLE IF NOT EXISTS SENDER (
    LOGIN VARCHAR(50) NOT NULL,
    SENDER_ID INT PRIMARY KEY,
    NODE_ID VARCHAR(50) UNIQUE NOT NULL,
    AVATAR_URL VARCHAR(80) NOT NULL,
    GRAVATAR_ID VARCHAR(50) UNIQUE NOT NULL,
    URL VARCHAR(80) NOT NULL,
    HTML_URL VARCHAR(80) NOT NULL,
    FOLLOWERS_URL VARCHAR(80) NOT NULL,
    FOLLOWING_URL VARCHAR(80) NOT NULL,
    GISTS_URL VARCHAR(80) NOT NULL,
    STARRED_URL VARCHAR(80) NOT NULL,
    SUBSCRIPTIONS_URL VARCHAR(80) NOT NULL,
    ORGANIZATIONS_URL VARCHAR(80) NOT NULL,
    REPOS_URL VARCHAR(80) NOT NULL,
    EVENTS_URL VARCHAR(80) NOT NULL,
    RECEIVED_EVENTS_URL VARCHAR(80) NOT NULL,
    TYPE VARCHAR(30) NOT NULL,
    SITE_ADMIN BOOLEAN NOT NULL,
    PAYLOAD_ID INT UNIQUE NOT NULL
);