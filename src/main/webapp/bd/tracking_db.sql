-- MySQL dump 10.13  Distrib 5.5.34, for Win32 (x86)
--
-- Host: localhost    Database: tracking_db
-- ------------------------------------------------------
-- Server version	5.5.34

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
-- Table structure for table `activation_token`
--

DROP TABLE IF EXISTS `activation_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activation_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `users_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_activation_token_users1_idx` (`users_id`),
  CONSTRAINT `fk_activation_token_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activation_token`
--

LOCK TABLES `activation_token` WRITE;
/*!40000 ALTER TABLE `activation_token` DISABLE KEYS */;
INSERT INTO `activation_token` VALUES (1,'fa0e4664-85d2-42d8-b025-16acb271c1f8',0,5),(2,'ce8918f3-6d62-4486-831e-70d54ec2384a',0,6),(9,'b38d240e-cc7d-4d16-84d1-bca55bc40fb8',0,13);
/*!40000 ALTER TABLE `activation_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_authorities_authority_idx` (`authority`),
  KEY `fk_authorities_users1_idx` (`username`),
  CONSTRAINT `fk_authorities_authority` FOREIGN KEY (`authority`) REFERENCES `authoritys` (`authority`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_authorities_users1` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES (1,'ROLE_ADMIN','admin'),(2,'ROLE_CLIENT','wrivas'),(3,'ROLE_CLIENT','anza12'),(4,'ROLE_USER','admin'),(5,'ROLE_CLIENT','magudelo'),(6,'ROLE_CLIENT','wrivas1'),(13,'ROLE_CLIENT','jormar');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authoritys`
--

DROP TABLE IF EXISTS `authoritys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authoritys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(50) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `authority_UNIQUE` (`authority`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authoritys`
--

LOCK TABLES `authoritys` WRITE;
/*!40000 ALTER TABLE `authoritys` DISABLE KEYS */;
INSERT INTO `authoritys` VALUES (1,'ROLE_ADMIN','ROLE_ADMIN'),(2,'ROLE_USER','ROLE_USER'),(3,'ROLE_CLIENT','ROLE_CLIENT');
/*!40000 ALTER TABLE `authoritys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria_detalle`
--

DROP TABLE IF EXISTS `categoria_detalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria_detalle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_detalle`
--

LOCK TABLES `categoria_detalle` WRITE;
/*!40000 ALTER TABLE `categoria_detalle` DISABLE KEYS */;
INSERT INTO `categoria_detalle` VALUES (2,'Deportivo'),(1,'Electrónicos');
/*!40000 ALTER TABLE `categoria_detalle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contenedor`
--

DROP TABLE IF EXISTS `contenedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contenedor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(100) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `estatus` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `codigo_UNIQUE` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contenedor`
--

LOCK TABLES `contenedor` WRITE;
/*!40000 ALTER TABLE `contenedor` DISABLE KEYS */;
INSERT INTO `contenedor` VALUES (1,'123','123Ropa','CONFIRMADO_ALMACEN_DESTINO','2014-04-28 13:36:02','2014-04-28 13:37:34');
/*!40000 ALTER TABLE `contenedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `countries` (
  `iso_alpha2` varchar(2) NOT NULL,
  `name` varchar(200) NOT NULL,
  `iso_alpha3` varchar(3) NOT NULL,
  `iso_numeric` int(11) NOT NULL,
  `currency_code` varchar(3) NOT NULL,
  `currency_name` varchar(32) DEFAULT NULL,
  `currrency_symbol` varchar(3) DEFAULT NULL,
  `flag` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`iso_alpha2`),
  UNIQUE KEY `iso_alpha2_UNIQUE` (`iso_alpha2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries`
--

LOCK TABLES `countries` WRITE;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` VALUES ('AD','Andorra','AND',20,'EUR','Euro','€','AD.png'),('AE','United Arab Emirates','ARE',784,'AED','Dirham',NULL,'AE.png'),('AF','Afghanistan','AFG',4,'AFN','Afghani','؋','AF.png'),('AG','Antigua and Barbuda','ATG',28,'XCD','Dollar','$','AG.png'),('AI','Anguilla','AIA',660,'XCD','Dollar','$','AI.png'),('AL','Albania','ALB',8,'ALL','Lek','Lek','AL.png'),('AM','Armenia','ARM',51,'AMD','Dram',NULL,'AM.png'),('AN','Netherlands Antilles','ANT',530,'ANG','Guilder','ƒ','AN.png'),('AO','Angola','AGO',24,'AOA','Kwanza','Kz','AO.png'),('AQ','Antarctica','ATA',10,'','',NULL,'AQ.png'),('AR','Argentina','ARG',32,'ARS','Peso','$','AR.png'),('AS','American Samoa','ASM',16,'USD','Dollar','$','AS.png'),('AT','Austria','AUT',40,'EUR','Euro','€','AT.png'),('AU','Australia','AUS',36,'AUD','Dollar','$','AU.png'),('AW','Aruba','ABW',533,'AWG','Guilder','ƒ','AW.png'),('AZ','Azerbaijan','AZE',31,'AZN','Manat','ман','AZ.png'),('BA','Bosnia and Herzegovina','BIH',70,'BAM','Marka','KM','BA.png'),('BB','Barbados','BRB',52,'BBD','Dollar','$','BB.png'),('BD','Bangladesh','BGD',50,'BDT','Taka',NULL,'BD.png'),('BE','Belgium','BEL',56,'EUR','Euro','€','BE.png'),('BF','Burkina Faso','BFA',854,'XOF','Franc',NULL,'BF.png'),('BG','Bulgaria','BGR',100,'BGN','Lev','лв','BG.png'),('BH','Bahrain','BHR',48,'BHD','Dinar',NULL,'BH.png'),('BI','Burundi','BDI',108,'BIF','Franc',NULL,'BI.png'),('BJ','Benin','BEN',204,'XOF','Franc',NULL,'BJ.png'),('BM','Bermuda','BMU',60,'BMD','Dollar','$','BM.png'),('BN','Brunei','BRN',96,'BND','Dollar','$','BN.png'),('BO','Bolivia','BOL',68,'BOB','Boliviano','$b','BO.png'),('BR','Brazil','BRA',76,'BRL','Real','R$','BR.png'),('BS','Bahamas','BHS',44,'BSD','Dollar','$','BS.png'),('BT','Bhutan','BTN',64,'BTN','Ngultrum',NULL,'BT.png'),('BV','Bouvet Island','BVT',74,'NOK','Krone','kr','BV.png'),('BW','Botswana','BWA',72,'BWP','Pula','P','BW.png'),('BY','Belarus','BLR',112,'BYR','Ruble','p.','BY.png'),('BZ','Belize','BLZ',84,'BZD','Dollar','BZ$','BZ.png'),('CA','Canada','CAN',124,'CAD','Dollar','$','CA.png'),('CC','Cocos Islands','CCK',166,'AUD','Dollar','$','CC.png'),('CD','Democratic Republic of the Congo','COD',180,'CDF','Franc',NULL,'CD.png'),('CF','Central African Republic','CAF',140,'XAF','Franc','FCF','CF.png'),('CG','Republic of the Congo','COG',178,'XAF','Franc','FCF','CG.png'),('CH','Switzerland','CHE',756,'CHF','Franc','CHF','CH.png'),('CI','Ivory Coast','CIV',384,'XOF','Franc',NULL,'CI.png'),('CK','Cook Islands','COK',184,'NZD','Dollar','$','CK.png'),('CL','Chile','CHL',152,'CLP','Peso',NULL,'CL.png'),('CM','Cameroon','CMR',120,'XAF','Franc','FCF','CM.png'),('CN','China','CHN',156,'CNY','Yuan Renminbi','¥','CN.png'),('CO','Colombia','COL',170,'COP','Peso','$','CO.png'),('CR','Costa Rica','CRI',188,'CRC','Colon','₡','CR.png'),('CS','Serbia and Montenegro','SCG',891,'RSD','Dinar','Дин','CS.png'),('CU','Cuba','CUB',192,'CUP','Peso','₱','CU.png'),('CV','Cape Verde','CPV',132,'CVE','Escudo',NULL,'CV.png'),('CX','Christmas Island','CXR',162,'AUD','Dollar','$','CX.png'),('CY','Cyprus','CYP',196,'CYP','Pound',NULL,'CY.png'),('CZ','Czech Republic','CZE',203,'CZK','Koruna','Kč','CZ.png'),('DE','Germany','DEU',276,'EUR','Euro','€','DE.png'),('DJ','Djibouti','DJI',262,'DJF','Franc',NULL,'DJ.png'),('DK','Denmark','DNK',208,'DKK','Krone','kr','DK.png'),('DM','Dominica','DMA',212,'XCD','Dollar','$','DM.png'),('DO','Dominican Republic','DOM',214,'DOP','Peso','RD$','DO.png'),('DZ','Algeria','DZA',12,'DZD','Dinar',NULL,'DZ.png'),('EC','Ecuador','ECU',218,'USD','Dollar','$','EC.png'),('EE','Estonia','EST',233,'EEK','Kroon','kr','EE.png'),('EG','Egypt','EGY',818,'EGP','Pound','£','EG.png'),('EH','Western Sahara','ESH',732,'MAD','Dirham',NULL,'EH.png'),('ER','Eritrea','ERI',232,'ERN','Nakfa','Nfk','ER.png'),('ES','Spain','ESP',724,'EUR','Euro','€','ES.png'),('ET','Ethiopia','ETH',231,'ETB','Birr',NULL,'ET.png'),('FI','Finland','FIN',246,'EUR','Euro','€','FI.png'),('FJ','Fiji','FJI',242,'FJD','Dollar','$','FJ.png'),('FK','Falkland Islands','FLK',238,'FKP','Pound','£','FK.png'),('FM','Micronesia','FSM',583,'USD','Dollar','$','FM.png'),('FO','Faroe Islands','FRO',234,'DKK','Krone','kr','FO.png'),('FR','France','FRA',250,'EUR','Euro','€','FR.png'),('GA','Gabon','GAB',266,'XAF','Franc','FCF','GA.png'),('GB','United Kingdom','GBR',826,'GBP','Pound','£','GB.png'),('GD','Grenada','GRD',308,'XCD','Dollar','$','GD.png'),('GE','Georgia','GEO',268,'GEL','Lari',NULL,'GE.png'),('GF','French Guiana','GUF',254,'EUR','Euro','€','GF.png'),('GH','Ghana','GHA',288,'GHC','Cedi','¢','GH.png'),('GI','Gibraltar','GIB',292,'GIP','Pound','£','GI.png'),('GL','Greenland','GRL',304,'DKK','Krone','kr','GL.png'),('GM','Gambia','GMB',270,'GMD','Dalasi','D','GM.png'),('GN','Guinea','GIN',324,'GNF','Franc',NULL,'GN.png'),('GP','Guadeloupe','GLP',312,'EUR','Euro','€','GP.png'),('GQ','Equatorial Guinea','GNQ',226,'XAF','Franc','FCF','GQ.png'),('GR','Greece','GRC',300,'EUR','Euro','€','GR.png'),('GS','South Georgia and the South Sandwich Islands','SGS',239,'GBP','Pound','£','GS.png'),('GT','Guatemala','GTM',320,'GTQ','Quetzal','Q','GT.png'),('GU','Guam','GUM',316,'USD','Dollar','$','GU.png'),('GW','Guinea-Bissau','GNB',624,'XOF','Franc',NULL,'GW.png'),('GY','Guyana','GUY',328,'GYD','Dollar','$','GY.png'),('HK','Hong Kong','HKG',344,'HKD','Dollar','$','HK.png'),('HM','Heard Island and McDonald Islands','HMD',334,'AUD','Dollar','$','HM.png'),('HN','Honduras','HND',340,'HNL','Lempira','L','HN.png'),('HR','Croatia','HRV',191,'HRK','Kuna','kn','HR.png'),('HT','Haiti','HTI',332,'HTG','Gourde','G','HT.png'),('HU','Hungary','HUN',348,'HUF','Forint','Ft','HU.png'),('ID','Indonesia','IDN',360,'IDR','Rupiah','Rp','ID.png'),('IE','Ireland','IRL',372,'EUR','Euro','€','IE.png'),('IL','Israel','ISR',376,'ILS','Shekel','₪','IL.png'),('IN','India','IND',356,'INR','Rupee','₹','IN.png'),('IO','British Indian Ocean Territory','IOT',86,'USD','Dollar','$','IO.png'),('IQ','Iraq','IRQ',368,'IQD','Dinar',NULL,'IQ.png'),('IR','Iran','IRN',364,'IRR','Rial','﷼','IR.png'),('IS','Iceland','ISL',352,'ISK','Krona','kr','IS.png'),('IT','Italy','ITA',380,'EUR','Euro','€','IT.png'),('JM','Jamaica','JAM',388,'JMD','Dollar','$','JM.png'),('JO','Jordan','JOR',400,'JOD','Dinar',NULL,'JO.png'),('JP','Japan','JPN',392,'JPY','Yen','¥','JP.png'),('KE','Kenya','KEN',404,'KES','Shilling',NULL,'KE.png'),('KG','Kyrgyzstan','KGZ',417,'KGS','Som','лв','KG.png'),('KH','Cambodia','KHM',116,'KHR','Riels','៛','KH.png'),('KI','Kiribati','KIR',296,'AUD','Dollar','$','KI.png'),('KM','Comoros','COM',174,'KMF','Franc',NULL,'KM.png'),('KN','Saint Kitts and Nevis','KNA',659,'XCD','Dollar','$','KN.png'),('KP','North Korea','PRK',408,'KPW','Won','₩','KP.png'),('KR','South Korea','KOR',410,'KRW','Won','₩','KR.png'),('KW','Kuwait','KWT',414,'KWD','Dinar',NULL,'KW.png'),('KY','Cayman Islands','CYM',136,'KYD','Dollar','$','KY.png'),('KZ','Kazakhstan','KAZ',398,'KZT','Tenge','лв','KZ.png'),('LA','Laos','LAO',418,'LAK','Kip','₭','LA.png'),('LB','Lebanon','LBN',422,'LBP','Pound','£','LB.png'),('LC','Saint Lucia','LCA',662,'XCD','Dollar','$','LC.png'),('LI','Liechtenstein','LIE',438,'CHF','Franc','CHF','LI.png'),('LK','Sri Lanka','LKA',144,'LKR','Rupee','₨','LK.png'),('LR','Liberia','LBR',430,'LRD','Dollar','$','LR.png'),('LS','Lesotho','LSO',426,'LSL','Loti','L','LS.png'),('LT','Lithuania','LTU',440,'LTL','Litas','Lt','LT.png'),('LU','Luxembourg','LUX',442,'EUR','Euro','€','LU.png'),('LV','Latvia','LVA',428,'LVL','Lat','Ls','LV.png'),('LY','Libya','LBY',434,'LYD','Dinar',NULL,'LY.png'),('MA','Morocco','MAR',504,'MAD','Dirham',NULL,'MA.png'),('MC','Monaco','MCO',492,'EUR','Euro','€','MC.png'),('MD','Moldova','MDA',498,'MDL','Leu',NULL,'MD.png'),('MG','Madagascar','MDG',450,'MGA','Ariary',NULL,'MG.png'),('MH','Marshall Islands','MHL',584,'USD','Dollar','$','MH.png'),('MK','Macedonia','MKD',807,'MKD','Denar','ден','MK.png'),('ML','Mali','MLI',466,'XOF','Franc',NULL,'ML.png'),('MM','Myanmar','MMR',104,'MMK','Kyat','K','MM.png'),('MN','Mongolia','MNG',496,'MNT','Tugrik','₮','MN.png'),('MO','Macao','MAC',446,'MOP','Pataca','MOP','MO.png'),('MP','Northern Mariana Islands','MNP',580,'USD','Dollar','$','MP.png'),('MQ','Martinique','MTQ',474,'EUR','Euro','€','MQ.png'),('MR','Mauritania','MRT',478,'MRO','Ouguiya','UM','MR.png'),('MS','Montserrat','MSR',500,'XCD','Dollar','$','MS.png'),('MT','Malta','MLT',470,'MTL','Lira',NULL,'MT.png'),('MU','Mauritius','MUS',480,'MUR','Rupee','₨','MU.png'),('MV','Maldives','MDV',462,'MVR','Rufiyaa','Rf','MV.png'),('MW','Malawi','MWI',454,'MWK','Kwacha','MK','MW.png'),('MX','Mexico','MEX',484,'MXN','Peso','$','MX.png'),('MY','Malaysia','MYS',458,'MYR','Ringgit','RM','MY.png'),('MZ','Mozambique','MOZ',508,'MZN','Meticail','MT','MZ.png'),('NA','Namibia','NAM',516,'NAD','Dollar','$','NA.png'),('NC','New Caledonia','NCL',540,'XPF','Franc',NULL,'NC.png'),('NE','Niger','NER',562,'XOF','Franc',NULL,'NE.png'),('NF','Norfolk Island','NFK',574,'AUD','Dollar','$','NF.png'),('NG','Nigeria','NGA',566,'NGN','Naira','₦','NG.png'),('NI','Nicaragua','NIC',558,'NIO','Cordoba','C$','NI.png'),('NL','Netherlands','NLD',528,'EUR','Euro','€','NL.png'),('NO','Norway','NOR',578,'NOK','Krone','kr','NO.png'),('NP','Nepal','NPL',524,'NPR','Rupee','₨','NP.png'),('NR','Nauru','NRU',520,'AUD','Dollar','$','NR.png'),('NU','Niue','NIU',570,'NZD','Dollar','$','NU.png'),('NZ','New Zealand','NZL',554,'NZD','Dollar','$','NZ.png'),('OM','Oman','OMN',512,'OMR','Rial','﷼','OM.png'),('PA','Panama','PAN',591,'PAB','Balboa','B/.','PA.png'),('PE','Peru','PER',604,'PEN','Sol','S/.','PE.png'),('PF','French Polynesia','PYF',258,'XPF','Franc',NULL,'PF.png'),('PG','Papua New Guinea','PNG',598,'PGK','Kina',NULL,'PG.png'),('PH','Philippines','PHL',608,'PHP','Peso','Php','PH.png'),('PK','Pakistan','PAK',586,'PKR','Rupee','₨','PK.png'),('PL','Poland','POL',616,'PLN','Zloty','zł','PL.png'),('PM','Saint Pierre and Miquelon','SPM',666,'EUR','Euro','€','PM.png'),('PN','Pitcairn','PCN',612,'NZD','Dollar','$','PN.png'),('PR','Puerto Rico','PRI',630,'USD','Dollar','$','PR.png'),('PS','Palestinian Territory','PSE',275,'ILS','Shekel','₪','PS.png'),('PT','Portugal','PRT',620,'EUR','Euro','€','PT.png'),('PW','Palau','PLW',585,'USD','Dollar','$','PW.png'),('PY','Paraguay','PRY',600,'PYG','Guarani','Gs','PY.png'),('QA','Qatar','QAT',634,'QAR','Rial','﷼','QA.png'),('RE','Reunion','REU',638,'EUR','Euro','€','RE.png'),('RO','Romania','ROU',642,'RON','Leu','lei','RO.png'),('RU','Russia','RUS',643,'RUB','Ruble','руб','RU.png'),('RW','Rwanda','RWA',646,'RWF','Franc',NULL,'RW.png'),('SA','Saudi Arabia','SAU',682,'SAR','Rial','﷼','SA.png'),('SB','Solomon Islands','SLB',90,'SBD','Dollar','$','SB.png'),('SC','Seychelles','SYC',690,'SCR','Rupee','₨','SC.png'),('SD','Sudan','SDN',736,'SDD','Dinar',NULL,'SD.png'),('SE','Sweden','SWE',752,'SEK','Krona','kr','SE.png'),('SG','Singapore','SGP',702,'SGD','Dollar','$','SG.png'),('SH','Saint Helena','SHN',654,'SHP','Pound','£','SH.png'),('SI','Slovenia','SVN',705,'EUR','Euro','€','SI.png'),('SJ','Svalbard and Jan Mayen','SJM',744,'NOK','Krone','kr','SJ.png'),('SK','Slovakia','SVK',703,'SKK','Koruna','Sk','SK.png'),('SL','Sierra Leone','SLE',694,'SLL','Leone','Le','SL.png'),('SM','San Marino','SMR',674,'EUR','Euro','€','SM.png'),('SN','Senegal','SEN',686,'XOF','Franc',NULL,'SN.png'),('SO','Somalia','SOM',706,'SOS','Shilling','S','SO.png'),('SR','Suriname','SUR',740,'SRD','Dollar','$','SR.png'),('ST','Sao Tome and Principe','STP',678,'STD','Dobra','Db','ST.png'),('SV','El Salvador','SLV',222,'SVC','Colone','$','SV.png'),('SY','Syria','SYR',760,'SYP','Pound','£','SY.png'),('SZ','Swaziland','SWZ',748,'SZL','Lilangeni',NULL,'SZ.png'),('TC','Turks and Caicos Islands','TCA',796,'USD','Dollar','$','TC.png'),('TD','Chad','TCD',148,'XAF','Franc',NULL,'TD.png'),('TF','French Southern Territories','ATF',260,'EUR','Euro  ','€','TF.png'),('TG','Togo','TGO',768,'XOF','Franc',NULL,'TG.png'),('TH','Thailand','THA',764,'THB','Baht','฿','TH.png'),('TJ','Tajikistan','TJK',762,'TJS','Somoni',NULL,'TJ.png'),('TK','Tokelau','TKL',772,'NZD','Dollar','$','TK.png'),('TL','East Timor','TLS',626,'USD','Dollar','$','TL.png'),('TM','Turkmenistan','TKM',795,'TMM','Manat','m','TM.png'),('TN','Tunisia','TUN',788,'TND','Dinar',NULL,'TN.png'),('TO','Tonga','TON',776,'TOP','Pa\'anga','T$','TO.png'),('TR','Turkey','TUR',792,'TRY','Lira','YTL','TR.png'),('TT','Trinidad and Tobago','TTO',780,'TTD','Dollar','TT$','TT.png'),('TV','Tuvalu','TUV',798,'AUD','Dollar','$','TV.png'),('TW','Taiwan','TWN',158,'TWD','Dollar','NT$','TW.png'),('TZ','Tanzania','TZA',834,'TZS','Shilling',NULL,'TZ.png'),('UA','Ukraine','UKR',804,'UAH','Hryvnia','₴','UA.png'),('UG','Uganda','UGA',800,'UGX','Shilling',NULL,'UG.png'),('UM','United States Minor Outlying Islands','UMI',581,'USD','Dollar ','$','UM.png'),('US','United States','USA',840,'USD','Dollar','$','US.png'),('UY','Uruguay','URY',858,'UYU','Peso','$U','UY.png'),('UZ','Uzbekistan','UZB',860,'UZS','Som','лв','UZ.png'),('VA','Vatican','VAT',336,'EUR','Euro','€','VA.png'),('VC','Saint Vincent and the Grenadines','VCT',670,'XCD','Dollar','$','VC.png'),('VE','Venezuela','VEN',862,'VEF','Bolivar','Bs','VE.png'),('VG','British Virgin Islands','VGB',92,'USD','Dollar','$','VG.png'),('VI','U.S. Virgin Islands','VIR',850,'USD','Dollar','$','VI.png'),('VN','Vietnam','VNM',704,'VND','Dong','₫','VN.png'),('VU','Vanuatu','VUT',548,'VUV','Vatu','Vt','VU.png'),('WF','Wallis and Futuna','WLF',876,'XPF','Franc',NULL,'WF.png'),('WS','Samoa','WSM',882,'WST','Tala','WS$','WS.png'),('YE','Yemen','YEM',887,'YER','Rial','﷼','YE.png'),('YT','Mayotte','MYT',175,'EUR','Euro','€','YT.png'),('ZA','South Africa','ZAF',710,'ZAR','Rand','R','ZA.png'),('ZM','Zambia','ZMB',894,'ZMK','Kwacha','ZK','ZM.png'),('ZW','Zimbabwe','ZWE',716,'ZWD','Dollar','Z$','ZW.png');
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `destinos`
--

DROP TABLE IF EXISTS `destinos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `destinos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pais` varchar(45) NOT NULL,
  `ciudad` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `ciudad_UNIQUE` (`ciudad`),
  KEY `fk_locacion_countries_idx` (`pais`),
  CONSTRAINT `fk_locacion_countries` FOREIGN KEY (`pais`) REFERENCES `countries` (`iso_alpha2`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destinos`
--

LOCK TABLES `destinos` WRITE;
/*!40000 ALTER TABLE `destinos` DISABLE KEYS */;
INSERT INTO `destinos` VALUES (1,'US','Miami'),(2,'VE','Barquisimeto');
/*!40000 ALTER TABLE `destinos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_guia`
--

DROP TABLE IF EXISTS `detalle_guia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_guia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `guia_id` bigint(20) NOT NULL,
  `detalle_item_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_detalle_guia_guia1_idx` (`guia_id`),
  KEY `fk_detalle_guia_detalle_item1_idx` (`detalle_item_id`),
  CONSTRAINT `fk_detalle_guia_detalle_item1` FOREIGN KEY (`detalle_item_id`) REFERENCES `detalle_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_guia_guia1` FOREIGN KEY (`guia_id`) REFERENCES `guia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_guia`
--

LOCK TABLES `detalle_guia` WRITE;
/*!40000 ALTER TABLE `detalle_guia` DISABLE KEYS */;
INSERT INTO `detalle_guia` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4);
/*!40000 ALTER TABLE `detalle_guia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `detalle_guias_paquete`
--

DROP TABLE IF EXISTS `detalle_guias_paquete`;
/*!50001 DROP VIEW IF EXISTS `detalle_guias_paquete`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `detalle_guias_paquete` (
  `guia` tinyint NOT NULL,
  `paquete` tinyint NOT NULL,
  `estatus` tinyint NOT NULL,
  `fecha_creacion` tinyint NOT NULL,
  `fecha_ultimo_estatus` tinyint NOT NULL,
  `total_cargo` tinyint NOT NULL,
  `total_cargo_cambio` tinyint NOT NULL,
  `currrency_symbol` tinyint NOT NULL,
  `detalle` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `detalle_item`
--

DROP TABLE IF EXISTS `detalle_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paquete_id` bigint(20) NOT NULL,
  `categoria_detalle_id` bigint(20) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `piezas` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_detalle_item_paquete1_idx` (`paquete_id`),
  KEY `fk_detalle_item_categoria_detalle1_idx` (`categoria_detalle_id`),
  CONSTRAINT `fk_detalle_item_categoria_detalle1` FOREIGN KEY (`categoria_detalle_id`) REFERENCES `categoria_detalle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_item_paquete1` FOREIGN KEY (`paquete_id`) REFERENCES `paquete` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_item`
--

LOCK TABLES `detalle_item` WRITE;
/*!40000 ALTER TABLE `detalle_item` DISABLE KEYS */;
INSERT INTO `detalle_item` VALUES (1,15,2,'medias',1),(2,16,2,'zapatos',2),(3,16,2,'celular',2),(4,16,2,'computador',2);
/*!40000 ALTER TABLE `detalle_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_notificacion`
--

DROP TABLE IF EXISTS `detalle_notificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_notificacion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paquete_id` bigint(20) NOT NULL,
  `empresa_envio_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL,
  `descripcion` text NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_detalle_notificacion_paquete1_idx` (`paquete_id`),
  KEY `fk_detalle_notificacion_empresa_envio1_idx` (`empresa_envio_id`),
  KEY `fk_detalle_notificacion_users1_idx` (`users_id`),
  CONSTRAINT `fk_detalle_notificacion_empresa_envio1` FOREIGN KEY (`empresa_envio_id`) REFERENCES `empresa_envio` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_notificacion_paquete1` FOREIGN KEY (`paquete_id`) REFERENCES `paquete` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_notificacion_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_notificacion`
--

LOCK TABLES `detalle_notificacion` WRITE;
/*!40000 ALTER TABLE `detalle_notificacion` DISABLE KEYS */;
INSERT INTO `detalle_notificacion` VALUES (11,12,3,2,'celular, zapatos, pantalon y dos franelas','2014-04-09 14:25:59','2014-04-09 14:25:59'),(12,13,3,2,'cocina, lavadora','2014-04-09 15:24:50','2014-04-09 15:24:50'),(13,14,3,2,'samsung galaxy','2014-04-09 15:26:39','2014-04-09 15:26:39'),(14,15,3,3,'catalinas','2014-04-10 11:12:10','2014-04-10 11:12:10'),(15,16,3,2,'computador','2014-04-24 14:16:42','2014-04-24 14:16:42'),(16,17,3,2,'medias y zapatos','2014-04-28 15:49:59','2014-04-28 15:49:59');
/*!40000 ALTER TABLE `detalle_notificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_recepcion`
--

DROP TABLE IF EXISTS `detalle_recepcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_recepcion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paquete_id` bigint(20) NOT NULL,
  `empresa_envio_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL,
  `descripcion` text NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_detalle_recepcion_paquete1_idx` (`paquete_id`),
  KEY `fk_detalle_recepcion_empresa_envio1_idx` (`empresa_envio_id`),
  KEY `fk_detalle_recepcion_users1_idx` (`users_id`),
  CONSTRAINT `fk_detalle_recepcion_empresa_envio1` FOREIGN KEY (`empresa_envio_id`) REFERENCES `empresa_envio` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_recepcion_paquete1` FOREIGN KEY (`paquete_id`) REFERENCES `paquete` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_recepcion_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_recepcion`
--

LOCK TABLES `detalle_recepcion` WRITE;
/*!40000 ALTER TABLE `detalle_recepcion` DISABLE KEYS */;
INSERT INTO `detalle_recepcion` VALUES (6,12,3,1,'','2014-04-09 15:21:00','2014-04-09 15:21:00'),(7,14,3,1,'celular samsung galaxy 5','2014-04-09 15:25:35','2014-04-09 15:25:35'),(8,13,3,1,'cocina y lavadora','2014-04-10 09:42:58','2014-04-10 09:42:58'),(9,16,3,1,'computador portatil','2014-04-24 14:18:10','2014-04-24 14:18:10'),(10,17,3,1,'medias y zapatos','2014-04-28 15:50:39','2014-04-28 15:50:39');
/*!40000 ALTER TABLE `detalle_recepcion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'I.S Supply Inc.','2800 Glades Circle Suite 101 Weston, FL 33327, UNITED STATES','954-888-6624');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa_envio`
--

DROP TABLE IF EXISTS `empresa_envio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa_envio` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa_envio`
--

LOCK TABLES `empresa_envio` WRITE;
/*!40000 ALTER TABLE `empresa_envio` DISABLE KEYS */;
INSERT INTO `empresa_envio` VALUES (3,'AMAZON'),(2,'DHL'),(1,'UPS');
/*!40000 ALTER TABLE `empresa_envio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estatus_contenedor`
--

DROP TABLE IF EXISTS `estatus_contenedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estatus_contenedor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenedor_id` bigint(20) NOT NULL,
  `estatus` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `users_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_estatus_contenedor_contenedor1_idx` (`contenedor_id`),
  KEY `fk_estatus_contenedor_users1_idx` (`users_id`),
  CONSTRAINT `fk_estatus_contenedor_contenedor1` FOREIGN KEY (`contenedor_id`) REFERENCES `contenedor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_estatus_contenedor_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estatus_contenedor`
--

LOCK TABLES `estatus_contenedor` WRITE;
/*!40000 ALTER TABLE `estatus_contenedor` DISABLE KEYS */;
INSERT INTO `estatus_contenedor` VALUES (1,1,'CREADO','2014-04-28 13:36:02',1),(2,1,'EN_TRANSITO_PAIS_DESTINO','2014-04-28 13:37:17',1),(3,1,'CONFIRMADO_PAIS_DESTINO','2014-04-28 13:37:21',1),(4,1,'CONFIRMADO_ALMACEN_DESTINO','2014-04-28 13:37:34',1);
/*!40000 ALTER TABLE `estatus_contenedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estatus_guia`
--

DROP TABLE IF EXISTS `estatus_guia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estatus_guia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `guia_id` bigint(20) NOT NULL,
  `estatus` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `users_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_estatus_guia_guia1_idx` (`guia_id`),
  KEY `fk_estatus_guia_users1_idx` (`users_id`),
  CONSTRAINT `fk_estatus_guia_guia1` FOREIGN KEY (`guia_id`) REFERENCES `guia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_estatus_guia_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estatus_guia`
--

LOCK TABLES `estatus_guia` WRITE;
/*!40000 ALTER TABLE `estatus_guia` DISABLE KEYS */;
INSERT INTO `estatus_guia` VALUES (1,1,'CREADO','2014-04-28 07:55:00',1),(2,1,'AGREGADO_AL_CONTENEDOR','2014-04-28 13:36:02',1),(3,1,'EN_TRANSITO_PAIS_DESTINO','2014-04-28 13:37:17',1),(4,1,'CONFIRMADO_ALMACEN_DESTINO','2014-04-28 13:38:11',1),(5,2,'CREADO','2014-05-02 12:47:16',1),(6,3,'CREADO','2014-05-02 14:05:16',1),(7,4,'CREADO','2014-05-02 14:11:23',1);
/*!40000 ALTER TABLE `estatus_guia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estatus_paquete`
--

DROP TABLE IF EXISTS `estatus_paquete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estatus_paquete` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paquete_id` bigint(20) NOT NULL,
  `estatus` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `users_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_table1_paquete1_idx` (`paquete_id`),
  KEY `fk_estatus_paquete_users1_idx` (`users_id`),
  CONSTRAINT `fk_estatus_paquete_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_table1_paquete1` FOREIGN KEY (`paquete_id`) REFERENCES `paquete` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estatus_paquete`
--

LOCK TABLES `estatus_paquete` WRITE;
/*!40000 ALTER TABLE `estatus_paquete` DISABLE KEYS */;
INSERT INTO `estatus_paquete` VALUES (23,16,'NOTIFICADO','2014-04-24 14:16:42',2),(24,16,'NOTIFICADO_CONFIRMADO','2014-04-24 14:18:10',1),(25,17,'NOTIFICADO','2014-04-28 15:49:59',2),(26,17,'NOTIFICADO_CONFIRMADO','2014-04-28 15:50:39',1);
/*!40000 ALTER TABLE `estatus_paquete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guia`
--

DROP TABLE IF EXISTS `guia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tipo_transportacion` varchar(100) NOT NULL,
  `estatus` varchar(255) NOT NULL,
  `tipo_cargo` varchar(45) NOT NULL,
  `precio_cargo` decimal(15,5) NOT NULL,
  `total_cargo` decimal(15,5) NOT NULL,
  `total_cargo_cambio` decimal(15,5) NOT NULL,
  `descuento` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT 'Descuento en porcentaje sobre el total de cargo de todos los items',
  `peso` decimal(15,5) NOT NULL,
  `largo` decimal(15,5) NOT NULL,
  `ancho` decimal(15,5) NOT NULL,
  `alto` decimal(15,5) NOT NULL,
  `volumen` decimal(15,5) NOT NULL,
  `peso_volumetrico` decimal(15,5) NOT NULL,
  `created` datetime NOT NULL,
  `origen` bigint(20) NOT NULL,
  `destino` bigint(20) NOT NULL,
  `creador_id` bigint(20) NOT NULL,
  `cliente` bigint(20) NOT NULL,
  `tipo_embalaje_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_guia_ciudad1_idx` (`origen`),
  KEY `fk_guia_ciudad2_idx` (`destino`),
  KEY `fk_guia_users1_idx` (`creador_id`),
  KEY `fk_guia_users1_idx1` (`cliente`),
  KEY `fk_guia_tipo_embalaje1_idx` (`tipo_embalaje_id`),
  CONSTRAINT `fk_guia_creador` FOREIGN KEY (`creador_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_guia_destino` FOREIGN KEY (`destino`) REFERENCES `destinos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_guia_origen` FOREIGN KEY (`origen`) REFERENCES `destinos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_guia_tipo_embalaje1` FOREIGN KEY (`tipo_embalaje_id`) REFERENCES `tipo_embalaje` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_guia_users1` FOREIGN KEY (`cliente`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guia`
--

LOCK TABLES `guia` WRITE;
/*!40000 ALTER TABLE `guia` DISABLE KEYS */;
INSERT INTO `guia` VALUES (1,'AEREO','ENTREGADO_CLIENTE','PESO_VOLUMETRICO',5.00000,7.45480,670.93200,0.00,1.00000,9.00000,5.50000,5.00000,0.14323,1.49096,'2014-04-28 07:55:00',1,2,1,3,1),(2,'AEREO','CREADO','PESO_VOLUMETRICO',5.00000,3.76505,338.85450,0.00,5.00000,5.00000,5.00000,5.00000,0.07234,0.75301,'2014-05-02 12:47:16',1,2,1,2,1),(3,'AEREO','CREADO','PESO_VOLUMETRICO',5.00000,0.00000,0.00000,0.00,5.00000,5.00000,5.00000,5.00000,0.07234,0.75301,'2014-05-02 14:05:16',1,2,1,2,1),(4,'AEREO','CREADO','PESO_VOLUMETRICO',5.00000,3.76506,338.85542,0.00,5.00000,5.00000,5.00000,5.00000,0.07234,0.75301,'2014-05-02 14:11:23',1,2,1,2,1);
/*!40000 ALTER TABLE `guia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guia_contenedor`
--

DROP TABLE IF EXISTS `guia_contenedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guia_contenedor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenedor_id` bigint(20) NOT NULL,
  `guia_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `guia_id_UNIQUE` (`guia_id`),
  KEY `fk_guia_contenedor_contenedor1_idx` (`contenedor_id`),
  KEY `fk_guia_contenedor_guia1_idx` (`guia_id`),
  CONSTRAINT `fk_guia_contenedor_contenedor1` FOREIGN KEY (`contenedor_id`) REFERENCES `contenedor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_guia_contenedor_guia1` FOREIGN KEY (`guia_id`) REFERENCES `guia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guia_contenedor`
--

LOCK TABLES `guia_contenedor` WRITE;
/*!40000 ALTER TABLE `guia_contenedor` DISABLE KEYS */;
INSERT INTO `guia_contenedor` VALUES (1,1,1);
/*!40000 ALTER TABLE `guia_contenedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `guias`
--

DROP TABLE IF EXISTS `guias`;
/*!50001 DROP VIEW IF EXISTS `guias`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `guias` (
  `id` tinyint NOT NULL,
  `tipo_transportacion` tinyint NOT NULL,
  `estatus` tinyint NOT NULL,
  `descuento` tinyint NOT NULL,
  `created` tinyint NOT NULL,
  `tipo_cargo` tinyint NOT NULL,
  `precio_cargo` tinyint NOT NULL,
  `total_cargo` tinyint NOT NULL,
  `total_cargo_cambio` tinyint NOT NULL,
  `origen` tinyint NOT NULL,
  `destino` tinyint NOT NULL,
  `username` tinyint NOT NULL,
  `first_name` tinyint NOT NULL,
  `last_name` tinyint NOT NULL,
  `moneda_origen` tinyint NOT NULL,
  `moneda_destino` tinyint NOT NULL,
  `detalle` tinyint NOT NULL,
  `trackings` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `paquete`
--

DROP TABLE IF EXISTS `paquete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paquete` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tracking` varchar(100) NOT NULL,
  `asegurar` tinyint(1) NOT NULL,
  `recepcionado` tinyint(1) NOT NULL,
  `notificado` tinyint(1) NOT NULL,
  `tracking_generado` tinyint(1) NOT NULL,
  `estatus` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `tracking_UNIQUE` (`tracking`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paquete`
--

LOCK TABLES `paquete` WRITE;
/*!40000 ALTER TABLE `paquete` DISABLE KEYS */;
INSERT INTO `paquete` VALUES (12,'123456',0,1,1,0,'PROCESADO','2014-04-09 14:25:59','2014-04-09 15:21:00'),(13,'963258741',1,1,1,0,'PROCESADO','2014-04-09 15:24:50','2014-04-10 09:42:58'),(14,'852369741',0,1,1,0,'PROCESADO','2014-04-09 15:25:35','2014-04-09 15:26:39'),(15,'dfasffsfsadfdsf3242241SDF',0,0,1,0,'PROCESADO','2014-04-10 11:12:10','2014-04-10 11:12:10'),(16,'7896542154',0,1,1,0,'NOTIFICADO_CONFIRMADO','2014-04-24 14:16:42','2014-04-24 14:18:10'),(17,'999999999',1,1,1,0,'NOTIFICADO_CONFIRMADO','2014-04-28 15:49:59','2014-04-28 15:50:39');
/*!40000 ALTER TABLE `paquete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `paquetes_notificado_confirmado`
--

DROP TABLE IF EXISTS `paquetes_notificado_confirmado`;
/*!50001 DROP VIEW IF EXISTS `paquetes_notificado_confirmado`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `paquetes_notificado_confirmado` (
  `id` tinyint NOT NULL,
  `username` tinyint NOT NULL,
  `first_name` tinyint NOT NULL,
  `last_name` tinyint NOT NULL,
  `email` tinyint NOT NULL,
  `estatus` tinyint NOT NULL,
  `total` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `tipo_cambio`
--

DROP TABLE IF EXISTS `tipo_cambio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_cambio` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `valor_dolar` decimal(15,5) NOT NULL,
  `country` varchar(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_tipo_cambio_countries1_idx` (`country`),
  CONSTRAINT `fk_tipo_cambio_countries1` FOREIGN KEY (`country`) REFERENCES `countries` (`iso_alpha2`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_cambio`
--

LOCK TABLES `tipo_cambio` WRITE;
/*!40000 ALTER TABLE `tipo_cambio` DISABLE KEYS */;
INSERT INTO `tipo_cambio` VALUES (2,1.00000,'US'),(3,50.00000,'AD'),(4,90.00000,'VE');
/*!40000 ALTER TABLE `tipo_cambio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_embalaje`
--

DROP TABLE IF EXISTS `tipo_embalaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_embalaje` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `largo` decimal(15,5) NOT NULL,
  `ancho` decimal(15,5) NOT NULL,
  `alto` decimal(15,5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_embalaje`
--

LOCK TABLES `tipo_embalaje` WRITE;
/*!40000 ALTER TABLE `tipo_embalaje` DISABLE KEYS */;
INSERT INTO `tipo_embalaje` VALUES (1,'Bolsa',0.00000,0.00000,0.00000),(2,'Caja',0.00000,0.00000,0.00000),(3,'Otro',0.00000,0.00000,0.00000),(4,'Envoplast',0.00000,0.00000,0.00000);
/*!40000 ALTER TABLE `tipo_embalaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tracking_log`
--

DROP TABLE IF EXISTS `tracking_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tracking_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` varchar(255) NOT NULL,
  `entity` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `entity_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_tracking_log_users1_idx` (`users_id`),
  CONSTRAINT `fk_tracking_log_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tracking_log`
--

LOCK TABLES `tracking_log` WRITE;
/*!40000 ALTER TABLE `tracking_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tracking_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `ci_rif_passport` varchar(15) NOT NULL COMMENT 'Cedula, rif, pasaporte. Validar segun el patron: Letra-numeros-numero. Ejemplo: V-16532250-5, G-20000307-7, para pasaporte investigar el patron.',
  `password` varchar(100) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `bb_pin` varchar(45) DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `identificador_UNIQUE` (`ci_rif_passport`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','administrator','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',1,'Admin','Admin','system','admin@localhost.com','55555555','','2014-03-11 00:00:00','2014-04-03 15:46:20'),(2,'wrivas','V16532250','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',1,'Williams','Rivas','Urb Colinas San Lorenzo','williams.rivas1@gmail.com','4145690494','','2014-03-11 14:56:35','2014-05-02 12:54:33'),(3,'anza12','V15885876','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',1,'Francisco','Anza','La peña','francoo@yahoo.es','4169587858',NULL,'2014-03-11 15:00:54','2014-03-11 15:02:39'),(5,'magudelo','V11111111','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',1,'Mercedes','Agudelo','La paz','williams_rivass@yahoo.com','04145235452','','2014-04-10 11:49:55','2014-04-22 11:53:00'),(6,'wrivas1','V15425308','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',1,'dfadsf','dsafasdf','dfsdasfasd','will.riva@gmail.com','41254254',NULL,'2014-04-10 11:57:34','2014-04-10 11:57:34'),(13,'jormar','V14649804','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',1,'Jormar','Heredia','dd','wt.ra@hotmail.com','04152365245',NULL,'2014-04-15 11:30:53','2014-04-15 11:31:28'),(15,'jose12','V123265235','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',0,'jose','perez','san jose','jose@gmail.com','4152365245','','2014-04-22 11:39:55','2014-04-22 11:52:41');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `detalle_guias_paquete`
--

/*!50001 DROP TABLE IF EXISTS `detalle_guias_paquete`*/;
/*!50001 DROP VIEW IF EXISTS `detalle_guias_paquete`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `detalle_guias_paquete` AS select `g`.`id` AS `guia`,`di`.`paquete_id` AS `paquete`,`g`.`estatus` AS `estatus`,`g`.`created` AS `fecha_creacion`,`eg`.`created` AS `fecha_ultimo_estatus`,`g`.`total_cargo` AS `total_cargo`,`g`.`total_cargo_cambio` AS `total_cargo_cambio`,`c`.`currrency_symbol` AS `currrency_symbol`,(select group_concat(`di`.`descripcion` separator ',') AS `descripcion` from (`detalle_item` `di` left join `detalle_guia` `dg` on((`di`.`id` = `dg`.`detalle_item_id`))) where ((`di`.`paquete_id` = `di`.`paquete_id`) and (`dg`.`guia_id` = `g`.`id`)) group by `dg`.`guia_id`) AS `detalle` from (((((`detalle_guia` `dg` left join `detalle_item` `di` on((`dg`.`detalle_item_id` = `di`.`id`))) left join `guia` `g` on((`dg`.`guia_id` = `g`.`id`))) left join `estatus_guia` `eg` on(((`g`.`id` = `eg`.`guia_id`) and (`g`.`estatus` = `eg`.`estatus`)))) left join `destinos` `d` on((`d`.`id` = `g`.`destino`))) left join `countries` `c` on((`c`.`iso_alpha2` = `d`.`pais`))) group by `dg`.`guia_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `guias`
--

/*!50001 DROP TABLE IF EXISTS `guias`*/;
/*!50001 DROP VIEW IF EXISTS `guias`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `guias` AS select `g`.`id` AS `id`,`g`.`tipo_transportacion` AS `tipo_transportacion`,`g`.`estatus` AS `estatus`,`g`.`descuento` AS `descuento`,`g`.`created` AS `created`,`g`.`tipo_cargo` AS `tipo_cargo`,`g`.`precio_cargo` AS `precio_cargo`,`g`.`total_cargo` AS `total_cargo`,`g`.`total_cargo_cambio` AS `total_cargo_cambio`,`o`.`ciudad` AS `origen`,`d`.`ciudad` AS `destino`,`u`.`username` AS `username`,`u`.`first_name` AS `first_name`,`u`.`last_name` AS `last_name`,`co`.`currrency_symbol` AS `moneda_origen`,`cd`.`currrency_symbol` AS `moneda_destino`,group_concat(`di`.`descripcion` separator ',') AS `detalle`,group_concat(distinct `p`.`tracking` separator ',') AS `trackings` from ((((((((`guia` `g` left join `destinos` `o` on((`o`.`id` = `g`.`origen`))) left join `destinos` `d` on((`d`.`id` = `g`.`destino`))) left join `users` `u` on((`u`.`id` = `g`.`creador_id`))) left join `countries` `co` on((`co`.`iso_alpha2` = `o`.`pais`))) left join `countries` `cd` on((`cd`.`iso_alpha2` = `d`.`pais`))) left join `detalle_guia` `dg` on((`dg`.`guia_id` = `g`.`id`))) left join `detalle_item` `di` on((`dg`.`detalle_item_id` = `di`.`id`))) left join `paquete` `p` on((`p`.`id` = `di`.`paquete_id`))) group by `g`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `paquetes_notificado_confirmado`
--

/*!50001 DROP TABLE IF EXISTS `paquetes_notificado_confirmado`*/;
/*!50001 DROP VIEW IF EXISTS `paquetes_notificado_confirmado`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `paquetes_notificado_confirmado` AS select `u`.`id` AS `id`,`u`.`username` AS `username`,`u`.`first_name` AS `first_name`,`u`.`last_name` AS `last_name`,`u`.`email` AS `email`,`p`.`estatus` AS `estatus`,count(0) AS `total` from ((`paquete` `p` join `detalle_notificacion` `d` on((`d`.`paquete_id` = `p`.`id`))) join `users` `u` on((`d`.`users_id` = `u`.`id`))) where (`p`.`estatus` = 'NOTIFICADO_CONFIRMADO') group by `u`.`username` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-02 14:27:09
