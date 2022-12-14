-- 단어 관련 테이블

-- 단어 테이블
CREATE TABLE WORD (
	WORD_NO NUMBER CONSTRAINT WORD_NO_PK PRIMARY KEY,
	WORD_NM VARCHAR2(30) CONSTRAINT WORD_NAME_NN NOT NULL,
	DELETE_FL CHAR(1) DEFAULT 'N' CONSTRAINT DELETE_FL_CK CHECK(DELETE_FL IN('Y', 'N'))  CONSTRAINT DELETE_FL_NN NOT NULL
);
COMMENT ON COLUMN WORD.WORD_NO IS '단어 번호';
COMMENT ON COLUMN WORD.WORD_NAME IS '단어 이름';
COMMENT ON COLUMN WORD.DELETE_FL IS '삭제 여부';
SELECT * FROM WORD;

-- 단어 테이블 PK 시퀀스
CREATE SEQUENCE SEQ_WORD_PK NOCACHE;

-- 품사 테이블
CREATE TABLE PART_OF_SPEECH (
	POS_CODE VARCHAR2(10) CONSTRAINT POS_CODE_PK PRIMARY KEY,
	POS_NAME VARCHAR2(15) CONSTRAINT POS_NAME_NN NOT NULL
);
COMMENT ON COLUMN PART_OF_SPEECH.POS_CODE IS '품사 코드';
COMMENT ON COLUMN PART_OF_SPEECH.POS_NAME IS '품사 이름';

-- 품사 테이블 INIT
INSERT INTO PART_OF_SPEECH VALUES('n', '명사');
INSERT INTO PART_OF_SPEECH VALUES('a', '형용사');
INSERT INTO PART_OF_SPEECH VALUES('v', '동사');
INSERT INTO PART_OF_SPEECH VALUES('ad', '부사');
INSERT INTO PART_OF_SPEECH VALUES('pron', '대명사');
INSERT INTO PART_OF_SPEECH VALUES('prep', '전치사');
INSERT INTO PART_OF_SPEECH VALUES('conj', '접속사');
INSERT INTO PART_OF_SPEECH VALUES('int', '감탄사');
COMMIT;
SELECT * FROM PART_OF_SPEECH;

-- 단어 뜻 테이블
DROP TABLE MEANING;
CREATE TABLE MEANING(
	MEANING_NO NUMBER CONSTRAINT MEANING_NO_PK PRIMARY KEY,
	MEANING_NM VARCHAR(90) CONSTRAINT MEANING_NM_NN NOT NULL,
	WORD_NO NUMBER CONSTRAINT MEANING_WORD_NO REFERENCES WORD (WORD_NO),
	POS_CODE VARCHAR2(10) CONSTRAINT MEANING_POS_CODE REFERENCES PART_OF_SPEECH (POS_CODE),
	DELETE_FL CHAR(1) DEFAULT 'N' CONSTRAINT MEANING_DELETE_FL_CK CHECK(DELETE_FL IN('Y', 'N')) CONSTRAINT MEANING_DELETE_FL_NN NOT NULL
);
COMMENT ON COLUMN MEANING.MEANING_NO IS '뜻 번호';
COMMENT ON COLUMN MEANING.MEANING_NM IS '뜻 이름';
COMMENT ON COLUMN MEANING.WORD_NO IS '단어 번호';
COMMENT ON COLUMN MEANING.POS_CODE IS '품사 코드';

-- 단어 뜻 시퀀스
DROP SEQUENCE SEQ_MEANING_PK;
CREATE SEQUENCE SEQ_MEANING_PK NOCACHE;

DROP SEQUENCE SEQ_WORD_PK;
CREATE SEQUENCE SEQ_WORD_PK NOCACHE;

-- 단어 샘플 데이터
SELECT * FROM WORD;
DELETE FROM WORD;

SELECT * FROM MEANING;
INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'release', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '놓아주다', 1, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '발표하다', 1, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '면하게하다', 1, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '', 1, 'n', DEFAULT);
SELECT * FROM MEANING;
COMMIT;

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'attempt', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '시도하다', 2, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '시도, 노력', 2, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'merely', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '단지, 그저', 3, 'ad', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'optimistic', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '낙관적인', 4, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'skeptical', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '회의적인', 5, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'diversify', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '다양화하다', 6, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '~하는 데 변화를 주다', 6, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'accuse', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '고발하다', 7, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '비난하다', 7, 'v', DEFAULT);
COMMIT;

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'accuse', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, 'include', 8, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'approach', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '~에 접근하다', 9, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '접근(법)', 9, 'n', DEFAULT);
COMMIT;

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'nevertheless', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '그럼에도 불구하고', 10, 'ad', DEFAULT);
COMMIT;

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'reliable', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '믿을 수 있는', 11, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'capital', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '수도', 12, 'n', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '자본', 12, 'n', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '대문자', 12, 'n', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '자본의', 12, 'a', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '대문자의', 12, 'a', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '(죄가) 사형감인', 12, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'adjust', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '조정하다', 13, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '적응하다', 13, 'v', DEFAULT);

INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, ?, ?, (SELECT POS_CODE FROM PART_OF_SPEECH WHERE POS_NM = ?), DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'predict', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '예측하다', 17, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'appoint', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '지명하다', 18, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '약속하다', 18, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'censor', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '검열관', 19, 'n', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '검열하다', 19, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'alternative', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '대안, 양자택일', 20, 'n', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '대신의, 약자택일의', 20, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'variable', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '변하기 쉬운, 변덕스러운', 21, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'various', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '여러가지의, 다양한', 22, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'varied', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '가지각색의', 23, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'locate', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '(위치를) 알아내다', 24, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '~에 위치하다', 24, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'celebrity', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '유명 인사, 명성', 25, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'barrier', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '방벽, 국경의 요새', 26, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'anticipate', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '예상하다, 기대하다', 27, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'aware', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '알아차린, 감지한', 28, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'caution', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '조심, 경고', 29, 'n', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '~에게 경고하다', 29, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'hospitality', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '환대, 친절히 접대함', 30, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'hostility', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '적의, 적개심', 31, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'commit', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '(죄를) 범하다', 32, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '약속하다', 32, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'hence', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '따라서, 그러므로, 향후', 33, 'ad', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'achieve', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '이루다, 성취하다', 34, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'throughout', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '[장소] ~ 도처에', 35, 'prep', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '[시간] ~ 동안 내내', 35, 'prep', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'contend', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '싸우다, 경쟁하다', 36, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'steep', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '가파른', 37, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'ancestor', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '조상, 선조', 38, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'descendant', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '자손, 후예', 39, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'perceive', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '인지하다', 40, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'combine', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '결합[병합]시키다', 41, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'proceed', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '계속하다, 나아가다, 시작하다', 42, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'obvious', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '뚜렷한, 알기 쉬운', 43, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'apply', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '신청하다', 44, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '적용하다', 44, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '사용하다', 44, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'concentrate', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '집중하다', 45, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'crisis', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '위기, 결정적인 시기', 46, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'inclined', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '~할 마음이 있는', 47, 'a', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '~하는 경향이 있는', 47, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'permanent', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '영속적인, 영구적인', 48, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'temporary', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '임시적인, 잠시의', 49, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'occasion', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '(특정한) 때, 경우', 50, 'n', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '특별한 일(행사)', 50, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'conceal', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '숨기다, 감추다', 51, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'reveal', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '드러내다, 누설하다', 52, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'concern', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '관심(걱정), 관계', 53, 'n', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '걱정하다, 관계하다', 53, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'classify', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '분류하다', 54, 'v', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'circumstance', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '상황, 환경, 처지', 55, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'involved', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '복잡한', 56, 'a', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'spell', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '(글자를)철자하다', 57, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '마법, 기간', 57, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'exterior', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '바깥쪽의, 외부의', 58, 'a', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '외부', 58, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'respect', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '존경하다, 지키다', 59, 'v', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '존경, 관점', 59, 'n', DEFAULT);

INSERT INTO WORD VALUES (SEQ_WORD_PK.NEXTVAL, 'clarify', DEFAULT);
INSERT INTO MEANING VALUES (SEQ_MEANING_PK.NEXTVAL, '명백하게하다, 분명해지다', 60, 'v', DEFAULT);

COMMIT;

SELECT * FROM WORD;
SELECT * FROM MEANING;
SELECT * FROM PART_OF_SPEECH;
COMMIT;

