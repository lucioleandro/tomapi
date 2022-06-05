-- ==========================================================
-- Author: Lucio Leandro
-- Update date: 05/06/2022
-- Description: Estrutura Inicial do TOM
-- ==========================================================

-- Table `user`

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(50) NOT NULL,
  CONSTRAINT `PK_user` PRIMARY KEY (`id` ASC),
  UNIQUE KEY `login_unique` (`login`)
);

-- Table `role`

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `uuid` binary(255) DEFAULT NULL,
  CONSTRAINT `PK_role`PRIMARY KEY (`id` ASC)
);

-- Table `team`

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `uuid` binary(255) NOT NULL,
  CONSTRAINT `PK_team` PRIMARY KEY (`id` ASC)
);

-- Table `member`

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `display_name` varchar(255) NOT NULL,
  `uuid` binary(255) NOT NULL,
  `role_id` INT DEFAULT NULL,
  CONSTRAINT `PK_member` PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_member_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE Restrict ON UPDATE Restrict
);


-- Table `member_ship`

DROP TABLE IF EXISTS `member_ship`;

CREATE TABLE `member_ship` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT DEFAULT NULL,
  `team_id` INT DEFAULT NULL,
  CONSTRAINT `PK_member_ship`PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_member_ship_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE Restrict ON UPDATE Restrict,
  CONSTRAINT `FK_member_ship_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE Restrict ON UPDATE Restrict
);







