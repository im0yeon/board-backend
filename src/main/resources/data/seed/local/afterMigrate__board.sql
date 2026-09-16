-- 로컬 확인용 테스트 게시글 - 게시글이 하나도 없을 때만 넣는다
INSERT INTO board (title, content, writer, created_at, updated_at)
SELECT CONCAT(v.subject, ' (', v.seq, ')'),
       v.content,
       v.writer,
       v.created_at,
       v.created_at
FROM (
    SELECT r.x AS seq,
           CASE MOD(r.x, 10)
               WHEN 0 THEN '시스템 정기 점검 안내'
               WHEN 1 THEN '사내 문서 서식 개편 안내'
               WHEN 2 THEN '주차장 이용 규칙 변경'
               WHEN 3 THEN '연차 사용 촉진 제도 안내'
               WHEN 4 THEN '보안 교육 이수 요청'
               WHEN 5 THEN '사무용품 신청 절차 변경'
               WHEN 6 THEN '워크숍 사전 설문 요청'
               WHEN 7 THEN '출입 카드 재발급 안내'
               WHEN 8 THEN '회의실 예약 시스템 개편'
               ELSE '복리후생 포인트 사용 안내'
           END AS subject,
           CASE MOD(r.x, 10)
               WHEN 0 THEN '서비스 안정화를 위한 정기 점검을 진행합니다.

점검 시간 동안 로그인과 파일 업로드가 제한됩니다.
점검 완료 후 별도 공지 없이 정상 이용 가능합니다.'
               WHEN 1 THEN '사내에서 사용하는 문서 서식이 개편되었습니다.

기존 서식으로 작성된 문서는 이달 말까지만 접수됩니다.
이후에는 새 서식으로만 제출해 주시기 바랍니다.'
               WHEN 2 THEN '지하 주차 구역 운영 방식이 변경됩니다.

방문객 전용 구역이 신설되어 임직원 차량은 아래층을 이용해 주세요.
전기차 충전 구역은 기존과 동일하게 운영됩니다.'
               WHEN 3 THEN '미사용 연차에 대한 사용 촉진 제도를 안내드립니다.

잔여 연차가 많은 경우 인사팀에서 개별 안내가 나갑니다.
연말에 몰리지 않도록 미리 일정을 조율해 주세요.'
               WHEN 4 THEN '연간 정보보안 교육 이수 기간입니다.

교육은 온라인으로 진행되며 약 40분이 소요됩니다.
기한 내 미이수 시 계정 권한이 일시 제한될 수 있습니다.'
               WHEN 5 THEN '사무용품 신청 절차가 변경되었습니다.

신청은 매주 수요일까지 접수분을 일괄 처리합니다.
긴급 물품은 총무팀에 별도로 요청해 주세요.'
               WHEN 6 THEN '하반기 워크숍 준비를 위한 사전 설문을 진행합니다.

희망 일정과 프로그램 선호도를 확인하는 문항으로 구성되어 있습니다.
응답에는 5분 정도 소요됩니다.'
               WHEN 7 THEN '출입 카드 재발급 절차를 안내드립니다.

분실 시 즉시 신고해 주셔야 기존 카드가 비활성화됩니다.
재발급까지는 임시 출입증을 사용하시면 됩니다.'
               WHEN 8 THEN '회의실 예약 시스템이 개편되었습니다.

예약 가능 기간이 2주 뒤까지로 확대되었습니다.
노쇼가 반복되면 예약 권한이 제한될 수 있습니다.'
               ELSE '복리후생 포인트 사용 방법을 안내드립니다.

포인트는 매년 초기화되며 이월되지 않습니다.
사용 내역은 마이페이지에서 확인하실 수 있습니다.'
           END AS content,
           CASE MOD(r.x, 6)
               WHEN 0 THEN '관리자'
               WHEN 1 THEN '김도현'
               WHEN 2 THEN '이수민'
               WHEN 3 THEN '박지훈'
               WHEN 4 THEN '최은영'
               ELSE '정하늘'
           END AS writer,
           TIMESTAMPADD(HOUR, 9 + MOD(r.x, 10), TIMESTAMPADD(DAY, r.x - 100, CURRENT_DATE)) AS created_at
    FROM SYSTEM_RANGE(1, 100) r
) v
WHERE NOT EXISTS (SELECT 1 FROM board);
