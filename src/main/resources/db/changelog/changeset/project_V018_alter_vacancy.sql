ALTER TABLE vacancy
ADD COLUMN owner_member BIGINT;
ALTER TABLE vacancy
ADD COLUMN team_role VARCHAR(255);