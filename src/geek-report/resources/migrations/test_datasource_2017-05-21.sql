# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: localhost (MySQL 5.7.18)
# Database: test_datasource
# Generation Time: 2017-05-21 05:51:52 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table score
# ------------------------------------------------------------

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `student` varchar(32) DEFAULT NULL,
  `subject` varchar(24) DEFAULT NULL,
  `score` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;

INSERT INTO `score` (`id`, `student`, `subject`, `score`)
VALUES
	(1,'唐炯','高数',99),
	(2,'唐炯','物理',94),
	(3,'唐炯','线代',89),
	(4,'唐炯','离散',92),
	(5,'吴思瀚','物理',98),
	(6,'吴思瀚','离散',94),
	(7,'吴思瀚','线代',90),
	(8,'吴思瀚','高数',98),
	(9,'杨波','高数',92),
	(10,'杨波','物理',99),
	(11,'杨波','线代',87),
	(12,'杨波','离散',93);

/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
