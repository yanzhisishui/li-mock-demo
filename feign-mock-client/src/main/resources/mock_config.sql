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
                                    `expression_list_str` text,
                                    `mock_response` text,
                                    `creator` varchar(255) DEFAULT NULL,
                                    `crtTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `uptTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `deleted` tinyint NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `yaorao-mall-order`.`mock_config_item` (`id`, `config_id`, `status`, `expression_list_str`, `mock_response`, `creator`, `crtTime`, `uptTime`, `deleted`) VALUES (1, 1, 0, '[\"body.request.applyNo == \'applyNo\'\"]', '{\"code\":1,\"message\":\"mockMessage\"}', '孙玉超', '2024-12-16 20:35:29', '2024-12-16 20:37:19', 0);

