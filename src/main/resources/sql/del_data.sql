CREATE OR REPLACE FUNCTION public.truncate_if_exists(_table text, _schema text DEFAULT NULL)
    RETURNS text
    LANGUAGE plpgsql AS
$func$
DECLARE
    _qual_tbl  text := concat_ws('.', quote_ident(_schema), quote_ident(_table));
    _row_found bool;
BEGIN
    IF to_regclass(_qual_tbl) IS NOT NULL THEN
        EXECUTE 'SELECT EXISTS (SELECT FROM ' || _qual_tbl || ')'
            INTO _row_found;

        IF _row_found THEN
            EXECUTE 'TRUNCATE ' || _qual_tbl || ' cascade';
            RETURN 'Table truncated: ' || _qual_tbl;
        ELSE -- optional!
            RETURN 'Table exists but is empty: ' || _qual_tbl;
        END IF;
    ELSE -- optional!
        RETURN 'Table not found: ' || _qual_tbl;
    END IF;
END
$func$;

select truncate_if_exists('apparatuses_types');
select truncate_if_exists('apparatuses_modes');
select truncate_if_exists('apparatuses_operations');
select truncate_if_exists('apparatuses_tasks');
select truncate_if_exists('apparatuses');
select truncate_if_exists('operations_apparatuses_products');
select truncate_if_exists('operations_modes');
select truncate_if_exists('posts');
select truncate_if_exists('products');
select truncate_if_exists('products_orders');
select truncate_if_exists('products_snapshots');
select truncate_if_exists('products_tasks');
select truncate_if_exists('products_types');
select truncate_if_exists('storage_snapshot');
select truncate_if_exists('tasks');
select truncate_if_exists('technical_task');
select truncate_if_exists('"user"');
select truncate_if_exists('mods');