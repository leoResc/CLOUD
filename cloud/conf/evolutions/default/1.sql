# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table event (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  password                  varchar(255),
  description               varchar(255),
  begin                     datetime,
  end                       datetime,
  constraint pk_event primary key (id))
;

create table playlist (
  name                      varchar(255) not null,
  duration                  double,
  constraint pk_playlist primary key (name))
;

create table song (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  artist                    varchar(255),
  genre                     varchar(255),
  duration                  bigint,
  user_likes                integer,
  constraint pk_song primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  username                  varchar(255),
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table event;

drop table playlist;

drop table song;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

