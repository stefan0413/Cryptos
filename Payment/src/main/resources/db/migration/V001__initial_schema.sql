CREATE TABLE IF NOT EXISTS `customer_stripe_account`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_id` BIGINT NOT NULL UNIQUE,
    `currency_code` VARCHAR(3) NOT NULL,
    `free_balance` DECIMAL(19,4) NOT NULL,
    `email` VARCHAR(64) NOT NULL UNIQUE,
    `names` VARCHAR(128) NOT NULL,

    INDEX `customer_stripe_account_id_idx` (`id`),
    INDEX `customer_stripe_account_customer_id_idx` (`customer_id`),
    INDEX `customer_stripe_account_email_idx` (`email`)
);