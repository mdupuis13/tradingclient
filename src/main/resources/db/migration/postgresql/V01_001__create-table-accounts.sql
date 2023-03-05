set schema 'dbo';

create table if not exists dbo.accounts (
    id                      smallint generated by default as identity primary key,
    questrade_acct_number   varchar(10) not null,
    acct_name               varchar(10) null,
    constraint uniq_acctnumber unique(questrade_acct_number)
);
