set schema dbo;

create table if not exists dbo.account_activities (
    id              integer generated by default as identity primary key,
    acct_id         smallint not null,
    symbol_id       smallint not null,
    settlement_date date not null,
    acct_action     varchar(10) null,
    currency        char(3) not null,
    quantity        numeric(8,2),
    price           numeric(11,5),
    grossAmount     numeric(14,5),
    commission      numeric(12,2),
    net_amount      numeric(14,5),
    actvt_type      varchar(25) not null
);

alter table account_activities
add constraint fk_aa_account_id foreign key (acct_id) references accounts(id);

alter table account_activities 
add constraint fk_aa_symbol_id foreign key (symbol_id) references symbols(id);