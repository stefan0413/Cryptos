CREATE TABLE IF NOT EXISTS `customer_trading_account`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_id` BIGINT NOT NULL,
    `supported_currency_id` INT NOT NULL,
    `currency_amount` DECIMAL(19,4) NOT NULL,

    INDEX `customer_trading_account_id_idx` (`id`),
    INDEX `customer_trading_account_customer_id_idx` (`customer_id`),
    INDEX `customer_trading_account_supported_currency_id_idx` (`supported_currency_id`),

    CONSTRAINT `FK_customer_trading_account_n_supported_currencies` FOREIGN KEY (`supported_currency_id`) REFERENCES `n_supported_currencies`(`id`)
);