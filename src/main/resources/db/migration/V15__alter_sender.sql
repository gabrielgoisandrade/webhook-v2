ALTER TABLE IF EXISTS SENDER
    ADD CONSTRAINT C_SENDER_EVENT
        FOREIGN KEY (EVENT_ID)
            REFERENCES EVENT (EVENT_ID);