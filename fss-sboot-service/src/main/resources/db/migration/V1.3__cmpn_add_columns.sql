ALTER TABLE companies_cmpn
    ADD COLUMN banned char(1) NOT NULL DEFAULT 'N',
    ADD COLUMN source varchar(40) NULL,
    ADD COLUMN notes text NULL,
    ADD COLUMN created_at timestamptz(6) NULL,
    ADD COLUMN updated_at timestamptz(6) NULL;
