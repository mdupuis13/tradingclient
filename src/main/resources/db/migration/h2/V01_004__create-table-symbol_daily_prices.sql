set schema dbo;

create table if not exists dbo.symbol_daily_prices (
    id          integer generated by default as identity primary key,
    symbol_id   smallint not null,
    price_date  date not null,
    open_price  numeric(11,5) null,
    close_price numeric(11,5) not null,
    low_price   numeric(11,5) null,
    high_price  numeric(11,5) null
);

alter table symbol_daily_prices 
add constraint fk_sdp_symbol_id foreign key (symbol_id) references symbols(id);