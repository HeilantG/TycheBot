drop database if exists `TychePoe`;
create database if not exists `TychePoe` character set UTF8;
use `TychePoe`;

    /**/
    create table if not exists `currency`
    (
        `id` int unsigned primary key auto_increment,
        `name` varchar(255) not null ,
        `translated_name` varchar(255) ,
        `common_name` varchar(255),
        `common_name_2` varchar(255),
        `common_name_3` varchar(255),
        `item_info` varchar(255),
        `chaos_value` double,
        `exalted_value` double,
        `listing_count` double
    )auto_increment = 1000

