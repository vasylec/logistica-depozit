INSERT INTO
    warehouse (name, location, capacity)
VALUES (
        'Depozit Central',
        'Chisinau',
        10000
    ),
    ('Depozit Nord', 'Balti', 8000),
    ('Depozit Sud', 'Cahul', 6000),
    ('Depozit Est', 'Orhei', 5000),
    (
        'Depozit Vest',
        'Ungheni',
        7000
    );

INSERT INTO
    supplier (
        name,
        description,
        contact,
        address
    )
VALUES (
        'Supplier A',
        'Electronice și accesorii',
        'contactA@example.com',
        'Chisinau'
    ),
    (
        'Supplier B',
        'Materiale de construcții',
        'contactB@example.com',
        'Balti'
    ),
    (
        'Supplier C',
        'Alimente',
        'contactC@example.com',
        'Cahul'
    ),
    (
        'Supplier D',
        'Mobilier',
        'contactD@example.com',
        'Orhei'
    ),
    (
        'Supplier E',
        'Produse industriale',
        'contactE@example.com',
        'Ungheni'
    );

INSERT INTO
    product (
        name,
        description,
        unit_price,
        volume
    )
VALUES (
        'Frigider Samsung RB34T600FSA',
        'Frigider No Frost, 344L, clasa A+',
        2200.00,
        1.2
    ),
    (
        'Frigider LG GBP62DSNCC',
        'Frigider No Frost, 384L, Smart Inverter',
        2500.00,
        1.25
    ),
    (
        'Frigider Beko RCNA406E60ZXB',
        'Frigider No Frost, 365L, inox',
        1900.00,
        1.15
    ),
    (
        'Frigider Bosch KGN39VWEP',
        'Frigider No Frost, 363L, VitaFresh',
        2600.00,
        1.18
    ),
    (
        'Frigider Whirlpool W7X 82I OX',
        'Frigider No Frost, 335L, inox',
        2400.00,
        1.22
    ),
    (
        'Microunde Samsung MS23K3513AK',
        'Microunde 23L, 800W, grill',
        650.00,
        0.35
    ),
    (
        'Microunde LG MH6535GIS',
        'Microunde 25L, 1000W, grill',
        720.00,
        0.37
    ),
    (
        'Microunde Whirlpool MWP 303 SB',
        'Microunde 30L, 900W',
        680.00,
        0.38
    ),
    (
        'Microunde Bosch FEL053MS2',
        'Microunde 25L, 800W',
        750.00,
        0.36
    ),
    (
        'Microunde Heinner HMW-23GR',
        'Microunde 23L, grill',
        400.00,
        0.34
    ),
    (
        'Masina de spalat LG F4J3TN5WE',
        'Masina de spalat 8kg, 1200 rpm',
        1700.00,
        0.9
    ),
    (
        'Masina de spalat Samsung WW80T304MBW',
        'Masina de spalat 8kg, EcoBubble',
        1800.00,
        0.92
    ),
    (
        'Masina de spalat Bosch WAN28163BY',
        'Masina de spalat 7kg, 1400 rpm',
        2000.00,
        0.9
    ),
    (
        'Masina de spalat Whirlpool FFB 8469 BV EE',
        'Masina de spalat 8kg, FreshCare',
        1500.00,
        0.9
    ),
    (
        'Masina de spalat Beko WTE7612XBW',
        'Masina de spalat 7kg, SteamCure',
        1300.00,
        0.88
    ),
    (
        'Aspirator Philips FC9332/09',
        'Aspirator fara sac, 650W',
        650.00,
        0.45
    ),
    (
        'Aspirator Samsung VC07R302MVB',
        'Aspirator ciclon, 700W',
        720.00,
        0.47
    ),
    (
        'Aspirator Bosch BGC05AAA2',
        'Aspirator fara sac, 700W',
        800.00,
        0.48
    ),
    (
        'Aspirator Dyson V8',
        'Aspirator vertical fara fir',
        2200.00,
        0.55
    ),
    (
        'Aspirator Xiaomi Mi Vacuum',
        'Aspirator vertical, baterie 2500mAh',
        1100.00,
        0.52
    ),
    (
        'Televizor Samsung UE43AU7100',
        'TV LED 43", 4K UHD',
        1500.00,
        0.2
    ),
    (
        'Televizor LG 43UP75003LF',
        'TV LED 43", 4K UHD',
        1450.00,
        0.2
    ),
    (
        'Televizor Philips 43PUS8506',
        'TV LED 43", Ambilight, 4K',
        1700.00,
        0.2
    ),
    (
        'Televizor Sony KD-43X80J',
        'TV LED 43", 4K HDR',
        1900.00,
        0.2
    ),
    (
        'Televizor TCL 43P615',
        'TV LED 43", 4K UHD',
        1200.00,
        0.2
    ),
    (
        'Laptop Lenovo IdeaPad 3',
        'Laptop i5, 8GB RAM, 512GB SSD',
        3200.00,
        0.08
    ),
    (
        'Laptop HP Pavilion 15',
        'Laptop Ryzen 5, 8GB RAM, 512GB SSD',
        3300.00,
        0.08
    ),
    (
        'Laptop ASUS VivoBook 15',
        'Laptop i5, 8GB RAM, 256GB SSD',
        3000.00,
        0.08
    ),
    (
        'Laptop Acer Aspire 5',
        'Laptop Ryzen 5, 8GB RAM, 512GB SSD',
        3100.00,
        0.08
    ),
    (
        'Laptop Dell Inspiron 15',
        'Laptop i5, 8GB RAM, 512GB SSD',
        3400.00,
        0.08
    ),
    (
        'Telefon Samsung Galaxy A54',
        'Smartphone 128GB, 5G',
        1600.00,
        0.02
    ),
    (
        'Telefon iPhone 13',
        'Smartphone 128GB, iOS',
        3500.00,
        0.02
    ),
    (
        'Telefon Xiaomi Redmi Note 12',
        'Smartphone 128GB, 5G',
        1200.00,
        0.02
    ),
    (
        'Telefon OnePlus Nord 2',
        'Smartphone 128GB, 5G',
        1900.00,
        0.02
    ),
    (
        'Telefon Motorola Edge 30',
        'Smartphone 256GB, 5G',
        2100.00,
        0.02
    ),
    (
        'Frigider Arctic AD60340M30W',
        'Frigider 302L, clasa A+',
        1500.00,
        1.1
    ),
    (
        'Frigider Indesit LI8 S2E W',
        'Frigider 339L, No Frost',
        1700.00,
        1.15
    ),
    (
        'Frigider Haier A3FE742CGBJ',
        'Frigider 426L, No Frost',
        2800.00,
        1.25
    ),
    (
        'Frigider Gorenje NRK6192AXL4',
        'Frigider 302L, No Frost',
        2000.00,
        1.12
    ),
    (
        'Frigider Electrolux EN3881MOX',
        'Frigider 357L, No Frost',
        2400.00,
        1.18
    ),
    (
        'Aer conditionat Samsung AR12TXHQASIN',
        'AC 12000 BTU, inverter',
        2800.00,
        0.6
    ),
    (
        'Aer conditionat LG PC12SQ',
        'AC 12000 BTU, inverter',
        3000.00,
        0.6
    ),
    (
        'Aer conditionat Gree GWH12QC',
        'AC 12000 BTU',
        2200.00,
        0.6
    ),
    (
        'Aer conditionat Bosch CL3000i',
        'AC 12000 BTU, inverter',
        3100.00,
        0.6
    ),
    (
        'Aer conditionat Haier Tundra',
        'AC 12000 BTU',
        2400.00,
        0.6
    ),
    (
        'Cuptor electric Bosch HBF133BR0',
        'Cuptor electric 66L',
        1800.00,
        0.5
    ),
    (
        'Cuptor electric Whirlpool AKP 785',
        'Cuptor electric 65L',
        1600.00,
        0.5
    ),
    (
        'Cuptor electric Gorenje BO735E11X',
        'Cuptor electric 71L',
        1500.00,
        0.5
    ),
    (
        'Cuptor electric Electrolux EOF3H50BK',
        'Cuptor electric 65L',
        1700.00,
        0.5
    ),
    (
        'Cuptor electric Samsung NV68R3541RS',
        'Cuptor electric 68L',
        2000.00,
        0.5
    );

INSERT INTO
    receipt (
        supplier_id,
        warehouse_id,
        document_number,
        notes
    )
VALUES (
        1,
        1,
        'REC-2024-001',
        'Recepție frigidere și electronice'
    ),
    (
        2,
        2,
        'REC-2024-002',
        'Materiale construcții'
    ),
    (
        3,
        3,
        'REC-2024-003',
        'Produse alimentare'
    ),
    (
        4,
        4,
        'REC-2024-004',
        'Mobilier și accesorii'
    ),
    (
        5,
        5,
        'REC-2024-005',
        'Produse industriale'
    );

INSERT INTO
    receipt_item (
        receipt_id,
        product_id,
        quantity,
        unit_price
    )
VALUES (1, 1, 20, 2200.00),
    (1, 2, 15, 2500.00),
    (1, 3, 25, 1900.00),
    (1, 4, 10, 2600.00),
    (1, 5, 30, 2400.00),
    (1, 6, 40, 650.00),
    (1, 7, 35, 720.00),
    (1, 8, 20, 680.00),
    (1, 9, 50, 750.00),
    (1, 10, 60, 400.00);

INSERT INTO
    receipt_item (
        receipt_id,
        product_id,
        quantity,
        unit_price
    )
VALUES (2, 11, 18, 1700.00),
    (2, 12, 22, 1800.00),
    (2, 13, 25, 2000.00),
    (2, 14, 30, 1500.00),
    (2, 15, 40, 1300.00),
    (2, 16, 50, 650.00),
    (2, 17, 45, 720.00),
    (2, 18, 20, 800.00),
    (2, 19, 60, 2200.00),
    (2, 20, 55, 1100.00);

INSERT INTO
    receipt_item (
        receipt_id,
        product_id,
        quantity,
        unit_price
    )
VALUES (3, 21, 15, 1500.00),
    (3, 22, 20, 1450.00),
    (3, 23, 25, 1700.00),
    (3, 24, 18, 1900.00),
    (3, 25, 22, 1200.00),
    (3, 26, 30, 3200.00),
    (3, 27, 40, 3300.00),
    (3, 28, 35, 3000.00),
    (3, 29, 28, 3100.00),
    (3, 30, 50, 3400.00);

INSERT INTO
    receipt_item (
        receipt_id,
        product_id,
        quantity,
        unit_price
    )
VALUES (4, 31, 25, 1600.00),
    (4, 32, 30, 3500.00),
    (4, 33, 35, 1200.00),
    (4, 34, 20, 1900.00),
    (4, 35, 18, 2100.00),
    (4, 36, 40, 1500.00),
    (4, 37, 22, 2800.00),
    (4, 38, 10, 2000.00),
    (4, 39, 55, 2400.00),
    (4, 40, 60, 1700.00);

INSERT INTO
    receipt_item (
        receipt_id,
        product_id,
        quantity,
        unit_price
    )
VALUES (5, 41, 20, 2800.00),
    (5, 42, 25, 3000.00),
    (5, 43, 18, 2200.00),
    (5, 44, 22, 3100.00),
    (5, 45, 30, 2400.00),
    (5, 46, 35, 1800.00),
    (5, 47, 40, 1600.00),
    (5, 48, 12, 1500.00),
    (5, 49, 50, 1700.00),
    (5, 50, 45, 2000.00);

INSERT INTO
    stock_movement (
        from_warehouse_id,
        to_warehouse_id,
        reference
    )
VALUES (1, 2, 'MOV-2024-001'),
    (3, 4, 'MOV-2024-002');

INSERT INTO
    stock_movement_item (
        stock_movement_id,
        product_id,
        quantity
    )
VALUES (1, 1, 10),
    (1, 2, 15),
    (1, 3, 20),
    (1, 4, 25),
    (1, 5, 30),
    (1, 6, 18),
    (1, 7, 22),
    (1, 8, 12),
    (1, 9, 17),
    (1, 10, 28);

INSERT INTO
    stock_movement_item (
        stock_movement_id,
        product_id,
        quantity
    )
VALUES (2, 11, 14),
    (2, 12, 19),
    (2, 13, 27),
    (2, 14, 21),
    (2, 15, 16),
    (2, 16, 23),
    (2, 17, 18),
    (2, 18, 12),
    (2, 19, 30),
    (2, 20, 25);