DROP TABLE GOOD;
DROP TABLE FEED;
DROP SEQUENCE FEED_NUM_SEQ;
DROP TABLE CHALLENGE;
DROP TABLE GRADE;
DROP TABLE ECO_LV;
DROP TABLE MEMBERS;


-- MEMBERS TABLE 생성 ------------------------------------------------------------------------
CREATE TABLE MEMBERS(
    USER_ID     VARCHAR2(20) PRIMARY KEY,
    USER_PW     VARCHAR2(20) NOT NULL CHECK (LENGTH(USER_PW)>7),
    USER_ALIAS  VARCHAR2(30) UNIQUE NOT NULL CHECK(LENGTH(USER_ALIAS)>1),
    USER_NAME   VARCHAR2(15) NOT NULL,
    USER_EMAIL  VARCHAR2(40) UNIQUE,
    USER_PHONE  CHAR(13) UNIQUE NOT NULL,
    JOIN_DATE   DATE DEFAULT SYSDATE,
    USER_POINT  NUMBER(5) DEFAULT 0 NOT NULL
    );

    -- MEMBERS DATA 입력
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('sophialee328', 'P@ssw0rd123!','마르코', '허윤진', 'example1@gmail.com', '010-4567-8584','16-APR-2023',15500);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT) -- 문제발생
        VALUES('eunbyulwang1','Secur1ty!S','에이스','사쿠라','testemail2@yahoo.com','010-4567-8585','17.Apr.23',250);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('dhkimm1021', 'MyP@ssw0d20', '흰수염', '김채원', 'sample3@hotmail.com', '010-4567-8586', '18.Apr.23', 3650);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('chickeng2', '9@Saf3&!', '아론', '카즈하', 'user4@outlook.com', '010-4567-8587', '19.Apr.23', 4000);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('peaceeeh12', 'Pr1v@te#Data', '와포르', '홍은채', 'myemail5@aol.com', '010-4567-8588', '20.Apr.23', 700);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('eyung1205',	'Pa$$w0rd!P',	'로우',	'오해원', 'email6@protonmail.com', '010-4567-8589',	'21.Apr.23', 8500);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('jessi29', 'C3@CoLLnf', '히루루크', '박진', 'random7@mail.com', '010-4567-8590', '22.Apr.23', 6150);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('danbi0122',	'Str0ng#P@ss', '나미', '설윤아', 'email8@icloud.com', '010-4567-8591', '23.Apr.23', 450);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('dblee0122', 'S3cur3#Acc', '조로', '배진솔', 'newuser9@zoho.com', '010-4567-8592', '24.Apr.23', 2000);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('hey87830', '2F@ctor#Auth',	'로빈',	'김지우', 'email10@inbox.com', '010-4567-8593', '25.Apr.23', 1050);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('sung3062', 'Pr0t3ctM3', '상디', '장규진', 'demo1@fastmail.com', '010-4567-8594', '26.Apr.23',	1300);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('ekthal1022', '@cc3ssC0d3!', '우솝', '방찬', 'email12@yandex.com', '010-4567-8595', '27.Apr.23', 2550);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('aileen960116', 'P@ssPhr@2023', '브룩', '이민호',	'user13@mailinator.com', '010-4567-8596', '28.Apr.23', 3100);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('awaken16', 'Saf3@ua12', '징베',	'서청빈', 'tempry14@tempmail.net', '010-4567-8597', '29.Apr.23',	3850);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('pronoia91',	'F0rt1f13d#', '쵸파', '김승민',	'mail15@guerrillamail.com',	'010-4567-8598', '30.Apr.23', 2650);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('jinkei904',  '7h1s1$Str', '프랑키', '황현진', 'inbox16@cock.li', '010-4567-8599', '1.May.23', 1950);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('woowu6310',	'P@ssw0rd!', '빅맘',	'이용복', 'example17@tutanota.com', '010-4567-8600', '2.May.23', 1500);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('superb1818', '3ncrypt#M', '버기', '한지성',	'testemail18@hushmail.com',	'010-4567-8601', '3.May.23', 2400);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('hjuiop0', 'Gu@rdY0ur', '크로커다일', '양정인', 'user19@dispostable.com', '010-4567-8602', '4.May.23', 4950);
INSERT INTO MEMBERS(USER_ID,USER_PW,USER_ALIAS,USER_NAME,USER_EMAIL,USER_PHONE,JOIN_DATE,USER_POINT)
        VALUES('kiwon511', 'nvuln3r@bl3', '행콕', '김준수', 'email20@10minutemail.com', '010-4567-8603', '5.May.23', 1250);

SELECT * FROM MEMBERS;

-- ECO_LV TABLE 생성 ------------------------------------------------------------------------------------------------------------------
CREATE TABLE ECO_LV(
    LEVELS VARCHAR2(3) PRIMARY KEY,
    POINT NUMBER(3) UNIQUE NOT NULL
);

-- ECO_LV DATA 입력
INSERT INTO ECO_LV(LEVELS, POINT) VALUES('*', 50);
INSERT INTO ECO_LV(LEVELS, POINT) VALUES('**', 100);
INSERT INTO ECO_LV(LEVELS, POINT) VALUES('***', 150);

SELECT * FROM ECO_LV;

-- GRADE TABLE 생성-----------------------------------------------------------------------------------------------------
	CREATE TABLE GRADE(
    HERO_GRADE VARCHAR2(21) PRIMARY KEY,
    LO_POINT NUMBER(4) NOT NULL,
    HI_POINT NUMBER(5) NOT NULL
);

-- GRADE DATA 입력
INSERT INTO GRADE(HERO_GRADE, LO_POINT, HI_POINT) VALUES ('말랑히어로',0,999);
INSERT INTO GRADE(HERO_GRADE, LO_POINT, HI_POINT) VALUES ('든든히어로',1000,1999);
INSERT INTO GRADE(HERO_GRADE, LO_POINT, HI_POINT) VALUES ('베테랑히어로',2000,2999);
INSERT INTO GRADE(HERO_GRADE, LO_POINT, HI_POINT) VALUES ('파워히어로',3000,4999);
INSERT INTO GRADE(HERO_GRADE, LO_POINT, HI_POINT) VALUES ('울트라히어로',5000,99999);

SELECT * FROM GRADE;


-- CHALLENGE TABLE 생성 -------------------------------------------------------------------------------------------------
CREATE TABLE CHALLENGE(
    CHL_NAME VARCHAR2(40) PRIMARY KEY,
    CHL_ICON VARCHAR2(300) UNIQUE NOT NULL,
    CHL_DESC VARCHAR2(300) NOT NULL,
    CHL_DATE DATE DEFAULT SYSDATE,
    CHL_LEVEL VARCHAR2(3) REFERENCES ECO_LV(LEVELS)
);

-- CHALLENGE DATA 입력
INSERT INTO CHALLENGE(CHL_NAME, CHL_ICON, CHL_DESC, CHL_DATE, CHL_LEVEL)
    VALUES('에코백사용하기', '새싹', '에코백을 사용하면 비닐 사용을 줄일 수 있다.' , '7-SEP-2023', '*');

INSERT INTO CHALLENGE(CHL_NAME, CHL_ICON, CHL_DESC, CHL_DATE, CHL_LEVEL)
    VALUES('텀블러사용', '텀블러', '텀블러를 사용하면 일회용품 사용을 줄일 수 있다.', '4-OCT-2022', '**');

INSERT INTO CHALLENGE(CHL_NAME, CHL_ICON, CHL_DESC, CHL_DATE, CHL_LEVEL)
    VALUES('계단이용하기', '계단', '계단을 이용하면 전기사용을 줄이고 건강에 좋다.', '14-AUG-2023', '***');

INSERT INTO CHALLENGE(CHL_NAME, CHL_ICON, CHL_DESC, CHL_DATE, CHL_LEVEL)
    VALUES('대중교통이용하기', '버스', '이산화탄소를 줄여서 환경오염에 도움이 된다.', '21-SEP-2023', '*');

INSERT INTO CHALLENGE(CHL_NAME, CHL_ICON, CHL_DESC, CHL_DATE, CHL_LEVEL)
    VALUES('만보걷기', '걷는사람', '이산화탄소를 더더더 줄이고 건강에도 매우 좋다.', '1-APR-2023', '***');

INSERT INTO CHALLENGE(CHL_NAME, CHL_ICON, CHL_DESC, CHL_DATE, CHL_LEVEL)
    VALUES('분리수거', '쓰레기봉투', '바다 동물들이 고통스럽지 않은 깨끗한 지구를 만든다.', '5-JUN-2023', '**');

SELECT * FROM CHALLENGE;

-- FEED_NUM SEQUENCE 생성 -------------------------------------------------------------------------------------

CREATE SEQUENCE FEED_NUM_SEQ
INCREMENT BY 1
START WITH 1000
MAXVALUE 9999
MINVALUE 1
NOCYCLE
CACHE 2;

-- FEED TABLE 생성
CREATE TABLE FEED (
    FEED_NUM    NUMBER(4) PRIMARY KEY,
    USER_ALIAS  VARCHAR2(30) REFERENCES MEMBERS(USER_ALIAS),
    ECO_IMG     VARCHAR2(300) NOT NULL,
    CHL_NAME    VARCHAR2(40) REFERENCES CHALLENGE(CHL_NAME),
    ECO_TXT     VARCHAR2(300)
);

-- FEED DATA 입력
INSERT INTO FEED(FEED_NUM, USER_ALIAS, ECO_IMG, CHL_NAME, ECO_TXT)
        VALUES(FEED_NUM_SEQ.NEXTVAL, '마르코', '텀블러.png', '텀블러사용', '스벅에서 텀블러 사용하고 300원 할인 받았어요 ㅎㅎ' );
INSERT INTO FEED(FEED_NUM, USER_ALIAS, ECO_IMG, CHL_NAME, ECO_TXT)
        VALUES(FEED_NUM_SEQ.NEXTVAL, '에이스', '계단.png', '계단이용하기', '17층에 살지만 하루 한번은 계단 이용하는 중:)' );
INSERT INTO FEED(FEED_NUM, USER_ALIAS, ECO_IMG, CHL_NAME, ECO_TXT)
        VALUES(FEED_NUM_SEQ.NEXTVAL, '흰수염', '버스.png', '대중교통이용하기', '매일 지각해서 택시를 자주 이용했는데, 이제는 환경 보호를 위해 일찍 일어나서 대중교통 타기 실천중이에요~' );
INSERT INTO FEED(FEED_NUM, USER_ALIAS, ECO_IMG, CHL_NAME, ECO_TXT)
        VALUES(FEED_NUM_SEQ.NEXTVAL, '아론', '걷기.png', '만보걷기', '만보 걷기로 건강도 지키고 환경도 지켰다!' );
INSERT INTO FEED(FEED_NUM, USER_ALIAS, ECO_IMG, CHL_NAME, ECO_TXT)
        VALUES(FEED_NUM_SEQ.NEXTVAL, '와포르', '분리수거통.png', '분리수거', '소비를 줄일 수 없다면 분리수거 라도...' );

SELECT * FROM FEED;

-- GOOD TABLE 생성-------------------------------------------------------------------------------------------------
CREATE TABLE GOOD(
    FEED_NUM NUMBER(4) REFERENCES FEED (FEED_NUM),
    USER_ID VARCHAR2(15) REFERENCES MEMBERS (USER_ID)
);

-- GOOD DATA 입력
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1000,'sophialee328');
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1000, 'sung3062');
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1000, 'chickeng2');
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1000, 'jinkei904');
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1001, 'sophialee328');
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1002, 'dblee0122');
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1002, 'sophialee328');
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1003, 'sophialee328');
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1004, 'sophialee328');
INSERT INTO GOOD(FEED_NUM, USER_ID) VALUES(1004, 'jessi29');



-- 1001 게시글에 대한 좋아요 수 조회
SELECT COUNT(*) AS GOODNUM
FROM GOOD
WHERE FEED_NUM = 1000;


COMMIT;