/*
 *	INSERT TIPOS LANCAMENTOS
 */
insert into posting_types ( name, info ) values ( 'DESPESA', 'Tipo para agrupara as categorias de lançamentos do tipo de despesas' );
insert into posting_types ( name, info ) values ( 'RECEITA', 'Tipo para agrupara as categorias de lançamentos do tipo de receita' );

/*
 *	INSERT CATEGORIAS LANCAMENTOS
 */
insert into posting_categories ( name, info, ref_posting_type ) values ( 'EDUCAÇAO',         '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'ALIMENTAÇÃO',      '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'COMBUSTÍVEL',      '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'OUTROS',           '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'SALÁRIO',          '', 2 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'OUTROS',           '', 2 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'EMPRÉSTIMO',       '', 2 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'EMPRÉSTIMO',       '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'LAZER',            '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'SAÚDE',            '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'TRABALHOS EXTRAS', '', 2 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'ALUGUEL',          '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'CONDOMINIO', 	     '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'AGUA',	     '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'LUZ', 	     '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'TELEFONE',         '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'ACADEMIA', 	     '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'INTERNET', 	     '', 1 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'MANUTENÇAO CASA',  '', 2 );
insert into posting_categories ( name, info, ref_posting_type ) values ( 'MANUTENÇAO CARRO', '', 2 );

/*
 *	INSERT ENTIDADES
 */
insert into entities ( name, companny_name, cnpj, phone, mail ) values ( 'UNIVATES'            , null, null, '5137147000', 'univates@univates.br' );
insert into entities ( name, companny_name, cnpj, phone, mail ) values ( 'HOSPITAL'            , null, null, '5137147001', null );
insert into entities ( name, companny_name, cnpj, phone, mail ) values ( 'RODOVIÁRIA LAJEADO'  , null, null, null,         null );
insert into entities ( name, companny_name, cnpj, phone, mail ) values ( 'RODOVIÁRIA PROGRESSO', null, null, null,         null );
insert into entities ( name, companny_name, cnpj, phone, mail ) values ( 'FEIER IMÓVEIS'       , null, null, null,         null );
insert into entities ( name, companny_name, cnpj, phone, mail ) values ( 'ARRUDA E MUNHOZ'     , null, null, null,         null );
insert into entities ( name, companny_name, cnpj, phone, mail ) values ( 'SUBMARINO'           , null, null, null,         null );
insert into entities ( name, companny_name, cnpj, phone, mail ) values ( 'WALMART'             , null, null, null,         null );
insert into entities ( name, companny_name, cnpj, phone, mail ) values ( 'INTERACT SOLUTIONS'  , null, null,'513710-1311', null );


/*
 *	INSERT USER
 */
insert into users ( cpf, birth_date, rg, mail, phone, gender, name, state, login, password, role  ) values ( '02943508020', '1995-03-15', '7383250983', 'lcstomasi@gmail.com'      , '5197068174', 1, 'LUCAS TOMASI'         , 0, 'lt',    '<2fcc1dd51cb7514a99f03debf513ca7af3b25669>', 0 );
insert into users ( cpf, birth_date, rg, mail, phone, gender, name, state, login, password, role  ) values ( '12312414211', '1995-03-15', '4242398503', 'artur.tomasi@outlook.com' , '5195090933', 1, 'ARTUR TOMASI'         , 0, 'art',   '<2fcc1dd51cb7514a99f03debf513ca7af3b25669>', 0 );
insert into users ( cpf, birth_date, rg, mail, phone, gender, name, state, login, password, role  ) values ( '00000000000', '1994-10-30', '1232141280', 'admin@helpfin.br'         , '5198434239', 1, 'ADMINISTRADOR SISTEMA', 0, 'admin', '<2fcc1dd51cb7514a99f03debf513ca7af3b25669>', 0 );

/*
 *	INSERT TIPOS FINALIZACOES
 */

insert into completion_types ( name , type ) values ( 'Vale Presente'  , 2 );
insert into completion_types ( name , type ) values ( 'Cartao Sicredi' , 2 );
insert into completion_types ( name , type ) values ( 'Cartao Refeição', 2 );
insert into completion_types ( name , type ) values ( 'Cartao BB'      , 2 );
insert into completion_types ( name , type ) values ( 'Conta Sicredi'  , 3 );
insert into completion_types ( name , type ) values ( 'Conta BB'       , 3 );
insert into completion_types ( name , type ) values ( 'A vista'        , 0 );
insert into completion_types ( name , type ) values ( 'Dollar'         , 0 );
insert into completion_types ( name , type ) values ( 'Pesos'          , 0 );
insert into completion_types ( name , type ) values ( 'Boleto'         , 1 );