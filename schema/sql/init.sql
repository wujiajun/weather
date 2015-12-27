CREATE SCHEMA IF NOT EXISTS `weather` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `weather` ;

-- -----------------------------------------------------
-- Table `weather`.`tb_donate`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `weather`.`tb_donate` (
  `udid` VARCHAR(100) NOT NULL ,
  `sum` INT(10) NOT NULL DEFAULT 0 ,
  `origin` VARCHAR(45) NOT NULL DEFAULT 'iphone' ,
  `last_order_id` VARCHAR(100) NOT NULL ,
  `last_donate_time` DATETIME NOT NULL ,
  `color_index` INT(2) NOT NULL DEFAULT 0 ,
  `name` VARCHAR(50) NOT NULL ,
  `city` VARCHAR(60) NOT NULL ,
  PRIMARY KEY (`udid`) ,
  INDEX `idx_sum_lastdonatetime` (`sum` DESC, `last_donate_time` DESC) ,
  INDEX `idx_lastdonatetime` (`last_donate_time` DESC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

-- -----------------------------------------------------
-- Table `weather`.`tb_order`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `weather`.`tb_order` (
  `order_id` VARCHAR(100) NOT NULL ,
  `udid` VARCHAR(100) NOT NULL ,
  `origin` VARCHAR(45) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `price` INT(10) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`order_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;