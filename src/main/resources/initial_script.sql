create table clientes (
	idclientes bigint auto_increment primary key, 
	nome varchar not null
);
	
create table tipos (
	idtipos bigint auto_increment primary key,
	descricao varchar not null
);
	
create table vinhos (
	idvinhos bigint auto_increment primary key,
	idtipos bigint references tipos,
	descricao varchar not null,
	peso double precision not null,
	valor numeric not null
);