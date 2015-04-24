# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table acsi.game (
  id                        integer auto_increment not null,
  Date                      datetime,
  constraint pk_game primary key (id))
;

create table acsi.player (
  id                        integer auto_increment not null,
  pseudo                    varchar(255),
  nom                       varchar(255),
  prenom                    varchar(255),
  vip                       tinyint,
  constraint pk_player primary key (id))
;

create table acsi.scoreboard (
  id                        integer auto_increment not null,
  idPlayer                  integer,
  idGame                    integer,
  constraint uq_acsi_scoreboard_1 unique (idPlayer,idGame),
  constraint pk_scoreboard primary key (id))
;

create table acsi.shot (
  id                        integer auto_increment not null,
  skittlesFall              integer,
  idTurn                    integer,
  constraint pk_shot primary key (id))
;

create table acsi.turn (
  id                        integer auto_increment not null,
  number                    integer,
  result                    integer,
  cumul                     integer,
  idScoreboard              integer,
  state                     varchar(7),
  nb_skittles               integer,
  constraint ck_turn_state check (state in ('STRIKE','SPARE','CLASSIC')),
  constraint pk_turn primary key (id))
;

alter table acsi.scoreboard add constraint fk_acsi_scoreboard_player_1 foreign key (idPlayer) references acsi.player (id) on delete restrict on update restrict;
create index ix_acsi_scoreboard_player_1 on acsi.scoreboard (idPlayer);
alter table acsi.scoreboard add constraint fk_acsi_scoreboard_game_2 foreign key (idGame) references acsi.game (id) on delete restrict on update restrict;
create index ix_acsi_scoreboard_game_2 on acsi.scoreboard (idGame);
alter table acsi.shot add constraint fk_acsi_shot_turn_3 foreign key (idTurn) references acsi.turn (id) on delete restrict on update restrict;
create index ix_acsi_shot_turn_3 on acsi.shot (idTurn);
alter table acsi.turn add constraint fk_acsi_turn_scoreboard_4 foreign key (idScoreboard) references acsi.scoreboard (id) on delete restrict on update restrict;
create index ix_acsi_turn_scoreboard_4 on acsi.turn (idScoreboard);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table acsi.game;

drop table acsi.player;

drop table acsi.scoreboard;

drop table acsi.shot;

drop table acsi.turn;

SET FOREIGN_KEY_CHECKS=1;

