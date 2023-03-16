CREATE TABLE IF NOT EXISTS `n_order_statuses`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(64) NOT NULL
);

INSERT IGNORE INTO `n_order_statuses` (name)
VALUES ('PENDING'),
       ('IN_EXECUTION'),
       ('FAILED_EXECUTION'),
       ('EXECUTED');

CREATE TABLE IF NOT EXISTS `n_supported_currencies`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `code` VARCHAR(4) NOT NULL
);

INSERT IGNORE INTO `n_supported_currencies` (code)
VALUES ('BTC'),
       ('ETH'),
       ('BNB'),
       ('ADA'),
       ('XRP'),
       ('DOT'),
       ('DOGE'),
       ('UNI'),
       ('LTC'),
       ('BCH');

CREATE TABLE IF NOT EXISTS `n_order_type`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `type` VARCHAR(16) NOT NULL
);

INSERT IGNORE INTO `n_order_type` (type)
VALUES ('BUY'),
       ('SELL');

CREATE TABLE IF NOT EXISTS `order`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_id` BIGINT NOT NULL,
    `supported_currency_id` INT NOT NULL,
    `type_id` INT NOT NULL,
    `order_size` DECIMAL(19,4) NOT NULL,
    `order_price` DECIMAL(19,4) NOT NULL,
    `total_cost` DECIMAL(19,4)  NOT NULL,
    `status_id` INT NOT NULL,
    `created_at` DATETIME NOT NULL ,
    `executed_at` DATETIME,

    INDEX `order_id_idx` (`id`),
    INDEX `order_customer_id_idx` (`customer_id`),
    INDEX `order_supported_currency_id_idx` (`supported_currency_id`),
    INDEX `order_type_id_idx` (`type_id`),
    INDEX `order_status_id_idx` (`status_id`),

    CONSTRAINT `FK_order_n_supported_currencies` FOREIGN KEY (`supported_currency_id`) REFERENCES `n_supported_currencies`(`id`),
    CONSTRAINT `FK_order_n_order_statuses` FOREIGN KEY (`status_id`) REFERENCES `n_order_statuses`(`id`),
    CONSTRAINT `FK_order_n_order_type` FOREIGN KEY (`type_id`) REFERENCES `n_order_type`(`id`)
);