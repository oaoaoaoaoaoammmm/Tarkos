insert into products (product_type)
values ('BEER'),
       ('BOTTLE'),
       ('WATER'),
       ('MALT'),
       ('HOP'),
       ('YEAST'),
       ('WORT'),
       ('BEER_WITHOUT_BOTTLE');

insert into apparatuses (apparatus_type, operation_coefficient)
values ('FERMENTATION', 1),
       ('COOKING', 0.9),
       ('BOTTLING', 1),
       ('WATER_PREPARATION', 1),
       ('YEAST_BANK', 2);

insert into products_types (product_id, name, variety, count, description)
VALUES (1, 'Dark Munich lager', 0.5, 10, 'about Dark Munich lager'),
       (1, 'Czech light lager', 0.5, 15, 'about Czech light lager'),
       (1, 'German wheat light', 0.5, 20, 'about German wheat light'),
       (1, 'English ale', 0.5, 25, 'about English ale'),
       (2, 'Half liter', 0.5, 100, 'about bottle of half liter'),
       (2, 'One liter', 1.0, 0, 'about bottle of one liter'),
       (3, 'Default water', 0, 900, 'about default water'),
       (3, 'Tough water', 0, 750, 'about tough water'),
       (3, 'Soft water', 0, 860, 'about soft water'),
       (3, 'Bavarian water', 0, 540, 'about bavarian water'),
       (3, 'English profile water', 0, 600, 'about english profile water'),
       (4, 'Munich malt', 0, 40, 'about munich malt'),
       (4, 'Czech malt', 0, 65, 'about czech malt'),
       (4, 'Wheat malt', 0, 45, 'about wheat malt'),
       (4, 'Barley malt', 0, 80, 'about barley malt'),
       (5, 'Hallertau hop', 0, 110, 'about hallertau hop'),
       (5, 'Saaz hop', 0, 55, 'about saaz hop'),
       (5, 'Liberty hop', 0, 28, 'about liberty hop'),
       (6, 'Munich Dunkel yeast', 0, 15, 'about munich dunkel yeast'),
       (6, 'White labs yeast', 0, 22, 'about white labs yeast'),
       (6, 'Wheat yeast', 0, 26, 'about wheat yeast'),
       (6, 'LEYKA yeast', 0, 18, 'about leyka yeast'),
       (7, 'Dark Munich lager wort', 0, 0, 'tough water munich malt hallertau hop'),
       (7, 'Czech light lager wort', 0, 0, 'soft water czech malt saaz hop'),
       (7, 'German wheat light wort', 0, 0, 'bavarian water wheat malt liberty hop'),
       (7, 'English ale wort', 0, 0, 'english profile water barley malt'),
       (8, 'Dark Munich lager without bottle', 0, 0, 'Dark Munich lager without bottle'),
       (8, 'Czech light lager without bottle', 0, 0, 'Czech light lager without bottle'),
       (8, 'German wheat light without bottle', 0, 0, 'German wheat light without bottle'),
       (8, 'English ale without bottle', 0, 0, 'English ale without bottle');

insert into products_derivatives (product_id, derivatives_product_id)
VALUES (1, 27),
       (1, 5),
       (2, 28),
       (2, 5),
       (3, 29),
       (3, 5),
       (4, 30),
       (4, 5),
       (8, 7),
       (9, 7),
       (10, 7),
       (11, 7),
       (19, 19),
       (20, 20),
       (21, 21),
       (22, 22),
       (23, 8),
       (23, 12),
       (23, 16),
       (24, 9),
       (24, 13),
       (24, 17),
       (25, 10),
       (25, 14),
       (25, 18),
       (26, 11),
       (26, 15),
       (27, 23),
       (27, 19),
       (28, 24),
       (28, 20),
       (29, 25),
       (29, 21),
       (30, 26),
       (30, 22);


/*
do
$$
    begin
        for i in 1 .. 9999
            LOOP
                insert into technical_tasks (description, ready, work_day_date)
                VALUES ('qwewqewqewq', true, '2022-12-12'),
                       ('qwewqewqewq', true, '2011-12-12'),
                       ('qwewqewqewq', true, '2015-10-10'),
                       ('qwewqewqewq', true, now());
            end LOOP;
    end;

$$;
*/

insert into technical_tasks (description, ready, work_day_date)
VALUES ('i want to be a cosmonaut', false, '2020-10-10');

insert into tasks (technical_task_id)
values (1);

insert into products_tasks (product_type_id, task_id, count)
VALUES (1, 1, 400.0),
       (5, 1, 400.0),
       (7, 1, 220.0),
       (8, 1, 220.0),
       (12, 1, 20.0),
       (19, 1, 0.2),
       (16, 1, 0.05);

insert into apparatuses_types (apparatus_id, name, volume, description)
VALUES (1, 'Cylindrical tank', 1200, 'about cylindrical tank'),
       (1, 'Barrel tank', 700, 'about barrel tank'),
       (1, 'High elongated capacity', 900, 'about high elongated capacity'),
       (2, 'Copper boiler', 800, 'about copper boiler'),
       (2, 'Large brewer', 1000, 'about large brewer'),
       (3, 'Bottling line', 500, 'about bottling line'),
       (4, 'Water preparation apparatus', 750, 'about water preparation'),
       (5, 'Yeast bank', 400, 'about yeast bank');

insert into apparatuses_operations (apparatus_type_id, date)
VALUES (4, '2020-10-10'),
       (8, '2020-10-10'),
       (2, '2020-10-10'),
       (7, '2020-10-10'),
       (6, '2020-10-10');

insert into mods (mode_name)
values ('Soft water'),
       ('Tough water'),
       ('Bavarian water'),
       ('English profile water'),
       ('Normal cooking'),
       ('Yeasting'),
       ('Fermentation'),
       ('Bottling');

insert into operations_modes (operation_id, mode_id)
VALUES (1, 5),
       (2, 6),
       (3, 7),
       (4, 2),
       (5, 8);

insert into apparatuses_modes (apparatus_type_id, mode_id)
VALUES (1, 5),
       (2, 5),
       (3, 5),
       (4, 7),
       (5, 7),
       (6, 8),
       (7, 1),
       (7, 2),
       (7, 3),
       (7, 4),
       (8, 6);

insert into apparatuses_tasks (apparatus_type_id, task_id)
VALUES (4, 1),
       (8, 1),
       (2, 1),
       (7, 1),
       (6, 1);

insert into posts (profession)
values ('MANAGER'),
       ('BOTTLE_MAN'),
       ('WATER_MAN'),
       ('MALT_MAN'),
       ('HOP_MAN'),
       ('YEAST_MAN');

insert into "user" (post_id, username, password, first_name, last_name, age)
VALUES (1, 'manager', 'manager', 'Leonid', 'Voronin', 44),
       (2, 'bottle', 'bottle', 'Konstantin', 'Voronin', 37),
       (3, 'water', 'water', 'Nokolay', 'Voronin', 63),
       (4, 'malt', 'malt', 'Galina', 'Voronina', 62),
       (5, 'hop', 'hop', 'Vera', 'Voronina', 35),
       (6, 'yeast', 'yeast', 'Masha', 'Voronina', 18);

update posts
set user_id = 1
where id = 1;

update posts
set user_id = 2
where id = 2;

update posts
set user_id = 3
where id = 3;

update posts
set user_id = 4
where id = 4;

update posts
set user_id = 5
where id = 5;

update posts
set user_id = 6
where id = 6;

/*
do
$$
    begin
        for i in 1 .. 5000 LOOP
        insert into products_orders (post_id, product_type_id, count, description, ready)
        VALUES (3, 7, 500, 'default water order', false),
               (2, 5, 300, 'half liter order', true);
        end LOOP;
    end;

$$;
*/

insert into products_orders (owner_id, executor_id, product_type_id, count, description, ready)
VALUES (1, 3, 7, 500, 'default water order', false),
       (1, 2, 5, 300, 'half liter order', true);


create or replace function create_snapshot_storage()
    returns trigger
    language plpgsql as
$func$
declare
    snapshot     storage_snapshot%rowtype;
    product_type products_types%rowtype;
begin
    insert into storage_snapshot (date) values (now());
    select max(id) from storage_snapshot into snapshot;

    for product_type in select * from products_types
        loop
            insert into products_snapshots (product_type_id, snapshot_id, count, variety)
            values (product_type.id, snapshot.id, product_type.count, product_type.variety);
        end loop;
    return null;
end;
$func$;

create trigger call_create_snapshot_storage_after_update_ready_flag
    after update of ready
    on technical_tasks
execute procedure create_snapshot_storage();

update technical_tasks
set ready = true
where id = 1;