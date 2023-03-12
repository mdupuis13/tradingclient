insert into dbo.account_activities (acct_id, symbol_id, settlement_date, acct_action, currency, quantity, price, grossAmount, commission, net_amount, actvt_type)
values (1, 2, '2021-02-19', 'Sell', 'USD', -15, 89.5417, 1343.13, -4.98,  1338.15, 'Trades'),
       (1, 0, '2021-02-17', 'FXT',  'USD',   0,       0,       0,     0, -1039.67, 'FX conversion'),
       (1, 0, '2021-02-17', 'FXT',  'CAD',   0,       0,       0,     0,     1300, 'FX conversion'),
       (1, 0, '2021-02-22', 'WDR',  'CAD',   0,       0,       0,     0,    -1300, 'Withdrawals'),
       (1, 3, '2021-05-24', 'Buy',  'USD',  20, 12.7671, -255.34, -4.95,  -260.29, 'Trades'),
       (1, 3, '2021-07-06', 'Sell', 'USD',  20, 16.8176,  336.35, -4.96,   331.39, 'Trades'),
       (1, 4, '2021-01-15', 'Buy',  'CAD',  10,   37.72,  -377.2, -4.99,  -382.19, 'Trades'),
       (1, 4, '2021-07-22', 'DIV',  'CAD',   0,       0,       0,     0,     0.88, 'Dividends');

