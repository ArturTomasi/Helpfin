create table posting_types
(
    id        serial          not null,
    name      varchar ( 200 ) not null,
    info      text,
    state     integer         not null  default 0,

    constraint pk_postings_types_id   primary key ( id ),
    constraint uq_postings_types_name unique ( name )
);

create table posting_categories
(
    id                    serial          not null,
    name                  varchar ( 200 ) not null,
    info                  text,
    ref_posting_type      integer         not null,
    state                 integer         not null  default 0,

    constraint pk_posting_categories primary key ( id ),
    constraint fk_ref_posting_type   foreign key ( ref_posting_type  ) references posting_types ( id ),
    constraint uq_cl_name            unique ( name, ref_posting_type )
);

create table entities
(
    id            serial         not null,
    name          varchar( 200 ) not null,
    companny_name varchar( 200 ),
    cnpj          varchar( 14 ),
    phone         varchar( 15 ),
    mail          varchar( 200 ), 
    state         integer         not null  default 0,

    constraint pk_entitie_id             primary key ( id ),
    constraint uq_entities_companny_name unique( companny_name ), 
    constraint uq_entities_cnpj          unique( cnpj ), 
    constraint uq_entities_phone         unique( phone ),
    constraint uq_entities_name          unique( name ), 
    constraint uq_entities_mail          unique( mail )  
);

create table users
(
    id              serial           not null,
    name            varchar ( 200 )  not null,
    cpf             varchar ( 11  )  not null,
    birth_date      date             not null,
    gender          integer          not null,
    rg              varchar ( 10 )   not null,
    state           integer          not null default 0,
    password        varchar ( 200 )  not null,
    login           varchar ( 200 )  not null,
    mail            varchar ( 200 )  not null,
    phone           varchar ( 14  )  ,
    role            integer          not null default 0,

    constraint pk_users_id        primary key ( id ),
    constraint uq_users_name      unique( name ),
    constraint uq_users_cpf       unique( cpf ),
    constraint uq_users_rg        unique( rg ),
    constraint uq_users_mail      unique( mail  ),
    constraint uq_users_login     unique( login )
);

-- 1 A vista
-- 2 Boletos
-- 3 Cartão
-- 4 Conta
-- 5 Outros

create table completion_types
(
    id               serial         not null,
    name             varchar( 200 ) not null,
    info             text,
    type             integer        not null,
    state            integer        not null default 0,

    constraint pk_completion_types_id primary key ( id )
);

create table postings
(
    id                       serial          not null,
    name                     varchar ( 200 ) not null,
    info                     text,
    real_date                date,
    estimate_date            date            not null,  
    fl_completion_auto       integer         not null  default 0,
    real_value               numeric,
    estimate_value           numeric         not null, 
    portion                  integer         not null,
    portion_total            integer         not null,
    fl_repeat                integer         not null  default 0,
    state                    integer         not null  default 0,
    ref_posting_category     integer, 
    ref_user                 integer         not null,
    ref_completion_type      integer,
    ref_entity               integer         not null,
    ref_posting              integer,

    constraint pk_postings_id                      primary key ( id ),
    constraint fk_postings_ref_posting_category    foreign key ( ref_posting_category ) references posting_categories  ( id ),
    constraint fk_postings_ref_user                foreign key ( ref_user )             references users               ( id ),
    constraint fk_postings_ref_completion_type     foreign key ( ref_completion_type )  references completion_types    ( id ),
    constraint fk_postings_ref_entity              foreign key ( ref_entity )           references entities            ( id ),
    constraint fk_postings_ref_posting             foreign key ( ref_posting )          references postings            ( id )
);

create table attachments
(
    id   serial          not null,
    name varchar ( 200 ) not null,
    ref_posting integer  not null,
    url  varchar ( 200 ) not null,
    info varchar ( 200 ) ,

    constraint pk_attachments_id primary key ( id ),
    constraint fk_attachments_ref_postingprimary foreign key ( ref_posting ) references postings ( id )
);


/* 
 * CREATE INDEX FOR TABLES 
 */

create index index_ref_user_posting on postings ( ref_user );
create index index_ref_posntig_posting on postings ( ref_posting ); 
