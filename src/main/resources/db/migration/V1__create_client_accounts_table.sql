CREATE TABLE client_accounts (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    full_name TEXT NOT NULL,
    gender VARCHAR(1) CHECK (gender in ('M', 'F')),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' CHECK (status in ('ACTIVE', 'INACTIVE')),
    create_dttm TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    modify_dttm TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE OR REPLACE FUNCTION set_modify_dttm()
RETURNS TRIGGER AS $$
    BEGIN
    NEW.modify_dttm := now();
    RETURN NEW;
    END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER before_update_modify_dttm
BEFORE UPDATE ON client_accounts
FOR EACH ROW
EXECUTE FUNCTION set_modify_dttm();