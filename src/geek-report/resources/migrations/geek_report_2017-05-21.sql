# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: localhost (MySQL 5.7.18)
# Database: geek_report
# Generation Time: 2017-05-21 05:32:17 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table datasource
# ------------------------------------------------------------

DROP TABLE IF EXISTS `datasource`;

CREATE TABLE `datasource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `dbname` varchar(30) NOT NULL,
  `driver_path` varchar(300) NOT NULL,
  `config` text,
  `project_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `datasource` WRITE;
/*!40000 ALTER TABLE `datasource` DISABLE KEYS */;

INSERT INTO `datasource` (`id`, `name`, `dbname`, `driver_path`, `config`, `project_id`, `created_at`)
VALUES
	(16,'LocalMySQL','mysql','embeded','{     \"host\": \"localhost\",     \"port\": 3306,     \"db\": \"test_datasource\",     \"user\": \"root\",     \"password\": \"123456\" }',11,'2017-05-15 23:49:39'),
	(17,'LocalPostgreSQL','postgres','embedded','{     \"host\": \"localhost\",     \"port\": 5432,     \"db\": \"test_datawarehose\",     \"user\": \"postgres\",     \"password\": \"123456\" }',11,'2017-05-16 01:48:03');

/*!40000 ALTER TABLE `datasource` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table paragraph
# ------------------------------------------------------------

DROP TABLE IF EXISTS `paragraph`;

CREATE TABLE `paragraph` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(120) NOT NULL DEFAULT '',
  `project_id` int(11) NOT NULL,
  `width` int(11) unsigned DEFAULT '24',
  `height` int(11) unsigned DEFAULT '1',
  `index` int(11) unsigned NOT NULL DEFAULT '1',
  `default_visual_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `paragraph` WRITE;
/*!40000 ALTER TABLE `paragraph` DISABLE KEYS */;

INSERT INTO `paragraph` (`id`, `title`, `project_id`, `width`, `height`, `index`, `default_visual_id`, `created_at`)
VALUES
	(1,'成绩雷达图',11,12,1,1,1,'2017-05-16 10:17:19'),
	(3,'水果销量',11,12,1,2,3,'2017-05-17 11:05:05'),
	(4,'成绩表',11,24,1,3,4,'2017-05-17 11:20:24'),
	(5,'销量表',11,12,1,4,5,'2017-05-17 22:26:12'),
	(6,'销量Polar Area',11,12,1,5,7,'2017-05-20 10:08:03');

/*!40000 ALTER TABLE `paragraph` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table project
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(120) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;

INSERT INTO `project` (`id`, `title`, `created_at`, `created_by`)
VALUES
	(11,'测试项目','2017-05-15 23:49:39',1);

/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table query
# ------------------------------------------------------------

DROP TABLE IF EXISTS `query`;

CREATE TABLE `query` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `paragraph_id` int(11) NOT NULL,
  `datasource_id` int(11) NOT NULL,
  `lang` varchar(24) DEFAULT 'sql',
  `raw` text NOT NULL,
  `max_age` int(10) unsigned DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `query` WRITE;
/*!40000 ALTER TABLE `query` DISABLE KEYS */;

INSERT INTO `query` (`id`, `paragraph_id`, `datasource_id`, `lang`, `raw`, `max_age`, `created_at`)
VALUES
	(1,1,16,'sql','select * from score',600,'2017-05-18 10:11:42'),
	(2,3,17,'sql','select * from test',0,'2017-05-20 09:44:42'),
	(4,4,16,'sql','select * from score',0,'2017-05-20 09:57:19'),
	(5,5,17,'sql','select * from test',0,'2017-05-20 10:06:47'),
	(6,6,17,'sql','select * from test',0,'2017-05-20 10:08:20');

/*!40000 ALTER TABLE `query` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table visualization
# ------------------------------------------------------------

DROP TABLE IF EXISTS `visualization`;

CREATE TABLE `visualization` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `query_id` int(11) NOT NULL,
  `title` varchar(30) DEFAULT NULL,
  `chart_type` varchar(30) DEFAULT 'table',
  `group_column` varchar(120) DEFAULT '',
  `observe_column` varchar(120) DEFAULT NULL,
  `measure_column` varchar(120) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `visualization` WRITE;
/*!40000 ALTER TABLE `visualization` DISABLE KEYS */;

INSERT INTO `visualization` (`id`, `query_id`, `title`, `chart_type`, `group_column`, `observe_column`, `measure_column`, `created_at`)
VALUES
	(1,1,'haha','radar','student','subject','score','2017-05-20 06:23:44'),
	(2,1,'','line','student','subject','score','2017-05-20 07:59:27'),
	(3,2,'销量比例','pie','','product','sales','2017-05-20 09:45:42'),
	(4,4,'结果表','table','','','','2017-05-20 09:58:41'),
	(5,5,'销量','table','','','','2017-05-20 10:06:50'),
	(6,6,'','table','','','','2017-05-20 10:08:37'),
	(7,6,'','polarArea','','product','sales','2017-05-20 10:09:18');

/*!40000 ALTER TABLE `visualization` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
