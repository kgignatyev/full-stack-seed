
INSERT INTO users_usrs (usrs_id, name, email, jwt_sub, created_at, updated_at)
VALUES ('guest', 'guest', 'guest+fss@test.com', 'none', '2025-07-17', '2025-07-17');


INSERT INTO security_policies_spls (spls_id, usrs_id, name, policy, created_at)
VALUES ('p_guest1', 'guest', 'guest', 'none, none', '2025-07-17');
