-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: yummy
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `address` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'南京大学鼓楼校区'),(2,'南京市新街口德基广场'),(3,'东南大学四牌楼校区'),(4,'南京市玄武区艾尚天地'),(5,'南京市玄武区玄武饭店'),(6,'南京师范大学随园校区'),(7,'南京市鼓楼区金润发超市'),(8,'南京市玄武区凯瑟琳广场'),(9,'南京市玄武区鸡鸣寺'),(10,'南京市夫子庙购物中心');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_address`
--

DROP TABLE IF EXISTS `member_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `member_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  `default_state` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `aid` (`aid`),
  KEY `mid` (`mid`),
  CONSTRAINT `member_address_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `address` (`aid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `member_address_ibfk_2` FOREIGN KEY (`mid`) REFERENCES `members` (`mid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_address`
--

LOCK TABLES `member_address` WRITE;
/*!40000 ALTER TABLE `member_address` DISABLE KEYS */;
INSERT INTO `member_address` VALUES (7,1,2,0),(8,3,2,0),(9,4,2,0),(49,1,1,1),(52,2,1,0),(53,3,1,0),(54,4,1,0),(55,9,1,0),(56,7,1,0),(57,1,7,1),(58,7,7,0),(59,1,9,0);
/*!40000 ALTER TABLE `member_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `members` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `level` int(11) DEFAULT '1',
  `balance` double(10,2) DEFAULT '0.00',
  `password` varchar(20) NOT NULL,
  `state` int(11) NOT NULL DEFAULT '0',
  `experience` int(11) DEFAULT '0',
  `code` varchar(32) DEFAULT NULL,
  `total_cost` double DEFAULT '0',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (1,'109876127@qq.com','15720981238','阿尔托莉雅',1,13503.15,'123456',1,41,'a6d91f70cba645d68dcc659f5e82b2c0',209.6),(2,'2263909098@qq.com','15720813992','刘苇',1,200.00,'123456',0,0,'a6d91f70cba645d68dcc659f5e82b2c7',0),(3,'2463909105@qq.com','15798731098','大刘',1,300.00,'161250078',0,0,'6fc190c0c8ea4f9da0f71bfd8160eea5',0),(4,'2439812861@qq.com','13787391098','测试用户',1,500.00,'123456',0,0,'7f297d770aca4c3a9cbdcecf5de95fbd',0),(7,'3463909185@qq.com','15720813992','刘苇',1,77.40,'2463909185as',1,4,'2d18d2b71eb64297953e50d3d2c8973f',22.6),(8,'3429812861@qq.com','15798731098','张三',1,0.00,'2463909185as',1,0,'1e11c7a93cab437fbbb72cec76f4ee44',0),(9,'2463909185@qq.com','15798731098','刘苇',1,0.00,'2463909185as',1,0,'60b02d6e63614369a5ad7876172009b3',0);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_package`
--

DROP TABLE IF EXISTS `order_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oid` varchar(20) NOT NULL,
  `pid` int(11) NOT NULL,
  `num` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `oid` (`oid`),
  KEY `pid` (`pid`),
  CONSTRAINT `order_package_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_package_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `packages` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_package`
--

LOCK TABLES `order_package` WRITE;
/*!40000 ALTER TABLE `order_package` DISABLE KEYS */;
INSERT INTO `order_package` VALUES (23,'20190314092249304130',1,1),(24,'20190314092710489708',1,1),(25,'20190314093020223972',1,1),(26,'20190314093020223972',2,1),(27,'20190315120508405087',2,1),(28,'20190316115118751055',1,1),(29,'20190316030038166112',1,1),(30,'20190318103747459114',1,1),(31,'20190318071737121599',1,1),(32,'20190318072249562756',1,1),(33,'20190319035420025415',1,1),(34,'20190319041328168537',1,1),(35,'20190327020919951689',9,1);
/*!40000 ALTER TABLE `order_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_single`
--

DROP TABLE IF EXISTS `order_single`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_single` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oid` varchar(20) NOT NULL,
  `sid` int(11) NOT NULL,
  `num` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `oid` (`oid`),
  KEY `sid` (`sid`),
  CONSTRAINT `order_single_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_single_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `singles` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_single`
--

LOCK TABLES `order_single` WRITE;
/*!40000 ALTER TABLE `order_single` DISABLE KEYS */;
INSERT INTO `order_single` VALUES (36,'20190314092249304130',1,1),(37,'20190314092249304130',3,1),(38,'20190314092710489708',1,1),(39,'20190314092710489708',3,1),(40,'20190314093020223972',1,1),(41,'20190314093020223972',3,1),(42,'20190315120508405087',4,1),(43,'20190315125746378684',1,8),(44,'20190316115118751055',1,1),(45,'20190316030038166112',1,1),(46,'20190316030038166112',4,1),(47,'20190318103747459114',7,1),(48,'20190318071737121599',1,1),(49,'20190318072237807409',1,1),(50,'20190318072237807409',4,1),(51,'20190318072249562756',1,1),(52,'20190319035420025415',1,1),(53,'20190319041254128997',20,1),(54,'20190319041328168537',1,1),(55,'20190319041338641362',1,1),(56,'20190319041338641362',3,1),(57,'20190327020919951689',20,1);
/*!40000 ALTER TABLE `order_single` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `oid` varchar(20) NOT NULL,
  `mid` int(11) NOT NULL,
  `rid` varchar(7) NOT NULL,
  `cost` double(10,2) NOT NULL,
  `note` varchar(100) DEFAULT NULL,
  `state` enum('支付中','已取消','配送中','已支付','已完成') DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `arrive_time` datetime DEFAULT NULL,
  `order_time` datetime NOT NULL,
  `delivery_time` datetime DEFAULT NULL,
  `admin_money` double(10,2) DEFAULT '0.00',
  `res_money` double(10,2) DEFAULT '0.00',
  `member_money` double(10,2) DEFAULT '0.00',
  PRIMARY KEY (`oid`),
  KEY `mid` (`mid`),
  KEY `rid` (`rid`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `members` (`mid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `restaurants` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('20190314092249304130',1,'1234567',41.00,'无','已取消','南京大学鼓楼校区',NULL,'2019-03-14 21:22:49','2019-03-16 14:59:22',0.00,0.00,0.00),('20190314092710489708',1,'1234567',41.00,'无','已完成','南京市新街口德基广场','2019-03-18 20:19:44','2019-03-14 21:27:10','2019-03-18 19:23:17',4.10,36.90,41.00),('20190314093020223972',1,'1234567',66.00,'无','已完成','南京大学鼓楼校区','2019-03-14 21:54:56','2019-03-14 21:30:20',NULL,6.60,59.40,66.00),('20190315120508405087',1,'1234567',21.50,'无','已取消','南京大学鼓楼校区',NULL,'2019-03-15 12:05:08',NULL,0.00,0.00,0.00),('20190315125746378684',1,'1234567',81.00,'无','已完成','南京大学鼓楼校区','2019-03-15 12:58:16','2019-03-15 12:57:46','2019-03-15 12:58:16',8.10,72.90,81.00),('20190316030038166112',1,'1234567',33.50,'无','已取消','南京大学鼓楼校区',NULL,'2019-03-16 15:00:38','2019-03-16 19:23:25',1.68,15.07,16.75),('20190316115118751055',1,'1234567',22.00,'无','已取消','南京大学鼓楼校区',NULL,'2019-03-16 11:51:19','2019-03-18 20:19:16',0.00,0.00,0.00),('20190318071737121599',7,'1234567',22.60,'无','已完成','南京市鼓楼区金润发超市','2019-03-18 19:23:51','2019-03-18 19:17:37','2019-03-18 19:23:12',2.26,20.34,22.60),('20190318072237807409',7,'1234567',14.10,'无','已取消','南京大学鼓楼校区',NULL,'2019-03-18 19:22:38','2019-03-18 19:23:30',0.00,0.00,0.00),('20190318072249562756',7,'1234567',22.60,'无','支付中','南京大学鼓楼校区',NULL,'2019-03-18 19:22:50',NULL,0.00,0.00,0.00),('20190318103747459114',1,'1234567',25.00,'无','已取消','南京大学鼓楼校区',NULL,'2019-03-18 10:37:47',NULL,0.00,0.00,0.00),('20190319035420025415',1,'1234567',22.60,'无','已取消','南京市玄武区艾尚天地',NULL,'2019-03-19 15:54:20',NULL,0.00,0.00,0.00),('20190319041254128997',1,'9f433b6',10.00,'无','已取消','南京市玄武区艾尚天地',NULL,'2019-03-19 16:12:54',NULL,0.00,0.00,0.00),('20190319041328168537',1,'1234567',22.60,'无','已取消','南京市玄武区艾尚天地',NULL,'2019-03-19 16:13:28',NULL,0.00,0.00,0.00),('20190319041338641362',1,'1234567',21.60,'无','已完成','南京市玄武区艾尚天地','2019-03-19 16:14:32','2019-03-19 16:13:39','2019-03-19 16:14:20',2.16,19.44,21.60),('20190327020919951689',1,'9f433b6',19.00,'无','已取消','南京大学鼓楼校区',NULL,'2019-03-27 14:09:20',NULL,0.00,0.00,0.00);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package_single`
--

DROP TABLE IF EXISTS `package_single`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `package_single` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `num` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  KEY `sid` (`sid`),
  CONSTRAINT `package_single_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `packages` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `package_single_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `singles` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package_single`
--

LOCK TABLES `package_single` WRITE;
/*!40000 ALTER TABLE `package_single` DISABLE KEYS */;
INSERT INTO `package_single` VALUES (3,2,3,1),(4,2,1,1),(5,3,7,1),(6,3,1,1),(7,3,5,1),(8,4,2,2),(9,4,6,1),(10,6,2,1),(11,6,5,2),(12,6,4,1),(15,7,7,1),(16,7,1,1),(17,7,4,1),(18,8,7,1),(19,8,1,1),(20,8,2,1),(21,8,4,1),(22,1,3,1),(23,1,4,1),(24,9,20,12);
/*!40000 ALTER TABLE `package_single` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `packages`
--

DROP TABLE IF EXISTS `packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `packages` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `rid` varchar(7) NOT NULL,
  `name` varchar(20) NOT NULL,
  `cost` double(5,2) NOT NULL,
  `type` varchar(10) NOT NULL,
  `num` int(11) NOT NULL,
  `discount` double DEFAULT NULL,
  `end_time` datetime NOT NULL,
  `start_time` datetime NOT NULL,
  `pic_path` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `rid` (`rid`),
  CONSTRAINT `packages_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `restaurants` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packages`
--

LOCK TABLES `packages` WRITE;
/*!40000 ALTER TABLE `packages` DISABLE KEYS */;
INSERT INTO `packages` VALUES (1,'1234567','香辣鸡腿堡套餐1',25.00,'汉堡炸鸡',45,100,'2019-08-10 12:02:00','2019-03-19 12:02:00',NULL),(2,'1234567','香辣鸡腿汉堡套餐2',25.00,'炸鸡汉堡',50,100,'2019-08-10 12:03:29','2019-03-10 12:03:25',NULL),(3,'1234567','小食拼盘',30.00,'小食',100,100,'2019-03-31 12:00:00','2019-03-18 12:00:00',NULL),(4,'1234567','巨无霸套餐',50.00,'汉堡',50,100,'2019-03-31 00:00:00','2019-03-20 12:00:00',NULL),(6,'1234567','嫩牛五方双人餐',50.00,'汉堡套餐',100,100,'2019-04-10 21:00:00','2019-03-19 10:00:00',NULL),(7,'1234567','各种鸡套餐',50.00,'炸鸡',50,90,'2019-03-29 12:00:00','2019-03-22 12:00:00',NULL),(8,'1234567','全家桶',70.00,'炸鸡小食',100,100,'2019-03-31 12:00:00','2019-03-19 12:00:00',NULL),(9,'9f433b6','炸鸡套餐',19.00,'炸鸡套餐',99,100,'2019-03-31 12:00:00','2019-03-19 16:13:00',NULL);
/*!40000 ALTER TABLE `packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reduction`
--

DROP TABLE IF EXISTS `reduction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reduction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` varchar(7) NOT NULL,
  `full_cost` double NOT NULL,
  `reduce_cost` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rid` (`rid`),
  CONSTRAINT `reduction_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `restaurants` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reduction`
--

LOCK TABLES `reduction` WRITE;
/*!40000 ALTER TABLE `reduction` DISABLE KEYS */;
INSERT INTO `reduction` VALUES (19,'9f433b6',20,10),(58,'1234567',20,10),(59,'1234567',30,15),(60,'1234567',40,20);
/*!40000 ALTER TABLE `reduction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurants`
--

DROP TABLE IF EXISTS `restaurants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `restaurants` (
  `rid` varchar(7) NOT NULL,
  `password` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `type` varchar(10) NOT NULL,
  `aid` int(11) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `human_cost` double(5,2) NOT NULL,
  `min_cost` double(5,2) NOT NULL,
  `total_money` double(10,2) DEFAULT '0.00',
  `pic_path` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rid`),
  KEY `aid` (`aid`),
  CONSTRAINT `restaurants_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `address` (`aid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurants`
--

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;
INSERT INTO `restaurants` VALUES ('1234567','123456','肯德基','综合店面',1,'18907183249','测试',1,3.00,10.00,311.76,NULL),('2136790','89760','麦当劳','快餐便当',2,'18763909873','麦当劳一家',1,0.00,0.00,0.00,NULL),('9f433b6','123456','新餐厅','快餐便当',1,'15798731098','无',1,0.00,0.00,0.00,NULL),('axkddpp','123','兰州拉面','综合店面',1,'15798731098','123',1,0.00,0.00,0.00,NULL),('vkedoa0','123456','王境泽炒饭','综合店面',4,'15798731098','真香',1,0.00,0.00,0.00,NULL),('wp9gwnm','123456','开心哈利','炸鸡汉堡',1,'15798731098','就是开心',1,0.00,0.00,0.00,NULL);
/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `singles`
--

DROP TABLE IF EXISTS `singles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `singles` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `rid` varchar(7) NOT NULL,
  `name` varchar(20) NOT NULL,
  `cost` double(5,2) NOT NULL,
  `type` varchar(10) NOT NULL,
  `num` int(11) NOT NULL,
  `discount` double DEFAULT NULL,
  `end_time` datetime NOT NULL,
  `start_time` datetime NOT NULL,
  `pic_path` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `rid` (`rid`),
  CONSTRAINT `singles_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `restaurants` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `singles`
--

LOCK TABLES `singles` WRITE;
/*!40000 ALTER TABLE `singles` DISABLE KEYS */;
INSERT INTO `singles` VALUES (1,'1234567','吮指原味鸡',12.00,'炸鸡',93,80,'2019-06-10 09:11:00','2019-03-10 02:11:00',NULL),(2,'1234567','醇香土豆泥',6.50,'小食',100,100,'2019-06-10 02:14:35','2019-05-10 02:14:47',NULL),(3,'1234567','香辣鸡腿堡',19.00,'汉堡',99,100,'2019-07-10 07:18:15','2019-03-10 07:18:42',NULL),(4,'1234567','黄金鸡块（5块装）',11.50,'小食',98,100,'2019-06-10 10:20:11','2019-03-10 10:20:17',NULL),(5,'1234567','嫩牛五方',10.00,'小食',100,100,'2019-03-10 07:36:54','2019-01-10 07:37:04',NULL),(6,'1234567','巨无霸汉堡',20.00,'汉堡',100,100,'2019-05-10 07:37:46','2019-09-10 07:37:50',NULL),(7,'1234567','劲爆鸡米花',12.00,'小食',99,100,'2019-03-30 12:00:00','2019-03-17 12:00:00',NULL),(8,'vkedoa0','真香炒饭',12.00,'炒饭',50,100,'2019-03-31 12:00:00','2019-03-18 12:00:00',NULL),(9,'vkedoa0','紫菜汤',5.00,'热汤',50,80,'2019-03-31 12:00:00','2019-03-18 12:00:00',NULL),(11,'vkedoa0','可乐',4.00,'饮料',50,100,'2019-03-31 12:00:00','2019-03-18 12:00:00',NULL),(12,'wp9gwnm','无骨鸡排',5.00,'炸鸡',20,100,'2019-03-31 12:00:00','2019-03-18 12:00:00',NULL),(18,'wp9gwnm','香酥鸡块',7.00,'炸鸡',100,100,'2019-03-21 12:00:00','2019-03-18 12:00:00',NULL),(19,'wp9gwnm','蛋挞',6.00,'糕点',100,100,'2019-03-31 12:00:00','2019-03-19 12:00:00',NULL),(20,'9f433b6','炸鸡',10.00,'炸鸡',98,100,'2019-03-30 12:00:00','2019-03-19 16:12:00',NULL);
/*!40000 ALTER TABLE `singles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-26 15:18:20
