
-- schema.sql
-- Создание таблицы для продуктов
CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY, -- Уникальный идентификатор продукта
    description VARCHAR(255) NOT NULL, -- Описание продукта
    price DECIMAL(10,2) NOT NULL CHECK (price > 0), -- Стоимость, больше 0
    quantity INTEGER NOT NULL CHECK (quantity >= 0), -- Количество, неотрицательное
    category VARCHAR(100) -- Категория продукта
);

-- Наполнение таблицы для продуктов

INSERT INTO product (id, description, price, quantity, category) 
VALUES
(1, 'Шампунь', 300, 10, 'для душа'),
(2, 'Мыло', 100, 10, 'для душа'),
(3, 'Паста', 200, 10, 'для зубов'),
(4, 'Краска', 1000, 10, 'для волос'),
(5, 'Помада', 700, 10, 'косметика'),
(6, 'Гель', 350, 10, 'для душа'),
(7, 'Кондиционер', 375, 10, 'для волос'),
(8, 'Праймер', 950, 10, 'косметика'),
(9, 'Карандаш', 430, 10, 'косметика'),
(10, 'Кисть', 569, 10, 'косметика');



-- Создание таблицы для клиентов
CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY, -- Уникальный идентификатор клиента
    first_name VARCHAR(50) NOT NULL, -- Имя
    last_name VARCHAR(50) NOT NULL, -- Фамилия
    phone VARCHAR(20), -- Телефон
    email VARCHAR(100) -- Email
);

-- Наполнение таблицы для клиентов

INSERT INTO customer (id, first_name, last_name, phone, email) 
VALUES
(236, 'Алексей', 'Иванов', '1312', 'se@ya.ru'),
(586, 'Петр', 'Петров', '4536', 'ak@ya.ru'),
(375, 'Иван', 'Сидоров', '2789', 'og@ya.ru'),
(4365, 'Анна', 'Берко', '6325', 'gs@ya.ru'),
(596, 'Катя', 'Мишина', '5698', 'lk@ya.ru'),
(6536, 'Маша', 'Сокол', '6512', 'fg@ya.ru'),
(7598, 'Федя', 'Лом', '9658', 'uy@ya.ru'),
(8999, 'Вася', 'Быстро', '6358', 'ju@ya.ru'),
(9012, 'Люда', 'Людина', '4500', 'tr@ya.ru'),
(1220, 'Мира', 'любовна', '9825', 'kk@ya.ru');

-- Создание таблицы для статусов заказов (справочник)
CREATE TABLE IF NOT EXISTS order_status (
    id SERIAL PRIMARY KEY, -- Идентификатор статуса
    status_name VARCHAR(50) NOT NULL -- Название статуса
);

-- Наполнение таблицы статусов заказов ( справочник)

INSERT INTO order_status (id, status_name) 
VALUES
(11, 'в пути'),
(21, 'доставлен'),
(31, 'ожидание оплаты'),
(41, 'доставляется'),
(55, 'возврат'),
(66, 'оплачен'),
(77, 'отмена'),
(87, 'сборка'),
(99, 'замена'),
(101,'предзаказ');


-- Создание таблицы заказов
CREATE TABLE IF NOT EXISTS "order" (
    id SERIAL PRIMARY KEY, -- Уникальный идентификатор заказа
    product_id INTEGER NOT NULL, -- Идентификатор продукта (внешний ключ)
    customer_id INTEGER NOT NULL, -- Идентификатор клиента (внешний ключ)
    order_date TIMESTAMP NOT NULL DEFAULT NOW(), -- Дата заказа
    quantity INTEGER NOT NULL CHECK (quantity > 0), -- Количество, больше 0
    status_id INTEGER NOT NULL, -- Статус заказа (внешний ключ)
    
    -- Внешние ключи
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES order_status (id)
);

-- Наполнение таблицы заказов

INSERT INTO "order" (id, product_id, customer_id, quantity, status_id) 
VALUES
(11, 1, 236, 2, 11),
(21, 2, 586, 3, 21),
(31, 3, 375, 2, 31),
(41, 4, 4365, 1, 41),
(55, 5, 596, 1, 55),
(66, 6, 6536, 3, 66),
(77, 7, 7598, 2 , 77),
(87, 8, 8999, 5, 87),
(99, 9, 9012, 6, 99),
(101,10, 1220, 2, 101);


-- Создание индексов для оптимизации поиска
CREATE INDEX IF NOT EXISTS idx_order_product_id ON "order" (product_id);
CREATE INDEX IF NOT EXISTS idx_order_customer_id ON "order" (customer_id);
CREATE INDEX IF NOT EXISTS idx_order_order_date ON "order" (order_date);
CREATE INDEX IF NOT EXISTS idx_order_status_id ON "order" (status_id);
