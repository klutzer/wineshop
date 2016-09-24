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
	idtipos bigint not null references tipos,
	descricao varchar not null,
	peso double precision not null,
	valor numeric not null
);

create table vendas (
	idvendas bigint auto_increment primary key,
	dataHora timestamp not null,
	idclientes bigint not null references clientes,
	distancia double precision not null,
	pesoTotal double precision not null,
	valorItens numeric not null,
	totalFrete numeric not null,
	totalVenda numeric not null
);

create table itens_venda (
	idvendas bigint not null references vendas on update cascade on delete cascade,
	idvinhos bigint not null references vinhos,
	qtde double precision not null,
	subtotal numeric not null,
	primary key (idvendas, idvinhos)
);