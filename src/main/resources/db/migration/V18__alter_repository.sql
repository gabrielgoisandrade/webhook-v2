ALTER TABLE IF EXISTS REPOSITORY
    ADD CONSTRAINT C_REPOSITORY_OWNER
        FOREIGN KEY (OWNER_ID)
            REFERENCES OWNER (OWNER_ID);

ALTER TABLE IF EXISTS REPOSITORY
    ADD CONSTRAINT C_REPOSITORY_LICENSE
        FOREIGN KEY (LICENSE_ID)
            REFERENCES LICENSE (LICENSE_ID);