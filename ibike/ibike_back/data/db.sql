create database ibike;
use ibike;
create table bike(
	bid bigint primary key auto_increment,
	status int default 0,
	qrcode varchar(100) default '',
	latitude double,
	longitude double
	
)


create table user(
  uid int primary key auto_increment,
  opendid varchar(50) not null,
  name varchar(50) not null,
  phone_num varchar(11),
  id_num varchar(18),
  balance double default 0.0,
  deposit double default 0.0,
  status int default 0
);

drop table user;