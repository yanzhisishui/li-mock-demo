/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : yaorao-mall-order

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 16/12/2024 20:37:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mock_config
-- ----------------------------
DROP TABLE IF EXISTS `mock_config`;
CREATE TABLE `mock_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `initiator_service` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发起方服务id',
  `target_service` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标服务id',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `crt_time` datetime NULL DEFAULT NULL,
  `upt_time` datetime NULL DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mock_config
-- ----------------------------
INSERT INTO `mock_config` VALUES (1, 'feign-mock-client', 'feign-mock-server', '/rcs/apply', '孙玉超', '2024-12-16 20:34:56', '2024-12-16 20:34:58', 0);

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `mock_config_item` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `config_id` int NOT NULL,
                                    `status` tinyint(1) NOT NULL DEFAULT '0',
                                    `expression` varchar(1024),
                                    `mock_response` text,
                                    `creator` varchar(255) DEFAULT NULL,
                                    `crtTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `uptTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `deleted` tinyint NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `mock_platform`.`mock_config_item` (`id`, `config_id`, `status`, `expression`, `mock_response`, `creator`, `crt_time`, `upt_time`, `deleted`) VALUES (1, 2, 1, 'body.request.applyNo == \'applyNo\' && body.request.gateId == \'C017\'', '{\"code\":2,\"message\":\"me2\"}', '孙玉超', '2024-12-17 14:23:56', '2024-12-23 15:44:57', 0);
INSERT INTO `mock_platform`.`mock_config_item` (`id`, `config_id`, `status`, `expression`, `mock_response`, `creator`, `crt_time`, `upt_time`, `deleted`) VALUES (2, 1, 1, 'body.applyNo == \'TQYJKN20240113191341383\'', '{\"code\":1,\"message\":\"me1\"}', '孙玉超', '2024-12-17 15:00:13', '2024-12-23 15:56:44', 0);
INSERT INTO `mock_platform`.`mock_config_item` (`id`, `config_id`, `status`, `expression`, `mock_response`, `creator`, `crt_time`, `upt_time`, `deleted`) VALUES (3, 3, 1, 'body.request.applyNo == \'applyNo\'', '{\"code\":3,\"message\":\"me3\"}', '孙玉超', '2024-12-17 15:51:03', '2024-12-23 15:56:51', 0);
INSERT INTO `mock_platform`.`mock_config_item` (`id`, `config_id`, `status`, `expression`, `mock_response`, `creator`, `crt_time`, `upt_time`, `deleted`) VALUES (4, 4, 1, 'body.applyNo == \'applyNo\' && body.testParam == \'C017\'', '{\"code\":4,\"message\":\"me4\"}', '孙玉超', '2024-12-17 16:54:15', '2024-12-23 15:57:04', 0);