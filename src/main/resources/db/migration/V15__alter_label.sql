ALTER TABLE
    LABEL
ADD
    CONSTRAINT C_LABEL_ISSUE FOREIGN KEY (ISSUE_ID) REFERENCES ISSUE (ISSUE_ID);