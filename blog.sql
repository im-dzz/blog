drop table if exists blog;
create table blog(
serialno int auto_increment primary key comment '博客id',
title varchar(100) not null unique comment '标题',
sub_title varchar(100) comment '子标题',
content varchar(200) comment '博客存放的路径',
classification varchar(30) comment '类别',
read_num int default(0) comment '阅读量',
create_date datetime comment '创建时间',
update_date datetime comment '更新时间'
);

drop table if exists `comment`;
create table `comment`(
serialno int auto_increment primary key comment '评论id',
blog_id int comment '该评论所属的博客id',
userId int comment '用户id',
content varchar(200) comment '评论内容',
create_date datetime comment '创建时间',
key `idx_comment_blog_id` (`blog_id`)
);