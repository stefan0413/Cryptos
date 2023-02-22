CREATE TABLE IF NOT EXISTS `n_withdrawal_statuses`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(64) NOT NULL
);

INSERT IGNORE INTO `n_withdrawal_statuses` (name)
VALUES ('PENDING'),
       ('FAILED_EXECUTION'),
       ('EXECUTED');

CREATE TABLE IF NOT EXISTS `withdrawal`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_id` BIGINT NOT NULL,
    `iban` VARCHAR(32) NOT NULL,
    `amount` DECIMAL(19,4) NOT NULL,
    `status_id` INT NOT NULL,
    `created_at` DATETIME NOT NULL,

    INDEX `withdrawal_id` (`id`),
    INDEX `withdrawal_customer_id` (`customer_id`),

    CONSTRAINT `FK_withdrawal_n_withdrawal_statuses` FOREIGN KEY (`status_id`) REFERENCES `n_withdrawal_statuses`(`id`)
);