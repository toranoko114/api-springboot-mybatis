-- マスタデータ投入
INSERT INTO DEPARTMENT (DEPARTMENT_NAME) values ('無所属'),('営業部'),('企画部'),('開発部');
-- ダミーデータ投入
INSERT INTO EMPLOYEE (DEPARTMENT_ID, EMPLOYEE_NAME, GENDER) VALUES (1, 'ダミー', 'FEMALE');
INSERT INTO PERSONAL_DATA (EMPLOYEE_ID, BIRTHDAY, TELEPHONE_NUMBER, MAIL_ADDRESS) VALUES (1, '2023-01-01', '00000000000', 'mail@gmail.com');
INSERT INTO HISTORY (EMPLOYEE_ID, DEPARTMENT_ID, CONTENT) VALUES (1, 2, 'ダミー入社');
INSERT INTO HISTORY (EMPLOYEE_ID, DEPARTMENT_ID, CONTENT) VALUES (1, 1, 'ダミー退職');
