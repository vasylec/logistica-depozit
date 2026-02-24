CREATE DATABASE logistica_depozit;

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
    unit_price DECIMAL(10, 2) NOT NULL,
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
    warehouse_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (warehouse_id) REFERENCES warehouse (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

-- TABEL: recepții (document de intrare)
CREATE TABLE receipt (
    id SERIAL PRIMARY KEY,
    supplier_id INT NOT NULL,
    warehouse_id INT NOT NULL,
    receipt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    document_number VARCHAR(100),
    notes TEXT,
    FOREIGN KEY (supplier_id) REFERENCES supplier (id) ON DELETE SET NULL,
    FOREIGN KEY (warehouse_id) REFERENCES warehouse (id) ON DELETE SET NULL
);

-- TABEL: articole recepționate (produse din recepție)
CREATE TABLE receipt_item (
    id SERIAL PRIMARY KEY,
    receipt_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2),
    FOREIGN KEY (receipt_id) REFERENCES receipt (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

-- TABEL: mișcări de stoc (opțional, dar foarte util)
CREATE TABLE stock_movement (
    id SERIAL PRIMARY KEY,
    from_warehouse_id INT NOT NULL,
    to_warehouse_id INT NOT NULL,
    movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reference VARCHAR(255), -- ex: ID recepție
    FOREIGN KEY (from_warehouse_id) REFERENCES warehouse (id),
    FOREIGN KEY (to_warehouse_id) REFERENCES warehouse (id)
);

CREATE TABLE stock_movement_item (
    id SERIAL PRIMARY KEY,
    stock_movement_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (stock_movement_id) REFERENCES stock_movement (id)
);