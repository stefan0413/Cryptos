CREATE TABLE IF NOT EXISTS `customer_payment_method`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_id` BIGINT NOT NULL,
    `payment_method_token` VARCHAR(64) UNIQUE,
    `stripe_payment_method_id` VARCHAR(32) UNIQUE,

    INDEX `customer_stripe_account_id_idx` (`id`),
    INDEX `customer_stripe_account_customer_id_idx` (`customer_id`)
);