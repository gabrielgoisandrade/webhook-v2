CREATE TABLE IF NOT EXISTS ISSUE
(
    URL                      VARCHAR(80)  NOT NULL,
    REPOSITORY_URL           VARCHAR(80)  NOT NULL,
    LABELS_URL               VARCHAR(80)  NOT NULL,
    COMMENTS_URL             VARCHAR(80)  NOT NULL,
    EVENTS_URL               VARCHAR(80)  NOT NULL,
    HTML_URL                 VARCHAR(80)  NOT NULL,
    ISSUE_ID                 BIGINT PRIMARY KEY,
    NODE_ID                  VARCHAR(50)  NOT NULL,
    NUMBER                   INT          NOT NULL,
    TITLE                    VARCHAR(80)  NOT NULL,
    STATE                    VARCHAR(20)  NOT NULL,
    LOCKED                   BOOLEAN      NOT NULL,
    COMMENTS                 VARCHAR(255) NOT NULL,
    CREATED_AT               DATE         NOT NULL,
    UPDATED_AT               DATE,
    CLOSED_AT                DATE,
    AUTHOR_ASSOCIATION       VARCHAR(20)  NOT NULL,
    ACTIVE_LOCK_REASON       VARCHAR(30),
    BODY                     VARCHAR(255),
    PERFORMED_VIA_GITHUB_APP VARCHAR(30),
    USER_ID                  INT UNIQUE   NOT NULL,
    ASSIGNEE_ID              INT UNIQUE,
    MILESTONE_ID             INT UNIQUE
);