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

# 聊天记录数据库
drop table if exists msg;

create table msg
(
    time    timestamp, -- 消息时间
    sender  varchar(20),
    message varchar(20)
) char set utf8;

# 用户登录次数记录表
drop table if exists logTimes;
create table logTimes
(
    id       int,
    name     varchar(20),
    logtimes int
) char set utf8;

#冻结登录表
drop table if exists frozentable;
create table frozentable
(
    id       int,
    name     varchar(20),
    postTime datetime
) char set utf8;

#失效密码设置
drop table if exists keyValid;
create table keyValid
(
    name     varchar(20),
    oldkey   varchar(20),
    newkey   varchar(20),
    modeTime datetime
);
drop table if exists userMsg;
create table userMsg
(
    id           int,
    ipAddress    varchar(20),
    os           varchar(20),
    device       varchar(20), -- 用户使用的浏览器信息
    networkSpeed int,
    time         datetime     -- 登录时间
);

drop table if exists IP;
create table IP
(
    ip varchar(20)
);

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

insert into msg
values (now(), 'root', '这时聊天记录数据库');
