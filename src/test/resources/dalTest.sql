/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  aar1069
 * Created: Sep 5, 2023
 */

insert into USER_ROLE_TYPE_VAL values(1, 'Role1');
insert into USER_ROLE_TYPE_VAL values(2, 'Role2');

insert into USER_INFO (USER_NUM, USER_ID, DISPLAY_NAME, USER_PWD) values(1, 'TEST1', 'I am a test', 'abcdefg');
insert into USER_INFO (USER_NUM, USER_ID, DISPLAY_NAME, USER_PWD) values(2, 'TEST2', 'I am another test', 'abcdefg');

insert into USER_ROLE(USER_ROLE_NUM, USER_NUM, USER_ROLE_TYPE_CDE) values(100, 2, 2);