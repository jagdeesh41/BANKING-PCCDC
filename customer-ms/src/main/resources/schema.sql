CREATE TABLE IF NOT EXISTS customer (
    customer_id varchar(100) NOT NULL PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    mobile_number varchar(20) NOT NULL,
    communication_sw BOOLEAN DEFAULT FALSE,
    created_at DATE NOT NULL,
    created_by varchar(20) NOT NULL,
    updated_at DATE DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
    active_sw BOOLEAN DEFAULT TRUE

);


CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id varchar(100) NOT NULL,
    account_number varchar(100) NOT NULL,
    account_type varchar(200) NOT NULL,
    branch_address varchar(200) NOT NULL,
    account_balance INT NOT NULL,
    created_at DATE NOT NULL,
    created_by varchar(20) NOT NULL,
    updated_at DATE DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL
);
