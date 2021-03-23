ALTER TABLE IF EXISTS ISSUE_CLASSIFIER
    ADD CONSTRAINT C_ISSUE_CLASSIFIER_ISSUE
        FOREIGN KEY (ISSUE_ID)
            REFERENCES ISSUE (ISSUE_ID);

ALTER TABLE IF EXISTS ISSUE_CLASSIFIER
    ADD CONSTRAINT C_ISSUE_CLASSIFIER_LABELS
        FOREIGN KEY (LABELS_ID)
            REFERENCES LABELS (LABELS_ID)
