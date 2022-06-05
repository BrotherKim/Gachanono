CREATE TABLE `gachanono`.`posts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `writer` VARCHAR(255) NOT NULL,
  `view` BIGINT NOT NULL,
  `created_date` VARCHAR(255) NOT NULL,
  `modified_date` VARCHAR(255) NOT NULL,
  `user_id` BIGINT NOT NULL,
  `good` BIGINT NOT NULL,
  `game_id` VARCHAR(255) NOT NULL,
  `gamename` varchar(255) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `gachanono`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255),
  `nickname` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `created_date` VARCHAR(255) NOT NULL,
  `modified_date` VARCHAR(255) NOT NULL,
  `role` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);

ALTER TABLE posts add FOREIGN KEY(user_id) REFERENCES user(id);

CREATE TABLE `gachanono`.`comments` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `comment` TEXT NOT NULL,
  `created_date` VARCHAR(255) NOT NULL,
  `modified_date` VARCHAR(255) NOT NULL,
  `posts_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE comments add FOREIGN KEY(posts_id) REFERENCES posts(id);
ALTER TABLE comments add FOREIGN KEY(user_id) REFERENCES user(id);

CREATE TABLE `gachanono`.`game` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `gamename` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));
ALTER TABLE posts add FOREIGN KEY(game_id) REFERENCES game(id);

CREATE TABLE `gachanono`.`gacha` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `gachaname` VARCHAR(255) NOT NULL,
  `templatefilename` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `gachanono`.`item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `itemname` VARCHAR(255) NOT NULL,
  `game_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE item add FOREIGN KEY(game_id) REFERENCES game(id);