create table UserInf
(
    username varchar(50) not null,
    passwords varchar(50) not null,
    authority_id int not null,
    highscore int,
    primary key(username)
);

create table authority(
    authority_id int,
    authority_name varchar(50),
    primary key(authority_id)
);

create table themes(
    themeId int,
    themeName varchar(20),
    primary key(themeId)
);

create table pictureInfo(
    pictureId int identity,
    pictureName varchar(20),
    themeId int,
    url varchar(300),
    primary key(pictureId)
);