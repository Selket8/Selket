select * from customer c;

select * from "order";

select * from order_status;

select * from product;


-- 1. Из таблицы product отфильтровать товары с категорией "косметика"
-- Выбираем все столбцы товаров, у которых category равна 'косметика'
SELECT * FROM product
WHERE category = 'косметика';

-- 2. Объединить таблицы order и order_status по статусу
-- получаем все заказы вместе с названием статуса
SELECT o.*, os.status_name
FROM "order" o
JOIN order_status os ON o.status_id = os.id;

-- 3. Из таблицы customer вывести клиентов, у которых телефон начинается на цифру 4
-- используем условие LIKE для поиска по шаблону
SELECT * FROM customer
WHERE phone LIKE '4%';

-- 4. В таблице customer изменить номер телефона в столбце phone для id 586 на 1234
UPDATE customer
SET phone = '1234'
WHERE id = 586;

-- 5. В таблицу order добавить заказ с данными: id=112, product_id=2, customer_id=8999, quantity=3, status_id=41
INSERT INTO "order" (id, product_id, customer_id, order_date, quantity, status_id)
VALUES (112, 2, 8999, NOW(), 3, 41);


-- 6. В таблице order изменить статус заказа с id=21 на статус с id=11
UPDATE "order"
SET status_id = 11
WHERE id = 21;

-- 7. В таблице order удалить заказ с id=101
DELETE FROM "order"
WHERE id = 101;

-- 8. В таблице product удалить товар с id=10
DELETE FROM product
WHERE id = 10;

-- 9. В таблице customer удалить покупателя с id=1220
DELETE FROM customer
WHERE id = 1220;

-- 10. напиши sql запрос - в таблице order_status изменить status_name для id 101 на значение "срочный заказ"
UPDATE order_status
SET status_name = 'срочный заказ'
WHERE id = 101;


-- 11. добавляем в product новый товар
INSERT INTO product (id, description, price, quantity, category) 
VALUES
(10, 'Румяна', 570, 12, 'косметика');

-- 12. добавляем нового покупателя в customer
INSERT INTO customer (id, first_name, last_name, phone, email) 
VALUES
(777, 'Анна', 'Удача', '4532', 'sel@ya.ru');


select * from customer c;

select * from "order";

select * from order_status;

select * from product;