CREATE TABLE users_usrs
(
    usrs_id    varchar(40)  NOT NULL,
    name       varchar(255) NOT NULL,
    email      varchar(255) NULL,
    jwt_sub    varchar(50) NULL,
    created_at timestamptz(6) NOT NULL,
    updated_at timestamptz(6) NULL,
    CONSTRAINT users_usrs_pkey PRIMARY KEY (usrs_id)
);

CREATE INDEX idx_users_jwt_sub ON users_usrs (jwt_sub);

CREATE TABLE security_policies_spls
(
    spls_id    varchar(40)  NOT NULL,
    usrs_id    varchar(40)  NOT NULL,
    name       varchar(255) NOT NULL,
    policy     varchar(255) NOT NULL,
    created_at timestamptz(6) NOT NULL,
    updated_at timestamptz(6) NULL,
    CONSTRAINT security_policies_pkey PRIMARY KEY (spls_id),
    CONSTRAINT fk_security_policies_spls_usrs_id FOREIGN KEY (usrs_id) REFERENCES users_usrs (usrs_id) ON DELETE CASCADE
);

CREATE INDEX idx_security_policies_spls_usrs_id ON security_policies_spls (usrs_id);

ALTER TABLE accounts_acnt
    ADD FOREIGN KEY (owner_id) REFERENCES users_usrs (usrs_id) ON DELETE SET NULL;


