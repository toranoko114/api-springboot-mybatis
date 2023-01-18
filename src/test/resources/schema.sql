CREATE TABLE DEPARTMENT (
  DEPARTMENT_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  DEPARTMENT_NAME VARCHAR(256) NOT NULL,
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE EMPLOYEE (
  EMPLOYEE_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  DEPARTMENT_ID BIGINT NOT NULL,
  EMPLOYEE_NAME VARCHAR(256) NOT NULL,
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT(DEPARTMENT_ID) ON DELETE CASCADE
);

CREATE TABLE HISTORY (
  EMPLOYEE_ID BIGINT NOT NULL,
  DEPARTMENT_ID BIGINT NOT NULL,
  CONTENT VARCHAR(256),
  CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY(`EMPLOYEE_ID`,`DEPARTMENT_ID`)
);