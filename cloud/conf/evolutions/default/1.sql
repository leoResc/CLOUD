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




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table event;

SET FOREIGN_KEY_CHECKS=1;

