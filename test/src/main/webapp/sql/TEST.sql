CREATE TABLE MEMBER(
	MID VARCHAR(10) PRIMARY KEY,
	MPW VARCHAR(10) NOT NULL,
	MNAME VARCHAR(20) NOT NULL
);
CREATE TABLE BOARD(
	BID INT PRIMARY KEY,
	MID VARCHAR(10) NOT NULL, -- FK는 반드시 상대 테이블의 PK를 사용
	MSG VARCHAR(50) NOT NULL,
	FAVCNT INT DEFAULT 0,
	RCNT INT DEFAULT 0
);

CREATE TABLE REPLY(
	RID INT PRIMARY KEY,
	MID VARCHAR(10) NOT NULL,
	BID INT NOT NULL,
	RMSG VARCHAR(30) NOT NULL,
	CONSTRAINT BOARD_REPLY FOREIGN KEY (BID) REFERENCES BOARD (BID) ON DELETE CASCADE
);
CREATE TABLE FAV(
	FID INT PRIMARY KEY,
	MID VARCHAR(10) NOT NULL,
	BID INT,
	FAV INT DEFAULT 1,
	CONSTRAINT BOARD_FAV FOREIGN KEY (BID) REFERENCES BOARD (BID) ON DELETE CASCADE
);

INSERT INTO FAV(FID,MID,BID) VALUES((SELECT NVL(MAX(FID),0)+1 FROM FAV),?,?)

INSERT INTO FAVORITE VALUES(1,'timo',13,1);

SELECT COUNT(*) AS CNT FROM FAV WHERE MID='timo' AND BID=13 AND FAV=1;
SELECT SUM(FAV) AS CNT FROM FAV WHERE BID='14' AND FAV=1;


SELECT * FROM USER_TABLES;

SELECT * FROM BOARD;
SELECT * FROM REPLY;
SELECT * FROM MEMBER;
SELECT * FROM FAV;

DROP TABLE TEST;

SELECT COUNT(MID) AS BCNT FROM BOARD WHERE MID='timo';
SELECT COUNT(*) AS RCNT FROM REPLY WHERE MID='timo';

SELECT COUNT(BOARD.MID) AS CNT FROM BOARD JOIN REPLY ON BOARD.MID = REPLY.MID WHERE BOARD.MID='timo';

DELETE FROM BOARD WHERE BID='2';
DELETE FROM MEMBER WHERE MID='111';

SELECT * FROM (SELECT BID,MNAME,MSG,FAVCNT,RCNT FROM BOARD JOIN MEMBER ON BOARD.MID = MEMBER.MID WHERE MNAME LIKE '%'||'티'||'%' ORDER BY BID DESC) WHERE ROWNUM <=2;

SELECT BID,MNAME,MSG,FAVCNT,RCNT FROM BOARD JOIN MEMBER ON BOARD.MID = MEMBER.MID;
WHERE MEMBER.MID='timo' ORDER BY BID DESC;

