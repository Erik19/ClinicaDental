/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 100315
Source Host           : localhost:3306
Source Database       : clinica

Target Server Type    : MYSQL
Target Server Version : 100315
File Encoding         : 65001

Date: 2019-06-14 17:17:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for acceso
-- ----------------------------
DROP TABLE IF EXISTS `acceso`;
CREATE TABLE `acceso` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USUARIO` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `PASSWORD` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `ULTIMO_ACCESO` datetime NOT NULL,
  `INTENTOS` int(11) NOT NULL,
  `ESTATUS` int(11) NOT NULL,
  `ID_TIPO_ACCESO` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_TIPO_ACCESO` (`ID_TIPO_ACCESO`),
  CONSTRAINT `acceso_ibfk_1` FOREIGN KEY (`ID_TIPO_ACCESO`) REFERENCES `tipo_acceso` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of acceso
-- ----------------------------
INSERT INTO `acceso` VALUES ('1', 'eislas', '6ClOBvr2sKwQ6i17HqBNkWHcg0M=', '2019-06-07 12:15:54', '0', '1', '1');

-- ----------------------------
-- Table structure for agenda
-- ----------------------------
DROP TABLE IF EXISTS `agenda`;
CREATE TABLE `agenda` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA_INICIO` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `FECHA_FIN` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ID_EVENTO` int(11) NOT NULL,
  `ID_DR` int(11) NOT NULL,
  `ID_PX` int(11) NOT NULL,
  `ESTATUS` int(11) NOT NULL,
  `NOTAS` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_EVENTO` (`ID_EVENTO`),
  KEY `ID_DR` (`ID_DR`),
  KEY `ID_PX` (`ID_PX`),
  CONSTRAINT `agenda_ibfk_1` FOREIGN KEY (`ID_EVENTO`) REFERENCES `evento` (`ID`),
  CONSTRAINT `agenda_ibfk_2` FOREIGN KEY (`ID_DR`) REFERENCES `doctor` (`ID`),
  CONSTRAINT `agenda_ibfk_3` FOREIGN KEY (`ID_PX`) REFERENCES `persona` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of agenda
-- ----------------------------

-- ----------------------------
-- Table structure for clinica
-- ----------------------------
DROP TABLE IF EXISTS `clinica`;
CREATE TABLE `clinica` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  `TELEFONO` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  `SITIO_WEB` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `RESPONSABLE` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `ID_DIRECCION` int(11) DEFAULT NULL,
  `LOGO` blob DEFAULT NULL,
  `OTRO_TEXTO` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `FECHA_REGISTRO` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ESTATUS` bit(1) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_DIRECCION` (`ID_DIRECCION`),
  CONSTRAINT `clinica_ibfk_1` FOREIGN KEY (`ID_DIRECCION`) REFERENCES `direccion` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of clinica
-- ----------------------------
INSERT INTO `clinica` VALUES ('1', 'Sonrisas', '55555555', null, 'Ing. Erik Islas Alvarez', null, null, null, '2019-06-11 16:41:33', '');

-- ----------------------------
-- Table structure for direccion
-- ----------------------------
DROP TABLE IF EXISTS `direccion`;
CREATE TABLE `direccion` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CALLE` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `NO_INT` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `NO_EXT` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `COLONIA` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `CP` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `ID_ESTADO` int(11) DEFAULT NULL,
  `MUNICIPIO` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `ID_PAIS` int(11) DEFAULT NULL,
  `OTRAS_REFERENCIAS` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_ESTADO` (`ID_ESTADO`),
  KEY `ID_PAIS` (`ID_PAIS`),
  CONSTRAINT `direccion_ibfk_1` FOREIGN KEY (`ID_ESTADO`) REFERENCES `estado` (`ID`),
  CONSTRAINT `direccion_ibfk_2` FOREIGN KEY (`ID_PAIS`) REFERENCES `pais` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of direccion
-- ----------------------------
INSERT INTO `direccion` VALUES ('1', '', '', '', '', '', '11', '', '1', '');
INSERT INTO `direccion` VALUES ('2', '', '', '', '', '', '7', '', '1', '');

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PERSONA` int(11) NOT NULL,
  `CEDULA` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  `TITULO` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `TITULO_PDF` blob DEFAULT NULL,
  `ESTATUS` bit(1) NOT NULL,
  `FECHA_REGISTRO` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ID_CLINICA` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_PERSONA` (`ID_PERSONA`),
  KEY `ID_CLINICA` (`ID_CLINICA`),
  CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`ID_PERSONA`) REFERENCES `persona` (`ID`),
  CONSTRAINT `doctor_ibfk_2` FOREIGN KEY (`ID_CLINICA`) REFERENCES `clinica` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of doctor
-- ----------------------------

-- ----------------------------
-- Table structure for estado
-- ----------------------------
DROP TABLE IF EXISTS `estado`;
CREATE TABLE `estado` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ESTADO` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `ID_PAIS` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of estado
-- ----------------------------
INSERT INTO `estado` VALUES ('1', 'Aguascalientes', '1');
INSERT INTO `estado` VALUES ('2', 'Baja California', '1');
INSERT INTO `estado` VALUES ('3', 'Baja California Sur', '1');
INSERT INTO `estado` VALUES ('4', 'Campeche', '1');
INSERT INTO `estado` VALUES ('5', 'Chiapas', '1');
INSERT INTO `estado` VALUES ('6', 'Chihuahua', '1');
INSERT INTO `estado` VALUES ('7', 'Ciudad de México', '1');
INSERT INTO `estado` VALUES ('8', 'Coahuila de Zaragoza', '1');
INSERT INTO `estado` VALUES ('9', 'Colima', '1');
INSERT INTO `estado` VALUES ('10', 'Durango', '1');
INSERT INTO `estado` VALUES ('11', 'Estado de México', '1');
INSERT INTO `estado` VALUES ('12', 'Guanajuato', '1');
INSERT INTO `estado` VALUES ('13', 'Guerrero', '1');
INSERT INTO `estado` VALUES ('14', 'Hidalgo', '1');
INSERT INTO `estado` VALUES ('15', 'Jalisco', '1');
INSERT INTO `estado` VALUES ('16', 'Michoacán de Ocampo', '1');
INSERT INTO `estado` VALUES ('17', 'Morelos', '1');
INSERT INTO `estado` VALUES ('18', 'Nayarit', '1');
INSERT INTO `estado` VALUES ('19', 'Nuevo León', '1');
INSERT INTO `estado` VALUES ('20', 'Oaxaca', '1');
INSERT INTO `estado` VALUES ('21', 'Puebla', '1');
INSERT INTO `estado` VALUES ('22', 'Querétaro', '1');
INSERT INTO `estado` VALUES ('23', 'Quintana Roo', '1');
INSERT INTO `estado` VALUES ('24', 'San Luis Potosí', '1');
INSERT INTO `estado` VALUES ('25', 'Sin Localidad', '1');
INSERT INTO `estado` VALUES ('26', 'Sinaloa', '1');
INSERT INTO `estado` VALUES ('27', 'Sonora', '1');
INSERT INTO `estado` VALUES ('28', 'Tabasco', '1');
INSERT INTO `estado` VALUES ('29', 'Tamaulipas', '1');
INSERT INTO `estado` VALUES ('30', 'Tlaxcala', '1');
INSERT INTO `estado` VALUES ('31', 'Veracruz de Ignacio de la Llave', '1');
INSERT INTO `estado` VALUES ('32', 'Yucatán', '1');
INSERT INTO `estado` VALUES ('33', 'Zacatecas', '1');

-- ----------------------------
-- Table structure for evento
-- ----------------------------
DROP TABLE IF EXISTS `evento`;
CREATE TABLE `evento` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EVENTO` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  `ESTATUS` bit(1) DEFAULT NULL,
  `ID_CLINICA` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_CLINICA` (`ID_CLINICA`),
  CONSTRAINT `evento_ibfk_1` FOREIGN KEY (`ID_CLINICA`) REFERENCES `clinica` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of evento
-- ----------------------------
INSERT INTO `evento` VALUES ('1', 'Consulta', '', '1');
INSERT INTO `evento` VALUES ('2', 'Limpieza', '', '1');
INSERT INTO `evento` VALUES ('3', 'Extración', '', '1');
INSERT INTO `evento` VALUES ('4', 'Blanqueamiento', '', '1');

-- ----------------------------
-- Table structure for paciente
-- ----------------------------
DROP TABLE IF EXISTS `paciente`;
CREATE TABLE `paciente` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PERSONA` int(11) NOT NULL,
  `TELEFONO` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  `E_MAIL` varchar(100) COLLATE utf8_spanish_ci DEFAULT '',
  `OTRO_CONTACTO` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `FECHA_REGISTRO` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ID_CLINICA` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_PERSONA` (`ID_PERSONA`),
  KEY `ID_CLINICA` (`ID_CLINICA`),
  CONSTRAINT `paciente_ibfk_1` FOREIGN KEY (`ID_PERSONA`) REFERENCES `persona` (`ID`),
  CONSTRAINT `paciente_ibfk_2` FOREIGN KEY (`ID_CLINICA`) REFERENCES `clinica` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of paciente
-- ----------------------------
INSERT INTO `paciente` VALUES ('1', '2', null, '', null, '2019-06-14 17:02:21', '1');
INSERT INTO `paciente` VALUES ('2', '3', '', '', null, '2019-06-14 16:26:59', '1');

-- ----------------------------
-- Table structure for pais
-- ----------------------------
DROP TABLE IF EXISTS `pais`;
CREATE TABLE `pais` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PAIS` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of pais
-- ----------------------------
INSERT INTO `pais` VALUES ('1', 'México');
INSERT INTO `pais` VALUES ('2', 'USA');

-- ----------------------------
-- Table structure for perfil
-- ----------------------------
DROP TABLE IF EXISTS `perfil`;
CREATE TABLE `perfil` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_PERFIL` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `PERFIL` bigint(20) NOT NULL,
  `ID_CLINICA` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_CLINICA` (`ID_CLINICA`),
  CONSTRAINT `perfil_ibfk_1` FOREIGN KEY (`ID_CLINICA`) REFERENCES `clinica` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of perfil
-- ----------------------------
INSERT INTO `perfil` VALUES ('1', 'Administardor', '1', '1');

-- ----------------------------
-- Table structure for persona
-- ----------------------------
DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(30) COLLATE utf8_spanish_ci DEFAULT NULL,
  `APELLIDO_PATERNO` varchar(30) COLLATE utf8_spanish_ci DEFAULT NULL,
  `APELLIDO_MATERNO` varchar(30) COLLATE utf8_spanish_ci DEFAULT NULL,
  `FECHA_NACIMIENTO` date DEFAULT NULL,
  `ID_DIRECCION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_DIRECCION` (`ID_DIRECCION`),
  CONSTRAINT `persona_ibfk_1` FOREIGN KEY (`ID_DIRECCION`) REFERENCES `direccion` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of persona
-- ----------------------------
INSERT INTO `persona` VALUES ('1', 'Erik', 'Islas', 'Alvarez', '1994-08-29', null);
INSERT INTO `persona` VALUES ('2', 'Helder Ivan', 'Islas', 'Alvarez', '1997-11-04', '1');
INSERT INTO `persona` VALUES ('3', 'Edwyn', 'Islas', 'Alvarez', '2001-03-07', '2');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROL` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `ESTATUS` bit(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', 'Paciente', '');
INSERT INTO `roles` VALUES ('2', 'Doctor', '');
INSERT INTO `roles` VALUES ('3', 'Administardor', '');
INSERT INTO `roles` VALUES ('4', 'Admingral', '');

-- ----------------------------
-- Table structure for tipo_acceso
-- ----------------------------
DROP TABLE IF EXISTS `tipo_acceso`;
CREATE TABLE `tipo_acceso` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_USUARIO` int(11) NOT NULL,
  `ID_ROL` int(11) NOT NULL,
  `ID_PERFIL` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_ROL` (`ID_ROL`),
  KEY `ID_PERFIL` (`ID_PERFIL`),
  CONSTRAINT `tipo_acceso_ibfk_1` FOREIGN KEY (`ID_ROL`) REFERENCES `roles` (`ID`),
  CONSTRAINT `tipo_acceso_ibfk_2` FOREIGN KEY (`ID_PERFIL`) REFERENCES `perfil` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- ----------------------------
-- Records of tipo_acceso
-- ----------------------------
INSERT INTO `tipo_acceso` VALUES ('1', '1', '4', '1');
SET FOREIGN_KEY_CHECKS=1;
