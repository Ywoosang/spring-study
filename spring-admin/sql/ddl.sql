drop table if exists member CASCASE;

create table member
(
    id bigint generated by default as identity,
    name varhar(255),
    primary key (id)
)