CREATE TABLE IF NOT EXISTS `debit_card` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `customer_id` VARCHAR(100) NOT NULL,
    `card_number` VARCHAR(100) NOT NULL,
    `card_type` VARCHAR(100) NOT NULL,
    `account_number` VARCHAR(100) NOT NULL,
    `cvv` INT NOT NULL,
    `expiry_date` VARCHAR(100) NOT NULL,
    `created_at` DATE NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATE DEFAULT NULL,
    `updated_by` VARCHAR(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);