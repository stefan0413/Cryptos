CREATE TABLE IF NOT EXISTS `customer`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `email` VARCHAR(64) NOT NULL UNIQUE,
    `password` VARCHAR(64) NOT NULL UNIQUE,
    `first_name` VARCHAR(64) NOT NULL,
    `second_name` VARCHAR(64) NOT NULL,
    `last_name` VARCHAR(64) NOT NULL,
    `mobile_number` VARCHAR(64) NOT NULL UNIQUE,

    INDEX `customer_id_idx` (`id`),
    INDEX `customer_email_idx` (`email`)
)