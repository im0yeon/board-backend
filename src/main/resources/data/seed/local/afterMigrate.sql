-- 로컬 확인용 테스트 게시글 - 게시글이 하나도 없을 때만 넣는다
INSERT INTO article (title, content, writer, notice_yn, created_at, updated_at)
SELECT v.title, v.content, v.writer, v.notice_yn, v.created_at, v.created_at
FROM (
    SELECT '게시판 운영 정책 안내' AS title,
           '게시판 이용 시 지켜주셔야 할 기본 정책을 안내드립니다.

- 타인을 비방하거나 개인정보를 포함한 글은 사전 통보 없이 삭제됩니다.
- 동일한 내용을 반복 등록하는 경우 작성이 제한될 수 있습니다.
- 업무와 무관한 홍보성 게시물은 허용하지 않습니다.' AS content,
           '관리자' AS writer,
           'Y' AS notice_yn,
           TIMESTAMPADD(HOUR, 9, TIMESTAMPADD(DAY, -11, CURRENT_DATE)) AS created_at
    UNION ALL
    SELECT '사내 문서 서식 개편 안내',
           '2026년부터 사용하는 사내 문서 서식이 개편되었습니다.

기존 서식으로 작성된 문서는 3월 말까지만 접수되며,
이후에는 새 서식으로만 제출 가능합니다.',
           '김도현', 'N', TIMESTAMPADD(HOUR, 10, TIMESTAMPADD(DAY, -10, CURRENT_DATE))
    UNION ALL
    SELECT '주차장 이용 규칙 변경',
           '지하 2층 주차 구역이 방문객 전용으로 전환됩니다.

임직원 차량은 지하 3층부터 이용해 주시기 바랍니다.
전기차 충전 구역은 기존과 동일하게 운영됩니다.',
           '이수민', 'N', TIMESTAMPADD(HOUR, 11, TIMESTAMPADD(DAY, -9, CURRENT_DATE))
    UNION ALL
    SELECT '연차 사용 촉진 제도 시행',
           '미사용 연차에 대한 사용 촉진 제도가 시행됩니다.

잔여 연차가 5일 이상인 경우 담당자가 개별 안내드릴 예정이며,
사용 계획서는 이달 말까지 제출해 주시면 됩니다.',
           '박지훈', 'N', TIMESTAMPADD(HOUR, 13, TIMESTAMPADD(DAY, -8, CURRENT_DATE))
    UNION ALL
    SELECT '보안 교육 이수 요청',
           '전 직원 대상 정보보호 교육이 열립니다.

온라인 과정으로 진행되며 총 2시간 분량입니다.
미이수 시 다음 분기 시스템 접근 권한이 제한될 수 있습니다.',
           '관리자', 'N', TIMESTAMPADD(HOUR, 14, TIMESTAMPADD(DAY, -7, CURRENT_DATE))
    UNION ALL
    SELECT '사무용품 신청 절차 변경',
           '사무용품 신청이 그룹웨어로 일원화됩니다.

기존 메일 신청은 더 이상 처리되지 않으니
신청 메뉴를 통해 접수해 주시기 바랍니다.',
           '최은영', 'N', TIMESTAMPADD(HOUR, 15, TIMESTAMPADD(DAY, -6, CURRENT_DATE))
    UNION ALL
    SELECT '2026년 하반기 시스템 정기 점검 안내',
           '서비스 안정화를 위한 하반기 정기 점검을 아래와 같이 진행합니다.
점검 시간 동안 일부 서비스 이용이 제한되오니 업무에 참고 부탁드립니다.

■ 점검 일시
2026년 9월 6일(토) 02:00 ~ 06:00 (4시간)

■ 점검 대상
- 관리자 콘솔 전체
- 결제 및 정산 모듈
- 알림 발송 서버

■ 영향 범위
점검 시간 중에는 로그인 및 게시글 등록·수정이 불가합니다.',
           '관리자', 'Y', TIMESTAMPADD(HOUR, 16, TIMESTAMPADD(DAY, -5, CURRENT_DATE))
    UNION ALL
    SELECT '동호회 지원금 신청 접수',
           '하반기 동호회 지원금 신청을 받습니다.

활동 계획서와 전년도 결산 자료를 함께 제출해 주세요.
지원 규모는 동호회 인원에 따라 차등 적용됩니다.',
           '정민재', 'N', TIMESTAMPADD(HOUR, 9, TIMESTAMPADD(DAY, -4, CURRENT_DATE))
    UNION ALL
    SELECT '사옥 소방 훈련 실시',
           '전 층 대상 소방 대피 훈련을 실시합니다.

훈련 중에는 엘리베이터 사용이 중단되며,
안내 방송에 따라 비상 계단으로 이동해 주시기 바랍니다.',
           '이수민', 'N', TIMESTAMPADD(HOUR, 10, TIMESTAMPADD(DAY, -3, CURRENT_DATE))
    UNION ALL
    SELECT '휴게실 리모델링 일정 공유',
           '각 층 휴게실 리모델링이 순차적으로 진행됩니다.

공사 중인 층의 휴게실은 이용할 수 없으며,
인접 층 휴게실을 이용해 주시기 바랍니다.',
           '최은영', 'N', TIMESTAMPADD(HOUR, 11, TIMESTAMPADD(DAY, -2, CURRENT_DATE))
    UNION ALL
    SELECT '워크숍 사전 설문 요청',
           '하반기 워크숍 준비를 위한 사전 설문을 진행합니다.

희망 일정과 프로그램 선호도를 확인하는 문항으로 구성되어 있으며,
응답에는 5분 정도 소요됩니다.',
           '박지훈', 'N', TIMESTAMPADD(HOUR, 13, TIMESTAMPADD(DAY, -1, CURRENT_DATE))
) v
WHERE NOT EXISTS (SELECT 1 FROM article);
