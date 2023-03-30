-- 编写建库建表 sql
create database if not exists mange;

use mange;

-- 创建公示表
drop table if exists user;

create table user
(
    id       int primary key auto_increment,
    name     varchar(20) unique, -- 登录的用户名不能重复
    password varchar(128)
) char set utf8;

-- 创建公文表
drop table if exists paper;

create table paper
(
    paperId  int primary key auto_increment,
    title    varchar(1024),
    content  mediumtext, -- 文章内容
    userId   int,        -- 文章的作者id
    postTime datetime,
    pass     int         -- 文章是否通过 1.通过 0.未通过 2未审核
) char set utf8;

# 货物表
drop table if exists goods;

create table goods
(
    id       int primary key auto_increment,
    name     varchar(1024),
    price    varchar(20),
    quantity varchar(20)
) char set utf8;

# 资金表
drop table if exists money;
create table money
(
    time    timestamp,
    in_num  int, -- 入账
    out_num int, -- 出账
    remain  int  -- 剩余
) char set utf8;

insert into user
values (null, 'root', '000000');
insert into user
values (null, 'gby', '123');

insert into paper
values (null, 'root用户公文1', '中北大学信息对抗公文公示1', 1, now(), 1);
insert into paper
values (null, 'root用户公文2', '中北大学信息对抗公文公示2', 1, now(), 0);
insert into paper
values (null, 'root用户公文3', '中北大学信息对抗公文公示3', 1, now(), 2);

insert into money
values ('2023-3-15 00:00:00', 6000, 1000, 5000);
