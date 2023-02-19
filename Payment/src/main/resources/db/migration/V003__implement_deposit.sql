CREATE TABLE IF NOT EXISTS `n_deposit_statuses`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(64) NOT NULL
);

INSERT IGNORE INTO `n_deposit_statuses` (name)
VALUES ('PENDING'),
       ('FAILED_EXECUTION'),
       ('EXECUTED');

CREATE TABLE IF NOT EXISTS `deposit`
(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_id` BIGINT NOT NULL,
    `stripe_payment_method_id` VARCHAR(32) NOT NULL,
    `stripe_payment_intent_id` VARCHAR(32) NOT NULL,
    `amount` VARCHAR(32) NOT NULL,
    `currency_code` VARCHAR(3) NOT NULL,
    `status_id`  INT NOT NULL,
    `created_at` DATETIME NOT NULL ,
    `executed_at` DATETIME,

    INDEX `deposit_id_idx` (`id`),
    INDEX `deposit_customer_id_idx` (`customer_id`),
    INDEX `deposit_stripe_payment_method_id_idx` (`stripe_payment_method_id`),

    CONSTRAINT `FK_deposit_n_deposit_statuses` FOREIGN KEY (`status_id`) REFERENCES `n_deposit_statuses`(`id`)
);

