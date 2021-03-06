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
  `name` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `uuid` binary(36) DEFAULT NULL,
  CONSTRAINT `PK_role`PRIMARY KEY (`id` ASC),
  CONSTRAINT UC_role_name UNIQUE (name)
);

-- Table `member`

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` varchar(30),
  `last_name` varchar(30),
  `display_name` varchar(30) NOT NULL,
  `avatar_url` varchar(255),
  `location` varchar(50),
  `uuid` binary(36) NOT NULL,
  CONSTRAINT `PK_member` PRIMARY KEY (`id` ASC)
);

-- Table `team`

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `team_lead_id` INT,
  `uuid` binary(36) NOT NULL,
  CONSTRAINT `PK_team` PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_team_member` FOREIGN KEY (`team_lead_id`) REFERENCES `member` (`id`) ON DELETE Restrict ON UPDATE Restrict
);


-- Table `membership`

DROP TABLE IF EXISTS `membership`;

CREATE TABLE `membership` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT DEFAULT NULL,
  `team_id` INT DEFAULT NULL,
  `role_id` INT DEFAULT 1,
  `uuid` binary(36) NOT NULL,
  CONSTRAINT `PK_membership`PRIMARY KEY (`id` ASC),
  CONSTRAINT `FK_membership_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE Restrict ON UPDATE Restrict,
  CONSTRAINT `FK_membership_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE Restrict ON UPDATE RESTRICT,
  CONSTRAINT `FK_membership_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE Restrict ON UPDATE Restrict
);







