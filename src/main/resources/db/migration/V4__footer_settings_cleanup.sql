-- 실운영 정리: 푸터 전화/FAX 자리표시자(XXXX) 제거, 저작권 문구를 연도 토큰({year}) 방식으로 교체.
-- 운영자가 이미 다른 값으로 바꾼 경우는 건드리지 않는다.
UPDATE `site_settings`
   SET `value` = ''
 WHERE `setting_key` IN ('footer.phone', 'footer.fax')
   AND `value` LIKE '%XXXX%';

UPDATE `site_settings`
   SET `value` = '© {year} Cybermarine System Lab. All rights reserved.'
 WHERE `setting_key` = 'footer.copyright'
   AND `value` = 'Copyright © 2026 Cybermarine System Lab. All Rights Reserved.';
