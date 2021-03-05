ALTER TABLE ISSUE
    ADD CONSTRAINT C_ISSUE_ASSIGNEE
        FOREIGN KEY (ASSIGNEE_ID)
            REFERENCES ASSIGNEE (ASSIGNEE_ID);

ALTER TABLE ISSUE
    ADD CONSTRAINT C_ISSUE_USER
        FOREIGN KEY (USER_ID)
            REFERENCES "user" (USER_ID);

ALTER TABLE ISSUE
    ADD CONSTRAINT C_ISSUE_MILESTONE
        FOREIGN KEY (MILESTONE_ID)
            REFERENCES MILESTONE (MILESTONE_ID);
