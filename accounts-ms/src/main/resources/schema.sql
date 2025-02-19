CREATE TABLE IF NOT EXISTS account (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account_number varchar(100) NOT NULL,
    account_type varchar(200) NOT NULL,
    branch_address varchar(200) NOT NULL,
    account_balance INT NOT NULL,
    mobile_number varchar(200) NOT NULL,
    active_sw BOOLEAN NOT NULL,
    created_at DATE NOT NULL,
    created_by varchar(20) NOT NULL,
    updated_at DATE DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL
);