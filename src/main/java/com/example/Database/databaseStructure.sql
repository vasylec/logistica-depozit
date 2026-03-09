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
    FOREIGN KEY (fromWarehouseId) REFERENCES warehouse (id),
    FOREIGN KEY (toWarehouseId) REFERENCES warehouse (id)
);

CREATE TABLE stockMovementItem (
    id SERIAL PRIMARY KEY,
    stockMovementId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (productId) REFERENCES product (id),
    FOREIGN KEY (stockMovementId) REFERENCES stockMovement (id)
);