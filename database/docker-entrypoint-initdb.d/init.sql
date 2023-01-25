CREATE TABLE IF NOT EXISTS DEPARTMENT (
  DEPARTMENT_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  DEPARTMENT_NAME VARCHAR(256) NOT NULL,
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS EMPLOYEE (
  EMPLOYEE_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  DEPARTMENT_ID BIGINT NOT NULL,
  EMPLOYEE_NAME VARCHAR(256) NOT NULL,
  GENDER VARCHAR(256) NOT NULL,
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT(DEPARTMENT_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PERSONAL_DATA (
  EMPLOYEE_ID BIGINT NOT NULL PRIMARY KEY,
  BIRTHDAY DATE NOT NULL,
  TELEPHONE_NUMBER VARCHAR(256) NOT NULL,
  MAIL_ADDRESS VARCHAR(256) NOT NULL,
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS HISTORY (
  HISTORY_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  START_DATE DATE NOT NULL,
  EMPLOYEE_ID BIGINT NOT NULL,
  DEPARTMENT_ID BIGINT NOT NULL,
  CONTENT VARCHAR(256),
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT UQ_HISTORY UNIQUE (START_DATE, EMPLOYEE_ID, DEPARTMENT_ID)
);

INSERT INTO DEPARTMENT (DEPARTMENT_NAME) values ('無所属'),('営業部'),('企画部'),('開発部');
INSERT INTO EMPLOYEE (DEPARTMENT_ID, EMPLOYEE_NAME, GENDER) VALUES (1, 'ダミー', 'FEMALE');
INSERT INTO PERSONAL_DATA (EMPLOYEE_ID, BIRTHDAY, TELEPHONE_NUMBER, MAIL_ADDRESS) VALUES (1, '2023-01-01', '00000000000', 'mail@gmail.com');
INSERT INTO HISTORY (START_DATE, EMPLOYEE_ID, DEPARTMENT_ID, CONTENT) VALUES ('2022-01-01', 1, 2, 'ダミー入社');
INSERT INTO HISTORY (START_DATE, EMPLOYEE_ID, DEPARTMENT_ID, CONTENT) VALUES ('2023-01-01', 1, 1, 'ダミー退職');