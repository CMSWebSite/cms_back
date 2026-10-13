-- 기타 실적(저작권/기술이전/기타)에 저자 항목 추가. 선택 입력이므로 NULL 허용.
ALTER TABLE `other_achievements`
  ADD COLUMN `authors` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL AFTER `description`;
