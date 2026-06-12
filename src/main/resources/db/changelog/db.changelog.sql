--liquibase formatted sql
--changeset sarh:19_05_2026-1
CREATE TABLE IF NOT EXISTS carts (
    id VARCHAR(255) PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    total_price INTEGER NOT NULL DEFAULT 0
);

--changeset sarh:12_06_2026-2
CREATE TABLE IF NOT EXISTS cart_items (
    cart_id VARCHAR(255) NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    menu_item_id VARCHAR(255) NOT NULL,
    item_quantity INTEGER NOT NULL,
    item_price INTEGER NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE
);