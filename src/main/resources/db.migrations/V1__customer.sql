create table tasktesttheraven.customer
(
    id        bigint auto_increment
        primary key,
    created   bigint       not null,
    updated   bigint       not null,
    full_name varchar(50)  not null,
    email     varchar(100) not null,
    phone     varchar(14) null,
    is_active tinyint(1) not null,
    constraint email
        unique (email),
    check (length(`full_name`) >= 2),
    check (`email` like '%@%\'),
    check ((`phone` like '+%\') or (`phone` is null))
);

