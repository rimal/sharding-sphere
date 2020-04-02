create schema if not exists speedy_shard_1;
create schema if not exists speedy_shard_2;
create schema if not exists speedy_shard_3;
create schema if not exists speedy_shard_4;


CREATE TABLE IF NOT EXISTS `customer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` bigint(20) unsigned NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `customer_client_idx` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `ticket` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` bigint(20) unsigned NOT NULL,
  `customer_id` bigint(20) unsigned NOT NULL,
  `ticket_status_id` bigint(20) unsigned NOT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_ticket_customer1_idx` (`customer_id`),
  KEY `fk_ticket_ticket_status1_idx` (`ticket_status_id`),
  KEY `ticket_client_idx` (`client_id`),
  KEY `create_date_idx` (`create_date`),
  KEY `update_date_idx` (`update_date`),
  CONSTRAINT `_fk_ticket_customer1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `ticket`
add column  `conversation_hash` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
add column  `ticket_hash` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
add column  `ext_ticket_id` varchar(120) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
add column  `crm_ticket_id` varchar(120) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
add column  `crm_ticket_channel_id` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
add column  `channel` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
add column  `prev_ticket_id` bigint(20) unsigned DEFAULT NULL,
add column  `assigned_user_id` bigint(20) unsigned DEFAULT NULL,
add column  `inferred_item` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
add column  `bot_version_id` bigint(20) unsigned DEFAULT NULL,
add column  `node_group_id` bigint(20) unsigned DEFAULT NULL,
add column  `extra_info` mediumtext COLLATE utf8mb4_unicode_ci,
add column  `rating` decimal(3,1) DEFAULT NULL,
add column  `feedback` mediumtext COLLATE utf8mb4_unicode_ci,
add column  `comments` mediumtext COLLATE utf8mb4_unicode_ci,
add column  `offline_log_id` bigint(20) unsigned DEFAULT NULL,
add column  `landing_url` text COLLATE utf8mb4_unicode_ci,
add column  `traffic_tracking_id` bigint(20) unsigned DEFAULT NULL;