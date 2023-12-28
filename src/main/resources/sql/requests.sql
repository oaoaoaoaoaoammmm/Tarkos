-- + Поиск technical task по дате

select *
from technical_tasks
where work_day_date = ?;

-- + Поиск task по technical task id

select *
from tasks
where technical_task_id = ?;

-- + Составление первой части таблицы с нужными аппаратами для TT по task id

select apparatuses_types.id,
       apparatuses.apparatus_type,
       apparatuses_types.name,
       apparatuses_types.description
from tasks
         join apparatuses_tasks on tasks.id = apparatuses_tasks.task_id
         join apparatuses_types on apparatuses_tasks.apparatus_type_id = apparatuses_types.id
         join apparatuses on apparatuses_types.apparatus_id = apparatuses.id
where tasks.id = ?
group by apparatuses_types.id, apparatuses_types.name, apparatuses_types.description,
         apparatuses.apparatus_type;

-- + Составление второй части таблицы с нужными продуктами для TT по task id

select products_types.id,
       products.product_type,
       products_types.name,
       products_types.variety,
       products_types.count,
       products_tasks.count,
       products_types.description
from tasks
         join products_tasks on tasks.id = products_tasks.task_id
         join products_types on products_tasks.product_type_id = products_types.id
         join products on products_types.product_id = products.id
where tasks.id = ?
group by products_types.id, products_types.name, products_types.variety, products_types.count,
         products_types.description,
         products_tasks.count, products.product_type;

-- + Запрос на текущее состояние склада

select products_types.id,
       products.product_type,
       products_types.name,
       products_types.variety,
       products_types.count,
       products_types.description
from products_types
         join products on products_types.product_id = products.id;

-- + Запрос на поиск всех apparatuses

select apparatuses_types.id,
       apparatuses.apparatus_type,
       apparatuses_types.name,
       apparatuses_types.volume,
       apparatuses_types.description
from apparatuses_types
         join apparatuses on apparatuses_types.apparatus_id = apparatuses.id;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Делать вместе с авторизацией

select "user".id, posts.profession, first_name, last_name, age, username, password from "user"
join posts on "user".id = posts.user_id
where username = ?;

-- + Запросы на заказ продуктов

insert into products_orders (executor_id, owner_id, product_type_id, count, description)
VALUES (
           (select id from posts where text(profession) = ?),
           (select id from posts where text(profession) = ?),
           (select id from products_types where name = ?),
           ?, ?
       );

-- + Запрос на выдачу актyальных заказов определенной роли

select *
from products_orders
where owner_id = ?
  and ready = false;

-- + Запрос на подтверждение заказа

update products_orders
set ready = true
where id = ?;

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- + Поиск производимых продуктов

select products_types.name
from products_types
         left join products_derivatives on products_types.id = products_derivatives.product_id
where derivatives_product_id = (select id from products_types where name = ?);

-- + Взять все типы продуктов

select product_type
from products;

-- + Взять все моды конкретного аппарата по id

select mods.mode_name
from apparatuses_modes
         join mods on apparatuses_modes.mode_id = mods.id
where apparatuses_modes.apparatus_type_id = ?;

-- + Изменение кол-ва продукта по имени на складе

update products_types
set count = count + ?
where name = ?;

-- + Добавление операции с аппаратом в историю

insert into apparatuses_operations (apparatus_type_id, date)
select id, now()
from apparatuses_types
where name = ?;

-- + Добавление использованного мода в операцию

insert into operations_modes (operation_id, mode_id)
VALUES ((select max(id) from apparatuses_operations), (select id
                                                       from mods
                                                       where mode_name = ?));

-- + Добавление использованных в операции продуктов

insert into operations_apparatuses_products (product_type_id, apparatus_operation_id, count, description)
VALUES ((select id
         from products_types
         where name = ?), (select max(id) from apparatuses_operations), ?, ?);

-- + Сдача TT и срабатывание триггера на снимок склада

update technical_tasks
set ready = true
where work_day_date = ?;