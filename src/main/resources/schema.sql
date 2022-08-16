create table if not exists GENRES
(
    GENRE_ID   INTEGER auto_increment,
    GENRE_NAME CHARACTER VARYING(100) not null,
    constraint GENRE_ID
    primary key (GENRE_ID)
    );

create table if not exists MPA
(
    MPA_ID   INTEGER auto_increment,
    MPA_NAME CHARACTER VARYING(100) not null,
    constraint MPA_ID
    primary key (MPA_ID)
    );

create table if not exists FILMS
(
    FILM_ID      INTEGER auto_increment,
    FILM_NAME    CHARACTER VARYING(100) not null,
    DESCRIPTION  CHARACTER VARYING(250),
    DURATION     INTEGER,
    RELEASE_DATE DATE,
    RATE         INTEGER default 0      not null,
    MPA_ID       INTEGER                not null,
    constraint PK_FILMS
    primary key (FILM_ID),
    constraint FK_FILMS_MPA_ID
    foreign key (MPA_ID) references MPA ON DELETE CASCADE
    );

create table if not exists FILM_GENRE
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint PK_FILM_GENRE
    primary key (FILM_ID, GENRE_ID),
    constraint FK_FILM_GENRE_FILM_ID
    foreign key (FILM_ID) references FILMS ON DELETE CASCADE,
    constraint FK_FILM_GENRE_GENRE_ID
    foreign key (GENRE_ID) references GENRES ON DELETE CASCADE
    );

create table if not exists USERS
(
    USER_ID   INTEGER auto_increment,
    USER_NAME CHARACTER VARYING(100),
    LOGIN     CHARACTER VARYING(50)  not null,
    EMAIL     CHARACTER VARYING(100) not null,
    BIRTHDAY  DATE,

    constraint PK_USERS
    primary key (USER_ID)
    );

create table if not exists FRIENDS
(
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    constraint PK_FRIENDS
    primary key (USER_ID, FRIEND_ID),
    constraint FK_FRIENDS_USER_ID
    foreign key (USER_ID) references USERS ON DELETE CASCADE,
    constraint FK_LIKES_FRIEND_ID
    foreign key (FRIEND_ID) references USERS ON DELETE CASCADE
    );

create table if not exists LIKES
(
    USER_ID INTEGER not null,
    FILM_ID INTEGER not null,
    constraint PK_LIKES
    primary key (USER_ID, FILM_ID),
    constraint FK_LIKES_FILM_ID
    foreign key (FILM_ID) references FILMS ON DELETE CASCADE,
    constraint FK_LIKES_USER_ID
    foreign key (USER_ID) references USERS ON DELETE CASCADE
    );