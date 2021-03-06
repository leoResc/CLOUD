# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table current_playlist (
  id                        bigint not null,
  song_id                   bigint,
  constraint pk_current_playlist primary key (id))
;

create table event (
  id                        bigint not null,
  name                      varchar(255),
  password                  varchar(255),
  description               varchar(255),
  begin                     timestamp,
  end                       timestamp,
  constraint pk_event primary key (id))
;

create table event_playlist (
  id                        bigint not null,
  event_id                  bigint,
  playlist_id               bigint,
  constraint pk_event_playlist primary key (id))
;

create table likes (
  id                        bigint not null,
  user_id                   bigint,
  song_id                   bigint,
  constraint pk_likes primary key (id))
;

create table playlist (
  id                        bigint not null,
  name                      varchar(255),
  number_of_songs           integer,
  duration                  bigint,
  song_ids                  varchar(255),
  constraint pk_playlist primary key (id))
;

create table song (
  id                        bigint not null,
  title                     varchar(255),
  artist                    varchar(255),
  genre                     varchar(255),
  duration                  bigint,
  likes                     integer,
  filename                  varchar(255),
  constraint pk_song primary key (id))
;

create table user (
  id                        bigint not null,
  username                  varchar(255),
  constraint pk_user primary key (id))
;

create sequence current_playlist_seq;

create sequence event_seq;

create sequence event_playlist_seq;

create sequence likes_seq;

create sequence playlist_seq;

create sequence song_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists current_playlist;

drop table if exists event;

drop table if exists event_playlist;

drop table if exists likes;

drop table if exists playlist;

drop table if exists song;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists current_playlist_seq;

drop sequence if exists event_seq;

drop sequence if exists event_playlist_seq;

drop sequence if exists likes_seq;

drop sequence if exists playlist_seq;

drop sequence if exists song_seq;

drop sequence if exists user_seq;

