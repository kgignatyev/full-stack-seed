CREATE TABLE accounts_acnt
(
    acnt_id    varchar(40)  NOT NULL,
    name       varchar(255) NOT NULL,
    email      varchar(255) NULL,
    notes      text NULL,
    owner_id   varchar(50) NULL,
    active     varchar(20)  NOT NULL,
    created_at timestamptz(6) NOT NULL,
    updated_at timestamptz(6) NULL,
    CONSTRAINT accounts_pkey PRIMARY KEY (acnt_id)
);


CREATE TABLE companies_cmpn
(
    cmpn_id varchar(40) NOT NULL,
    acnt_id varchar(40) NOT NULL,
    name    varchar(255) NULL,
    CONSTRAINT companies_pkey PRIMARY KEY (cmpn_id)
);

CREATE INDEX idx_companies_acnt_id ON companies_cmpn (acnt_id);

CREATE TABLE jobs_jobs
(
    jobs_id          varchar(40) NOT NULL,
    acnt_id          varchar(40) NOT NULL,
    company_name     varchar(100) NULL,
    company_response varchar(20) NULL,
    created_at       timestamptz(6) NULL,
    description      text NULL,
    notes            varchar(256) NULL,
    source           varchar(40) NULL,
    source_id        varchar(40) NULL,
    status           varchar(20) NULL,
    title            varchar(255) NULL,
    updated_at       timestamptz(6) NULL,
    CONSTRAINT jobs_pkey PRIMARY KEY (jobs_id)
);
CREATE INDEX idx_jobs_company_name ON jobs_jobs (company_name);
CREATE INDEX idx_jobs_acnt_id ON jobs_jobs (acnt_id);
CREATE INDEX idx_jobs_title ON jobs_jobs (title);
