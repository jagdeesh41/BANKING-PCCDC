CREATE TABLE IF NOT EXISTS `payment_debit_card` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `card_number` VARCHAR(100) NOT NULL,
    `card_type` VARCHAR(100) NOT NULL,
    `cvv` INT NOT NULL,
    `expiry_date` VARCHAR(100) NOT NULL,
    `balance` INT NOT NULL,
    `is3DEnabled` BOOLEAN DEFAULT FALSE,
    `created_at` DATE NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATE DEFAULT NULL,
    `updated_by` VARCHAR(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `saved_card` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `customer_id` BIGINT NOT NULL,
    `debit_card_number` VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS `transaction` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `transaction_token` VARCHAR(100) NOT NULL,
    `amount` DECIMAL(10, 2) NOT NULL,
    `customer_id` BIGINT NOT NULL,
    `debit_card_number` VARCHAR(20) DEFAULT NULL,
    `credit_card_number` VARCHAR(20) DEFAULT NULL,
    `payment_method` VARCHAR(50) NOT NULL,
    `payment_status` VARCHAR(50) NOT NULL,
    `created_at` DATETIME DEFAULT NULL,
    `created_by` VARCHAR(255) DEFAULT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `updated_by` VARCHAR(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `otp` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `debit_card_number` VARCHAR(100) NOT NULL,
    `otp` INT NOT NULL,
    `expiry_date` TIMESTAMP NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
