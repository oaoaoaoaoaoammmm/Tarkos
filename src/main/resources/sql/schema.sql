do
$$
    begin
        create type product_type as enum ('BEER', 'BOTTLE', 'WATER', 'MALT', 'HOP', 'YEAST', 'WORT', 'BEER_WITHOUT_BOTTLE');
        create type apparatus_type as enum ('FERMENTATION', 'COOKING', 'BOTTLING', 'WATER_PREPARATION', 'YEAST_BANK');
        create type profession as enum ('MANAGER', 'BOTTLE_MAN', 'WATER_MAN', 'MALT_MAN', 'HOP_MAN', 'YEAST_MAN');
    exception
        when duplicate_object then null;
    end
$$;

create table if not exists technical_tasks
(
    id            serial primary key,
    ready         bool not null default false,
    work_day_date date not null,
    description   text
);

create index technical_tasks_ready on technical_tasks using btree (work_day_date);

create table if not exists products
(
    id           serial primary key,
    product_type product_type unique not null
);

create table if not exists products_types
(
    id          serial primary key,
    product_id  int         not null references products (id) on delete cascade,
    name        varchar(64) not null,
    variety     float,
    count       float       not null check ( count >= 0 ),
    description text
);

create table if not exists products_derivatives
(
    product_id             int not null references products_types (id) on delete cascade,
    derivatives_product_id int not null references products_types (id) on delete cascade,
    constraint products_derivatives_id primary key (product_id, derivatives_product_id)
);

create table if not exists apparatuses
(
    id                    serial primary key,
    operation_coefficient float                 not null,
    apparatus_type        apparatus_type unique not null
);

create table if not exists apparatuses_types
(
    id           serial primary key,
    apparatus_id int         not null references apparatuses (id) on delete cascade,
    name         varchar(64) not null,
    volume       int         not null check ( volume > 0 ),
    description  text
);

create table if not exists mods
(
    id        serial primary key,
    mode_name varchar(64) not null
);

create table if not exists apparatuses_modes
(
    apparatus_type_id int not null references apparatuses_types (id) on delete cascade,
    mode_id           int not null references mods (id) on delete cascade,
    constraint apparatuses_modes_id primary key (apparatus_type_id, mode_id)
);

create table if not exists apparatuses_operations
(
    id                serial primary key,
    apparatus_type_id int       not null references apparatuses_types (id) on delete restrict,
    date              timestamp not null
);

create table if not exists operations_modes
(
    operation_id int not null references apparatuses_operations (id) on delete cascade,
    mode_id      int not null references mods (id) on delete cascade,
    constraint operations_modes_id primary key (operation_id, mode_id)
);

create table if not exists operations_apparatuses_products
(
    product_type_id        int   not null references products_types (id) on delete restrict,
    apparatus_operation_id int   not null references apparatuses_operations (id) on delete restrict,
    count                  float not null check ( count >= 0 ),
    description            text,
    constraint operations_apparatuses_products_id primary key (product_type_id, apparatus_operation_id)
);

create table if not exists posts
(
    id         serial primary key,
    profession profession not null
);

create table if not exists "user"
(
    id         serial primary key,
    post_id    int                not null references posts (id) on delete cascade,
    username   varchar(32) unique not null,
    password   text               not null,
    first_name varchar(32)        not null,
    last_name  varchar(32)        not null,
    age        int                not null check ( age > 0 )
);

alter table posts
    add column if not exists user_id int references "user" (id) on delete set null;

create table if not exists products_orders
(
    id              serial primary key,
    executor_id     int   not null references posts (id) on delete restrict,
    owner_id        int   not null references posts (id) on delete restrict,
    product_type_id int   not null references products_types (id) on delete restrict,
    count           float not null check ( count >= 0 ),
    description     text,
    ready           bool  not null default false
);


create index products_orders_ready on products_orders using btree (ready);

create table if not exists storage_snapshot
(
    id   serial primary key,
    date timestamp not null
);

create table if not exists products_snapshots
(
    product_type_id int not null references products_types (id) on delete restrict,
    snapshot_id     int not null references storage_snapshot (id) on delete cascade,
    count           int not null check ( count >= 0 ),
    variety         float,
    constraint products_snapshots_id primary key (product_type_id, snapshot_id)
);

create table if not exists tasks
(
    id                serial primary key,
    technical_task_id int not null references technical_tasks (id) on delete cascade
);

create table if not exists apparatuses_tasks
(
    apparatus_type_id int not null references apparatuses_types (id) on delete restrict,
    task_id           int not null references tasks (id) on delete restrict,
    constraint apparatuses_tasks_id primary key (apparatus_type_id, task_id)
);

create table if not exists products_tasks
(
    product_type_id int   not null references products_types (id) on delete restrict,
    task_id         int   not null references tasks (id) on delete restrict,
    count           float not null check ( count >= 0 ),
    constraint products_tasks_id primary key (product_type_id, task_id)
);
