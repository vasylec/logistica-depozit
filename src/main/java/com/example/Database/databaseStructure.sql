CREATE DATABASE logisticaDepozit;

-- TABEL: depozite (warehouse)
CREATE TABLE warehouse (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    capacity INT
);

-- TABEL: produse
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    unitPrice DECIMAL(10, 2) NOT NULL,
    volume DECIMAL(10, 2)
);

-- TABEL: furnizori
CREATE TABLE supplier (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    contact VARCHAR(255),
    address TEXT
);

-- TABEL: stoc (inventar în depozit)
CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    warehouseId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (warehouseId) REFERENCES warehouse (id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES product (id) ON DELETE CASCADE
);

-- TABEL: recepții (document de intrare)
CREATE TABLE receipt (
    id SERIAL PRIMARY KEY,
    supplierId INT NOT NULL,
    warehouseId INT NOT NULL,
    receiptDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    documentNumber VARCHAR(100),
    notes TEXT,
    isprocessed BOOLEAN,
    FOREIGN KEY (supplierId) REFERENCES supplier (id) ON DELETE SET NULL,
    FOREIGN KEY (warehouseId) REFERENCES warehouse (id) ON DELETE SET NULL
);

-- TABEL: articole recepționate (produse din recepție)
CREATE TABLE receiptItem (
    id SERIAL PRIMARY KEY,
    receiptId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    unitPrice DECIMAL(10, 2),
    isprocessed BOOLEAN,
    FOREIGN KEY (receiptId) REFERENCES receipt (id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES product (id) ON DELETE CASCADE
);

-- TABEL: mișcări de stoc (opțional, dar foarte util)
CREATE TABLE stockMovement (
    id SERIAL PRIMARY KEY,
    fromWarehouseId INT NOT NULL,
    toWarehouseId INT NOT NULL,
    movementDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reference VARCHAR(255), -- ex: ID recepție
    isprocessed BOOLEAN,
    FOREIGN KEY (fromWarehouseId) REFERENCES warehouse (id),
    FOREIGN KEY (toWarehouseId) REFERENCES warehouse (id)
);

CREATE TABLE stockMovementItem (
    id SERIAL PRIMARY KEY,
    stockMovementId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    isprocessed BOOLEAN,
    FOREIGN KEY (productId) REFERENCES product (id),
    FOREIGN KEY (stockMovementId) REFERENCES stockMovement (id)
);

INSERT INTO
    inventory (
        warehouseId,
        productId,
        quantity
    )
VALUES (1, 1, 120),
    (1, 2, 80),
    (1, 3, 60),
    (1, 4, 200),
    (1, 5, 50),
    (2, 1, 40),
    (2, 2, 25),
    (2, 3, 70),
    (2, 6, 90),
    (2, 7, 110);

INSERT INTO
    receipt (
        supplierId,
        warehouseId,
        documentNumber,
        notes,
        isprocessed
    )
VALUES (
        1,
        2,
        'REC-001',
        'Prima receptie procesata',
        TRUE
    ),
    (
        2,
        2,
        'REC-002',
        'A doua receptie procesata',
        TRUE
    ),
    (
        1,
        2,
        'REC-003',
        'Receptie neprocesata',
        FALSE
    ),
    (
        3,
        2,
        'REC-004',
        'Receptie neprocesata',
        FALSE
    );

INSERT INTO
    receiptItem (
        receiptId,
        productId,
        quantity,
        unitPrice,
        isprocessed
    )
VALUES (1, 5, 30, 15.50, TRUE),
    (1, 6, 20, 12.00, TRUE),
    (2, 2, 40, 10.00, TRUE),
    (2, 3, 35, 8.50, TRUE),
    (3, 8, 50, 6.00, FALSE),
    (3, 9, 60, 7.50, FALSE),
    (4, 10, 25, 14.00, FALSE),
    (4, 11, 45, 9.20, FALSE);

INSERT INTO
    stockMovement (
        fromWarehouseId,
        toWarehouseId,
        reference,
        isprocessed
    )
VALUES (
        1,
        2,
        'Transfer procesat 1',
        TRUE
    ),
    (
        1,
        2,
        'Transfer procesat 2',
        TRUE
    ),
    (
        2,
        1,
        'Transfer neprocesat 1',
        FALSE
    ),
    (
        2,
        1,
        'Transfer neprocesat 2',
        FALSE
    );

INSERT INTO
    stockMovementItem (
        stockMovementId,
        productId,
        quantity,
        isprocessed
    )
VALUES (1, 1, 20, TRUE),
    (1, 2, 15, TRUE),
    (2, 3, 25, TRUE),
    (2, 4, 30, TRUE),
    (3, 5, 10, FALSE),
    (3, 6, 12, FALSE),
    (4, 7, 8, FALSE),
    (4, 8, 18, FALSE);