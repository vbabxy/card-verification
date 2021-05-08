--
-- Table structure for table `card_details`
--

DROP TABLE IF EXISTS `card_verification`;

CREATE TABLE `card_verification` (
  `id` varchar(45) NOT NULL,
  `card_number` varchar(20) DEFAULT NULL,
  `number_of_hits` bigint(19) DEFAULT NULL,
  `card_type` varchar(20) DEFAULT NULL,
  `card_scheme` varchar(45) DEFAULT NULL,
  `bank_name` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(45) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `version` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_number_UNIQUE` (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


