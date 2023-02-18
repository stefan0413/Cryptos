CREATE TABLE IF NOT EXISTS `customer_stripe_accounts`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_id` BIGINT NOT NULL UNIQUE,
    `free_balance` DECIMAL(19,4),
    `invested_balance` DECIMAL(19,4),
    `email` VARCHAR(64) NOT NULL UNIQUE,
    `names` VARCHAR(128) NOT NULL UNIQUE,

    INDEX `customer_stripe_accounts_id_idx` (`id`),
    INDEX `customer_stripe_accounts_customer_id_idx` (`customer_id`),
    INDEX `customer_stripe_accounts_email_idx` (`email`)
);