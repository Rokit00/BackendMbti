USE weing;

-- MEMBER 테이블 생성
create table member
(
    ID       bigint auto_increment        primary key,
    USER_ID  varchar(255) charset utf8mb4 not null,
    PASSWORD varchar(255) charset utf8mb4 not null,
    NICKNAME varchar(255) charset utf8mb4 not null,
    EMAIL    varchar(255) charset utf8mb4 null,
    BIRTHDAY date                         null,
    GENDER   varchar(255) charset utf8mb4 null
);

-- DEBATE_WRITE 테이블 생성
create table debate_write
(
    WRITER_NUM bigint auto_increment        primary key,
    NICKNAME   varchar(255) charset utf8mb4 null,
    TITLE      varchar(255) charset utf8mb4 not null,
    OPTION_A   varchar(255) charset utf8mb4 not null,
    OPTION_B   varchar(255) charset utf8mb4 not null,
    CREATE_AT  timestamp                    null,
    HIT        int                          null,
    LIKES      int                          null,
    BOOKMARK   tinyint(1)                   null
);

-- DEBATE_COMMENT 테이블 생성
create table debate_comment
(
    ID        bigint auto_increment        primary key,
    WRITE_NUM bigint                       not null,
    MEMBER_ID bigint                       not null,
    USER_ID   varchar(255) charset utf8mb4 null,
    NICKNAME  varchar(255) charset utf8mb4 null,
    OPT       char charset utf8mb4         not null,
    COMMENT   varchar(255) charset utf8mb4 not null,
    LIKES     int                          null,
    CREATE_AT timestamp                    null,
    constraint MEMBER_ID__FK        foreign key (MEMBER_ID) references member (ID),
    constraint WRITE_NUM__FK        foreign key (WRITE_NUM) references debate_write (WRITER_NUM)
);
