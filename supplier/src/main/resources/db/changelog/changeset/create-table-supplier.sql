create table if not exists supplier
(
    id                bigserial primary key,
    name              text,
    ip_name           text,
    ooo_name          text,
    ogrn              text,
    organization_name text,
    is_active         boolean
)