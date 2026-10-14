-- Contact us 지원(문의) 제출 정보. 비로그인 사용자가 작성, 관리자만 열람.
CREATE TABLE `contact_applications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `contact` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `major` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `introduce` text COLLATE utf8mb4_unicode_ci,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
