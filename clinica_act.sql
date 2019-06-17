/*
 Navicat Premium Data Transfer

 Source Server         : ServidorLocal
 Source Server Type    : MySQL
 Source Server Version : 100137
 Source Host           : localhost:3306
 Source Schema         : clinica

 Target Server Type    : MySQL
 Target Server Version : 100137
 File Encoding         : 65001

 Date: 16/06/2019 22:50:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for acceso
-- ----------------------------
DROP TABLE IF EXISTS `acceso`;
CREATE TABLE `acceso`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USUARIO` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `PASSWORD` varchar(200) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `ULTIMO_ACCESO` datetime(0) NOT NULL,
  `INTENTOS` int(11) NOT NULL,
  `ESTATUS` int(11) NOT NULL,
  `ID_TIPO_ACCESO` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_TIPO_ACCESO`(`ID_TIPO_ACCESO`) USING BTREE,
  CONSTRAINT `acceso_ibfk_1` FOREIGN KEY (`ID_TIPO_ACCESO`) REFERENCES `tipo_acceso` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of acceso
-- ----------------------------
INSERT INTO `acceso` VALUES (1, 'eislas', '6ClOBvr2sKwQ6i17HqBNkWHcg0M=', '2019-06-07 12:15:54', 0, 1, 1);

-- ----------------------------
-- Table structure for agenda
-- ----------------------------
DROP TABLE IF EXISTS `agenda`;
CREATE TABLE `agenda`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FECHA_INICIO` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `FECHA_FIN` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `ID_EVENTO` int(11) NOT NULL,
  `ID_DR` int(11) NOT NULL,
  `ID_PX` int(11) NOT NULL,
  `ESTATUS` int(11) NOT NULL,
  `NOTAS` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `ID_CLINICA` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_EVENTO`(`ID_EVENTO`) USING BTREE,
  INDEX `ID_DR`(`ID_DR`) USING BTREE,
  INDEX `ID_PX`(`ID_PX`) USING BTREE,
  CONSTRAINT `agenda_ibfk_1` FOREIGN KEY (`ID_EVENTO`) REFERENCES `evento` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `agenda_ibfk_2` FOREIGN KEY (`ID_DR`) REFERENCES `doctor` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `agenda_ibfk_3` FOREIGN KEY (`ID_PX`) REFERENCES `persona` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for clinica
-- ----------------------------
DROP TABLE IF EXISTS `clinica`;
CREATE TABLE `clinica`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `TELEFONO` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `SITIO_WEB` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `RESPONSABLE` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `ID_DIRECCION` int(11) NULL DEFAULT NULL,
  `LOGO` blob NULL,
  `OTRO_TEXTO` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `FECHA_REGISTRO` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `ESTATUS` bit(1) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_DIRECCION`(`ID_DIRECCION`) USING BTREE,
  CONSTRAINT `clinica_ibfk_1` FOREIGN KEY (`ID_DIRECCION`) REFERENCES `direccion` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of clinica
-- ----------------------------
INSERT INTO `clinica` VALUES (1, 'Sonrisas', '55555555', NULL, 'Ing. Erik Islas Alvarez', NULL, NULL, NULL, '2019-06-11 16:41:33', b'1');

-- ----------------------------
-- Table structure for direccion
-- ----------------------------
DROP TABLE IF EXISTS `direccion`;
CREATE TABLE `direccion`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CALLE` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `NO_INT` varchar(10) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `NO_EXT` varchar(10) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `COLONIA` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `CP` varchar(10) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `ID_ESTADO` int(11) NULL DEFAULT NULL,
  `MUNICIPIO` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `ID_PAIS` int(11) NULL DEFAULT NULL,
  `OTRAS_REFERENCIAS` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_ESTADO`(`ID_ESTADO`) USING BTREE,
  INDEX `ID_PAIS`(`ID_PAIS`) USING BTREE,
  CONSTRAINT `direccion_ibfk_1` FOREIGN KEY (`ID_ESTADO`) REFERENCES `estado` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `direccion_ibfk_2` FOREIGN KEY (`ID_PAIS`) REFERENCES `pais` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of direccion
-- ----------------------------
INSERT INTO `direccion` VALUES (1, '', '', '', '', '', 11, '', 1, '');
INSERT INTO `direccion` VALUES (2, '', '', '', '', '', 7, '', 1, '');
INSERT INTO `direccion` VALUES (3, '', '', '', '', '', 11, '', 1, '');
INSERT INTO `direccion` VALUES (4, '', '', '', '', '', 7, '', 1, '');

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PERSONA` int(11) NOT NULL,
  `CEDULA` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `TITULO` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `TITULO_PDF` blob NULL,
  `ESTATUS` bit(1) NOT NULL,
  `FECHA_REGISTRO` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `ID_CLINICA` int(11) NOT NULL,
  `TELEFONO` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT '',
  `OTRO_CONTACTO` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT '',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_PERSONA`(`ID_PERSONA`) USING BTREE,
  INDEX `ID_CLINICA`(`ID_CLINICA`) USING BTREE,
  CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`ID_PERSONA`) REFERENCES `persona` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `doctor_ibfk_2` FOREIGN KEY (`ID_CLINICA`) REFERENCES `clinica` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES (1, 5, '12345678', 'Cirujano dentista', NULL, b'0', '2019-06-15 16:50:33', 1, '', '');

-- ----------------------------
-- Table structure for estado
-- ----------------------------
DROP TABLE IF EXISTS `estado`;
CREATE TABLE `estado`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ESTADO` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `ID_PAIS` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of estado
-- ----------------------------
INSERT INTO `estado` VALUES (1, 'Aguascalientes', 1);
INSERT INTO `estado` VALUES (2, 'Baja California', 1);
INSERT INTO `estado` VALUES (3, 'Baja California Sur', 1);
INSERT INTO `estado` VALUES (4, 'Campeche', 1);
INSERT INTO `estado` VALUES (5, 'Chiapas', 1);
INSERT INTO `estado` VALUES (6, 'Chihuahua', 1);
INSERT INTO `estado` VALUES (7, 'Ciudad de México', 1);
INSERT INTO `estado` VALUES (8, 'Coahuila de Zaragoza', 1);
INSERT INTO `estado` VALUES (9, 'Colima', 1);
INSERT INTO `estado` VALUES (10, 'Durango', 1);
INSERT INTO `estado` VALUES (11, 'Estado de México', 1);
INSERT INTO `estado` VALUES (12, 'Guanajuato', 1);
INSERT INTO `estado` VALUES (13, 'Guerrero', 1);
INSERT INTO `estado` VALUES (14, 'Hidalgo', 1);
INSERT INTO `estado` VALUES (15, 'Jalisco', 1);
INSERT INTO `estado` VALUES (16, 'Michoacán de Ocampo', 1);
INSERT INTO `estado` VALUES (17, 'Morelos', 1);
INSERT INTO `estado` VALUES (18, 'Nayarit', 1);
INSERT INTO `estado` VALUES (19, 'Nuevo León', 1);
INSERT INTO `estado` VALUES (20, 'Oaxaca', 1);
INSERT INTO `estado` VALUES (21, 'Puebla', 1);
INSERT INTO `estado` VALUES (22, 'Querétaro', 1);
INSERT INTO `estado` VALUES (23, 'Quintana Roo', 1);
INSERT INTO `estado` VALUES (24, 'San Luis Potosí', 1);
INSERT INTO `estado` VALUES (25, 'Sin Localidad', 1);
INSERT INTO `estado` VALUES (26, 'Sinaloa', 1);
INSERT INTO `estado` VALUES (27, 'Sonora', 1);
INSERT INTO `estado` VALUES (28, 'Tabasco', 1);
INSERT INTO `estado` VALUES (29, 'Tamaulipas', 1);
INSERT INTO `estado` VALUES (30, 'Tlaxcala', 1);
INSERT INTO `estado` VALUES (31, 'Veracruz de Ignacio de la Llave', 1);
INSERT INTO `estado` VALUES (32, 'Yucatán', 1);
INSERT INTO `estado` VALUES (33, 'Zacatecas', 1);

-- ----------------------------
-- Table structure for evento
-- ----------------------------
DROP TABLE IF EXISTS `evento`;
CREATE TABLE `evento`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EVENTO` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `ESTATUS` bit(1) NULL DEFAULT NULL,
  `ID_CLINICA` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_CLINICA`(`ID_CLINICA`) USING BTREE,
  CONSTRAINT `evento_ibfk_1` FOREIGN KEY (`ID_CLINICA`) REFERENCES `clinica` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of evento
-- ----------------------------
INSERT INTO `evento` VALUES (1, 'Consulta', b'1', 1);
INSERT INTO `evento` VALUES (2, 'Limpieza', b'1', 1);
INSERT INTO `evento` VALUES (3, 'Extración', b'1', 1);
INSERT INTO `evento` VALUES (4, 'Blanqueamiento', b'1', 1);
INSERT INTO `evento` VALUES (5, 'Cirugia', b'1', 1);

-- ----------------------------
-- Table structure for paciente
-- ----------------------------
DROP TABLE IF EXISTS `paciente`;
CREATE TABLE `paciente`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PERSONA` int(11) NOT NULL,
  `TELEFONO` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `E_MAIL` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT '',
  `OTRO_CONTACTO` varchar(255) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `FECHA_REGISTRO` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `ID_CLINICA` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_PERSONA`(`ID_PERSONA`) USING BTREE,
  INDEX `ID_CLINICA`(`ID_CLINICA`) USING BTREE,
  CONSTRAINT `paciente_ibfk_1` FOREIGN KEY (`ID_PERSONA`) REFERENCES `persona` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `paciente_ibfk_2` FOREIGN KEY (`ID_CLINICA`) REFERENCES `clinica` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of paciente
-- ----------------------------
INSERT INTO `paciente` VALUES (1, 2, NULL, '', NULL, '2019-06-14 17:02:21', 1);
INSERT INTO `paciente` VALUES (2, 3, '', '', NULL, '2019-06-14 16:26:59', 1);
INSERT INTO `paciente` VALUES (3, 4, '4456875', '', NULL, '2019-06-15 00:28:17', 1);

-- ----------------------------
-- Table structure for pais
-- ----------------------------
DROP TABLE IF EXISTS `pais`;
CREATE TABLE `pais`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PAIS` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pais
-- ----------------------------
INSERT INTO `pais` VALUES (1, 'México');
INSERT INTO `pais` VALUES (2, 'USA');

-- ----------------------------
-- Table structure for perfil
-- ----------------------------
DROP TABLE IF EXISTS `perfil`;
CREATE TABLE `perfil`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_PERFIL` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `PERFIL` bigint(20) NOT NULL,
  `ID_CLINICA` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_CLINICA`(`ID_CLINICA`) USING BTREE,
  CONSTRAINT `perfil_ibfk_1` FOREIGN KEY (`ID_CLINICA`) REFERENCES `clinica` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of perfil
-- ----------------------------
INSERT INTO `perfil` VALUES (1, 'Administardor', 1, 1);

-- ----------------------------
-- Table structure for persona
-- ----------------------------
DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `APELLIDO_PATERNO` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `APELLIDO_MATERNO` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `FECHA_NACIMIENTO` date NULL DEFAULT NULL,
  `ID_DIRECCION` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_DIRECCION`(`ID_DIRECCION`) USING BTREE,
  CONSTRAINT `persona_ibfk_1` FOREIGN KEY (`ID_DIRECCION`) REFERENCES `direccion` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of persona
-- ----------------------------
INSERT INTO `persona` VALUES (1, 'Erik', 'Islas', 'Alvarez', '1994-08-29', NULL);
INSERT INTO `persona` VALUES (2, 'Helder Ivan', 'Islas', 'Alvarez', '1997-11-04', 1);
INSERT INTO `persona` VALUES (3, 'Edwyn', 'Islas', 'Alvarez', '2001-03-07', 2);
INSERT INTO `persona` VALUES (4, 'Erick Jesus', 'Juarez', 'Escamilla', '1992-11-06', 3);
INSERT INTO `persona` VALUES (5, 'Lorena Magali', 'Flores', 'Landa', '1993-04-30', 4);

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROL` varchar(100) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `ESTATUS` bit(1) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'Paciente', b'1');
INSERT INTO `roles` VALUES (2, 'Doctor', b'1');
INSERT INTO `roles` VALUES (3, 'Administardor', b'1');
INSERT INTO `roles` VALUES (4, 'Admingral', b'1');

-- ----------------------------
-- Table structure for tipo_acceso
-- ----------------------------
DROP TABLE IF EXISTS `tipo_acceso`;
CREATE TABLE `tipo_acceso`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_USUARIO` int(11) NOT NULL,
  `ID_ROL` int(11) NOT NULL,
  `ID_PERFIL` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `ID_ROL`(`ID_ROL`) USING BTREE,
  INDEX `ID_PERFIL`(`ID_PERFIL`) USING BTREE,
  CONSTRAINT `tipo_acceso_ibfk_1` FOREIGN KEY (`ID_ROL`) REFERENCES `roles` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tipo_acceso_ibfk_2` FOREIGN KEY (`ID_PERFIL`) REFERENCES `perfil` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tipo_acceso
-- ----------------------------
INSERT INTO `tipo_acceso` VALUES (1, 1, 4, 1);

SET FOREIGN_KEY_CHECKS = 1;
