-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: qlks
-- ------------------------------------------------------
-- Server version	5.7.15-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chi_tiet_dat_phong`
--

DROP TABLE IF EXISTS `chi_tiet_dat_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chi_tiet_dat_phong` (
  `ID_Phong` int(11) NOT NULL,
  `ID_Phieu_Dat_Phong` varchar(45) NOT NULL,
  PRIMARY KEY (`ID_Phong`,`ID_Phieu_Dat_Phong`),
  KEY `fk_Phong_has_Phieu_Dat_Phong_Phieu_Dat_Phong1_idx` (`ID_Phieu_Dat_Phong`),
  KEY `fk_Phong_has_Phieu_Dat_Phong_Phong1_idx` (`ID_Phong`),
  CONSTRAINT `fk_Phong_has_Phieu_Dat_Phong_Phieu_Dat_Phong1` FOREIGN KEY (`ID_Phieu_Dat_Phong`) REFERENCES `phieu_dat_phong` (`ID_Phieu_Dat_Phong`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Phong_has_Phieu_Dat_Phong_Phong1` FOREIGN KEY (`ID_Phong`) REFERENCES `phong` (`ID_Phong`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_dat_phong`
--

LOCK TABLES `chi_tiet_dat_phong` WRITE;
/*!40000 ALTER TABLE `chi_tiet_dat_phong` DISABLE KEYS */;
INSERT INTO `chi_tiet_dat_phong` VALUES (102,'VC2812161343'),(102,'VC2812161359'),(103,'VC2812161343'),(103,'VC2812161354'),(103,'VC2812161356'),(103,'VC2812161359'),(104,'VC2812161354'),(104,'VC2812161356'),(104,'VC2812161358'),(105,'VC2812161354'),(105,'VC2812161356'),(106,'VC2812161347'),(107,'VC2812161356'),(203,'VC2812161348'),(206,'VC2812161348'),(301,'VC2812161345'),(301,'VC2812161359'),(302,'VC2812161345'),(302,'VC2812161359'),(303,'VC2812161345'),(303,'VC2812161354'),(303,'VC2812161359'),(304,'VC2812161345'),(304,'VC2812161354'),(304,'VC2812161359'),(305,'VC2812161354'),(305,'VC2812161359');
/*!40000 ALTER TABLE `chi_tiet_dat_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dich_vu`
--

DROP TABLE IF EXISTS `dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dich_vu` (
  `ID_DichVu` varchar(45) NOT NULL,
  `Ten_Dich_Vu` varchar(50) NOT NULL,
  `Don_Gia` double NOT NULL,
  `Trang_Thai` int(11) NOT NULL,
  PRIMARY KEY (`ID_DichVu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dich_vu`
--

LOCK TABLES `dich_vu` WRITE;
/*!40000 ALTER TABLE `dich_vu` DISABLE KEYS */;
INSERT INTO `dich_vu` VALUES ('DV00001','Thuốc lá',5000,1),('DV00002','Cocacola',10000,1),('DV00003','Pepsi',10000,1),('DV00004','Hoa Quả Dầm',15000,1),('DV00005','Kem',5000,1),('DV00006','Massage',100000,1),('DV00007','Sông Hơi',30000,1),('DV00008','Mực chiên ',90000,1),('DV00009','Bim Bim',5000,1),('DV00010','Nước lọc Danisa',5000,1);
/*!40000 ALTER TABLE `dich_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khach_hang`
--

DROP TABLE IF EXISTS `khach_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `khach_hang` (
  `ID_Khach_Hang` varchar(45) NOT NULL,
  `Ho_Ten` varchar(60) NOT NULL,
  `Ngay_Sinh` date NOT NULL,
  `Gioi_Tinh` varchar(5) NOT NULL,
  `Quoc_Tich` varchar(45) NOT NULL,
  `So_CMT` varchar(45) NOT NULL,
  `So_Đien_Thoai` varchar(20) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `TrangThai` int(11) NOT NULL,
  PRIMARY KEY (`ID_Khach_Hang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khach_hang`
--

LOCK TABLES `khach_hang` WRITE;
/*!40000 ALTER TABLE `khach_hang` DISABLE KEYS */;
INSERT INTO `khach_hang` VALUES ('KH2812161343','Nguyễn Văn Thanh','2000-11-08','Nam','Việt Nam','012345369','0123456789','thanhchich@gmail.com',1),('KH2812161345','Trần Sơn Tùng','1996-11-17','Nam','Việt Nam','135850441','01687037749','tungbxars@gmail.com',1),('KH2812161347','Nguyễn Văn Tuyển','2016-12-14','Nam','Trung Quốc','123896666','013256844','tuyennv@china.com',1),('KH2812161348','Trần Quang Trung','1997-09-16','Nữ','Lào','1222222222','00001223333','trungcc@gggg.com',1),('KH2812161354','Lương Xuân Trường','2000-12-14','Nam','Việt Nam','123588888','0123588888','truonglx@gmail.com',1),('KH2812161356','Bùi Tiến Dũng','1996-12-18','Nữ','Myanma','125565566','001955222','dungbt@google.com',1),('KH2812161358','Adam Smith','2000-12-14','Nam','Mỹ','156666666','0666666666','smith@gmail.com',1),('KH2812161359','Jonh smith','2016-09-07','Nam','Bỉ','30000000555','0169999999','smith@outlook.com',1);
/*!40000 ALTER TABLE `khach_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhan_vien`
--

DROP TABLE IF EXISTS `nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nhan_vien` (
  `ID_Nhan_Vien` varchar(50) NOT NULL,
  `Ho_Ten` varchar(100) NOT NULL,
  `Ngay_Sinh` date NOT NULL,
  `Que_Quan` varchar(45) NOT NULL,
  `Gioi_Tinh` varchar(10) NOT NULL,
  `SoCMT` varchar(45) NOT NULL,
  `So_Dien_Thoai` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Bo_phan_lam_viec` varchar(45) NOT NULL,
  `user_name` varchar(40) DEFAULT NULL,
  `Password` varchar(40) DEFAULT NULL,
  `TrangThai` int(11) NOT NULL,
  PRIMARY KEY (`ID_Nhan_Vien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhan_vien`
--

LOCK TABLES `nhan_vien` WRITE;
/*!40000 ALTER TABLE `nhan_vien` DISABLE KEYS */;
INSERT INTO `nhan_vien` VALUES ('Admin','Quản lý khách sạn','2016-12-17','Admin','Nam','Admin','Admin','Admin','Quản lý khách sạn','admin','admin',1),('NV001','Trần Quan Trung','1988-10-22','Bắc Giang','Nữ','123568888','0165888888','trungtq@soict.com','Lễ tân','NV001','NV001',1),('NV002','Trần Sơn Tùng','1996-11-17','Bình Xuyên','Nam','135850441','01687037749','tungbxars@gmail.com','Lễ tân','NV002','NV002',1),('NV003','Cao Thanh Tùng','1986-11-27','Sơn Tây','Nam','132222564','0165489666','tungct@soict.com','Nhân viên vệ sinh','','',1);
/*!40000 ALTER TABLE `nhan_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieu_dat_phong`
--

DROP TABLE IF EXISTS `phieu_dat_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phieu_dat_phong` (
  `ID_Phieu_Dat_Phong` varchar(45) NOT NULL,
  `Ngay_Lap` date NOT NULL,
  `So_Tien_Tra_Truoc` double NOT NULL,
  `Ngay_Nhan_Phong` date NOT NULL,
  `Ngay_Tra_Phong` date NOT NULL,
  `So_Nguoi_o` int(11) NOT NULL,
  `ID_Khach_Hang_Dat_Phong` varchar(45) NOT NULL,
  `ID_Nhan_Vien_Nhan_Phieu` varchar(50) NOT NULL,
  `Trang_Thai` varchar(45) NOT NULL,
  PRIMARY KEY (`ID_Phieu_Dat_Phong`),
  KEY `fk_Phieu_Dat_Phong_Nhan_Vien1_idx` (`ID_Nhan_Vien_Nhan_Phieu`),
  KEY `fk_Phieu_Dat_Phong_Khach_Hang_Dat_Phong1_idx` (`ID_Khach_Hang_Dat_Phong`),
  CONSTRAINT `fk_Khach_So_Huu_Phieu` FOREIGN KEY (`ID_Khach_Hang_Dat_Phong`) REFERENCES `khach_hang` (`ID_Khach_Hang`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Nhan_Vien_Qly_Phieudatphong` FOREIGN KEY (`ID_Nhan_Vien_Nhan_Phieu`) REFERENCES `nhan_vien` (`ID_Nhan_Vien`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieu_dat_phong`
--

LOCK TABLES `phieu_dat_phong` WRITE;
/*!40000 ALTER TABLE `phieu_dat_phong` DISABLE KEYS */;
INSERT INTO `phieu_dat_phong` VALUES ('VC2812161343','2016-12-28',100000,'2017-12-01','2017-12-05',2,'KH2812161343','Admin','Đã Xóa'),('VC2812161345','2016-12-28',500000,'2017-12-01','2017-12-02',5,'KH2812161345','NV001','Đã Xóa'),('VC2812161347','2016-12-28',300000,'2016-12-28','2017-01-17',2,'KH2812161347','Admin','Đã Xóa'),('VC2812161348','2016-12-28',300000,'2017-01-20','2017-01-30',5,'KH2812161348','NV001','Đã Xóa'),('VC2812161354','2016-12-28',200000,'2017-01-10','2017-01-12',2,'KH2812161354','NV001','Đã Xóa'),('VC2812161356','2016-12-28',300000,'2016-12-09','2016-12-16',2,'KH2812161356','NV001','Đã Xóa'),('VC2812161358','2016-12-28',100000,'2016-12-30','2016-12-31',2,'KH2812161358','NV001','Đang đặt'),('VC2812161359','2016-12-28',100000,'2016-12-30','2016-12-31',2,'KH2812161359','NV001','Đang đặt');
/*!40000 ALTER TABLE `phieu_dat_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phong`
--

DROP TABLE IF EXISTS `phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phong` (
  `ID_Phong` int(11) NOT NULL,
  `Loai_Phong` varchar(45) NOT NULL,
  `Tinh_Trang_Phong` varchar(45) NOT NULL,
  `So_Khach_Toi_Da` int(11) NOT NULL,
  `Gia_Phong` double NOT NULL,
  PRIMARY KEY (`ID_Phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phong`
--

LOCK TABLES `phong` WRITE;
/*!40000 ALTER TABLE `phong` DISABLE KEYS */;
INSERT INTO `phong` VALUES (101,'Phòng đơn','Đang sửa chữa',1,100000),(102,'Phòng đôi','Đang đặt',2,150000),(103,'Phòng VIP','Đang đặt',4,500000),(104,'Phòng hội nghị','Đang đặt',10,600000),(105,'Phòng đôi','Đang trống',2,150000),(106,'Phòng VIP','Đang trống',2,200000),(107,'Phòng đôi','Đang trống',2,200000),(108,'Phòng VIP','Đang trống',2,300000),(201,'Phòng đơn','Đang sửa chữa',1,200000),(202,'Phòng VIP','Đang trống',2,200000),(203,'Phòng đôi','Đang trống',2,300000),(204,'Phòng đôi','Đang sửa chữa',4,300000),(205,'Phòng VIP','Đang trống',4,600000),(206,'Phòng đôi','Đang trống',2,300000),(301,'Phòng hội nghị','Đang đặt',10,600000),(302,'Phòng hội nghị','Đang đặt',20,1000000),(303,'Phòng VIP','Đang đặt',4,400000),(304,'Phòng VIP','Đang đặt',2,300000),(305,'Phòng đôi','Đang đặt',2,200000),(306,'Phòng đôi','Đang trống',2,300000);
/*!40000 ALTER TABLE `phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phong_su_dung_dich_vu`
--

DROP TABLE IF EXISTS `phong_su_dung_dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phong_su_dung_dich_vu` (
  `Ngay_Su_Dung` date NOT NULL,
  `So_Luong` int(11) NOT NULL,
  `ID_Phieu_Dat_Phong` varchar(45) NOT NULL,
  `ID_DichVu` varchar(45) NOT NULL,
  PRIMARY KEY (`ID_DichVu`,`ID_Phieu_Dat_Phong`),
  KEY `fk_Phong_Su_Dung_Dich_Vu_Dich_Vu1_idx` (`ID_DichVu`),
  KEY `Phieu_Dat_Phong_idx` (`ID_Phieu_Dat_Phong`),
  CONSTRAINT `Ma_Phieu_Dat_Phong` FOREIGN KEY (`ID_Phieu_Dat_Phong`) REFERENCES `phieu_dat_phong` (`ID_Phieu_Dat_Phong`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Phong_Su_Dung_Dich_Vu_Dich_Vu1` FOREIGN KEY (`ID_DichVu`) REFERENCES `dich_vu` (`ID_DichVu`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phong_su_dung_dich_vu`
--

LOCK TABLES `phong_su_dung_dich_vu` WRITE;
/*!40000 ALTER TABLE `phong_su_dung_dich_vu` DISABLE KEYS */;
INSERT INTO `phong_su_dung_dich_vu` VALUES ('2016-12-28',5,'VC2812161343','DV00001'),('2016-12-28',5,'VC2812161348','DV00001'),('2016-12-28',5,'VC2812161348','DV00002'),('2016-12-28',6,'VC2812161356','DV00003'),('2016-12-28',6,'VC2812161356','DV00006'),('2016-12-28',5,'VC2812161348','DV00007'),('2016-12-28',5,'VC2812161356','DV00007'),('2016-12-28',5,'VC2812161356','DV00008');
/*!40000 ALTER TABLE `phong_su_dung_dich_vu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-28 14:00:37
