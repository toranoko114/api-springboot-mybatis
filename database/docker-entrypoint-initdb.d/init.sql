CREATE TABLE IF NOT EXISTS DEPARTMENT (
  DEPARTMENT_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  DEPARTMENT_NAME VARCHAR(256) NOT NULL,
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS EMPLOYEE (
  EMPLOYEE_ID VARCHAR(10) NOT NULL PRIMARY KEY,
  DEPARTMENT_ID BIGINT NOT NULL,
  EMPLOYEE_NAME VARCHAR(256) NOT NULL,
  GENDER VARCHAR(256) NOT NULL,
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS PERSONAL (
  EMPLOYEE_ID VARCHAR(10) NOT NULL PRIMARY KEY,
  BIRTHDAY DATE NOT NULL,
  TELEPHONE_NUMBER VARCHAR(256) NOT NULL,
  MAIL_ADDRESS VARCHAR(256) NOT NULL,
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS HISTORY (
  EMPLOYEE_ID VARCHAR(10) NOT NULL,
  START_DATE DATE NOT NULL,
  DEPARTMENT_ID BIGINT NOT NULL,
  CONTENT VARCHAR(256),
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT UQ_HISTORY UNIQUE (EMPLOYEE_ID, START_DATE, DEPARTMENT_ID)
);

INSERT INTO DEPARTMENT (DEPARTMENT_NAME) values ('無所属'),('営業部'),('企画部'),('開発部');
INSERT INTO EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_NAME, DEPARTMENT_ID, GENDER) VALUES ('dummy', 'ダミー', 1, 'FEMALE');
INSERT INTO PERSONAL (EMPLOYEE_ID, BIRTHDAY, TELEPHONE_NUMBER, MAIL_ADDRESS) VALUES ('dummy', '2023-01-01', '00000000000', 'dummy@gmail.com');
INSERT INTO HISTORY (EMPLOYEE_ID, START_DATE, DEPARTMENT_ID, CONTENT) VALUES ('dummy', '2022-01-01', 2, 'ダミー入社');
INSERT INTO HISTORY (EMPLOYEE_ID, START_DATE, DEPARTMENT_ID, CONTENT) VALUES ('dummy', '2023-01-01', 1, 'ダミー退職');