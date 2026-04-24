CREATE TABLE IF NOT EXISTS `verification_code` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `phone_number` VARCHAR(20) NOT NULL COMMENT '手机号',
    `code` VARCHAR(10) NOT NULL COMMENT '验证码',
    `type` VARCHAR(20) NOT NULL COMMENT '验证码类型: REGISTER, LOGIN, RESET_PASSWORD',
    `expires_time` DATETIME NOT NULL COMMENT '过期时间',
    `used` TINYINT DEFAULT 0 COMMENT '是否已使用: 0未使用, 1已使用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY `idx_code_phone_type` (`phone_number`, `type`),
    KEY `idx_code_expires` (`expires_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码表';
