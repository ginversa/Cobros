/*

select * from tmp_operaciones;

babase de datos de cobros.
eto hay que pasarlo a postgreSQL
*/
/*
CREATE DATABASE IF NOT EXISTS cobrosdb;
use cobrosdb;
*/
/**************************************************/
-- INICIO

CREATE TABLE IF NOT EXISTS Moneda (
    id_Moneda SERIAL PRIMARY KEY,
    codigo varchar(3),
    simbolo varchar(1),
    descripcion varchar(20),    
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);


CREATE TABLE IF NOT EXISTS tbl_deudor ( -- deuda
	id_deudor serial,
  	codigo_cartera varchar(5),
  	codigo_gestor varchar(5),
  	documento varchar(50),
  	cliente_operacion varchar(50),
  	nombre varchar(100),
  	saldo DECIMAL(19,4),
  	saldo_dolares DECIMAL(19,4),  
  	subtipificacion varchar(5),
  	fec_ult_gest date,
  	fec_ult_prom date,
  	fec_ult_pago date,
  	fec_ingreso date,
  	fec_actualizacion date,
  	estatus varchar(1),
  	marca1 varchar(250),
  	marca2 varchar(250),
  	marca3 varchar(250),
  	usuarioIngreso varchar(50),
	fechaIngreso TIMESTAMP default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico TIMESTAMP,
  	primary key(id_deudor)
);

CREATE TABLE IF NOT EXISTS tbl_operacion (
  id_operacion serial,
  operacion varchar(50) not null,
  codigo_cartera varchar(5),
  documento varchar(50),
  saldo DECIMAL(19,4),
  saldo_dolares DECIMAL(19,4),
  mora varchar(250),
  marca1 varchar(250),
  marca2 varchar(250),
  marca3 varchar(250),
  estatus varchar(1),
  primary key(id_operacion)
);

CREATE TABLE IF NOT EXISTS tbl_codeudor (
  id_codeudor serial,
  codigo_cartera varchar(5),
  documento_deudor varchar(50),
  documento varchar(250),
  nombre varchar(250),
  primary key(id_codeudor)
);

CREATE TABLE IF NOT EXISTS tbl_dato_financiero (
  id_dato_financiero serial, 
  codigo_cartera varchar(5),
  documento varchar(50),
  operacion varchar(50),
  saldo_total DECIMAL(19,4),
  saldo_vencido DECIMAL(19,4),
  saldo_corriente DECIMAL(19,4),
  Mora30 DECIMAL(19,4),
  Mora60 DECIMAL(19,4),
  Mora90 DECIMAL(19,4),
  Mora120 DECIMAL(19,4),
  Mora150 DECIMAL(19,4),
  Mora180 DECIMAL(19,4),
  Mora180Mas DECIMAL(19,4),
  Fecha_Ult_Pago date,
  Valor_Ult_Pago DECIMAL(19,4),
  primary key(id_dato_financiero)  
);

CREATE TABLE IF NOT EXISTS tbl_pago (
  id_pago BIGSERIAL,
  codigo_cartera varchar(5),
  documento varchar(50),
  operacion varchar(50),
  fecha_pago date,
  valor DECIMAL(19,4),
  valor_dolares DECIMAL(19,4),
  descripcion text,
  fecha_carga timestamp,
  codigo_gestor varchar(5),
  primary key(id_pago)
);


/********************************************************************************/
CREATE TABLE IF NOT EXISTS Tipificacion (
    id_tipificacion SERIAL PRIMARY KEY,
    descripcion varchar(50),
	codigo_cartera varchar(5),			
	estado varchar(3) default 'ACT',
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE IF NOT EXISTS SubTipificacion (
    id_subtipificacion SERIAL primary key,
    id_tipificacion SERIAL references Tipificacion(id_tipificacion),
    descripcion varchar(50),
	codigo_cartera varchar(5),			
	estado varchar(3) default 'ACT',
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE if not exists RazonMora(
	idRazonMora SERIAL primary KEY,
	descripcion varchar(100),
	codigo_cartera varchar(5),			
	estado varchar(3) default 'ACT',
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE IF NOT EXISTS tbl_gestion (
  	id_gestion BIGSERIAL primary key,
  	codigo_cartera varchar(5),
  	nombre_cartera varchar(100),  	
  	identificacion varchar(50),
  	nombre_cliente varchar(50),
  	operacion varchar(50),
  	leyUsura varchar(1) default '0',--?????
  	mtoSaldoCobrar decimal(18,6) default 0,--????
  	codigo_gestor varchar(5),  	  	
  	fecha_gestion timestamp,
  	descripcion text,
  	estado varchar(3) default 'ING', --INGRESAR
  	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp  	
);

CREATE TABLE IF NOT EXISTS TipoTelefono (
    id_TipoTelefono SERIAL PRIMARY KEY,
    descripcion varchar(50),
    codigo varchar(3),
	estado varchar(3) default 'ACT',
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

/*
************************************************************************************************************************************************ 
*/
CREATE TABLE IF NOT EXISTS tbl_resultadogestion (
	id_resultadogestion SERIAL PRIMARY KEY,
  	descripcion varchar(250),
  	codigo varchar(3),
    id_tipificacion SERIAL references Tipificacion(id_tipificacion),
  	id_subtipificacion int4 references SubTipificacion(id_subtipificacion),
  	usuarioIngreso varchar(50),
	fechaIngreso TIMESTAMP default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tbl_resultadotercero (
	id_resultadotercero SERIAL PRIMARY KEY,
	id_resultadogestion SERIAL references tbl_resultadogestion(id_resultadogestion),
	id_tipificacion SERIAL references Tipificacion(id_tipificacion),
  	descripcion varchar(250),
  	codigo varchar(3),
  	usuarioIngreso varchar(50),
	fechaIngreso TIMESTAMP default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico TIMESTAMP
);

/*
 ************************************************************************************************************************************************ 
*/

create table if not exists tbl_llamada(
	id_Llamada BIGSERIAL primary key,
	id_gestion BIGSERIAL references tbl_gestion(id_gestion),
	call_log_id text,
	date_ini timestamp,
	date_end timestamp,
	call_from_number varchar(5),
	call_to_number varchar(50),
	dialstatus varchar(50),
	call_length timestamp,
	conversation_length int4,
	estado varchar(3) default 'ING', --INGRESAR
	id_tipificacion SERIAL references Tipificacion(id_tipificacion),
	id_subtipificacion SERIAL references SubTipificacion(id_subtipificacion),    
	idRazonMora int8 references RazonMora(idRazonMora),
	id_resultadogestion INT4 references tbl_resultadogestion(id_resultadogestion),
    id_resultadotercero INT4 references tbl_resultadotercero(id_resultadotercero),
	id_tipotelefono int8 references TipoTelefono(id_tipotelefono),
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
	usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE IF NOT EXISTS tbl_promesa (
  	id_promesa BIGSERIAL primary key,
  	id_llamada BIGSERIAL references tbl_llamada(id_llamada),
  	id_gestion BIGSERIAL references tbl_gestion(id_gestion),  	
  	operacion varchar(50),
  	telefono varchar(50),
  	tipoDescuento VARCHAR(3),
  	mtoPorcentaje DECIMAL(19,4) default 0,
  	mtoPago DECIMAL(19,4) default 0,
  	id_Moneda SERIAL references moneda(id_Moneda),
  	fecha_pago timestamp,
  	tipoArregloPago VARCHAR(3),  	
  	estado varchar(3) default 'ING', --INGRESAR
  	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

/*****************************************************************************/
/*****************************************************************************/
CREATE TABLE if not exists tbl_persona(
	id_persona serial primary key,
	nombre varchar(100),
	cedula varchar(50),
	usuarioIngreso varchar(50),
	fechaIngreso TIMESTAMP default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico TIMESTAMP	
);

CREATE TABLE if not exists tbl_rolusuario(
	id_rolusuario serial primary key,
	nombre varchar(50),
	descripcion varchar(250),
	estado varchar(3) default 'ACT',
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE if not exists tbl_usuario(	
	id_persona serial primary key references tbl_persona(id_persona),
	usuario varchar(50),
	clave varchar(50),
	codigo_gestor varchar(5),
	ext_ension varchar(5),
	estado varchar(3) default 'ACT',
	id_usuarioSupervisor int8 references tbl_usuario(id_persona),
	id_rolusuario int8 references tbl_rolusuario(id_rolusuario),
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);
/*****************************************************************************/

CREATE TABLE if not exists tbl_cliente(
	id_cliente serial primary key,
	nombre varchar(50),
	codigo varchar(50),
	estado varchar(3) default 'ACT',-- Activo, Inactivo
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

create table if not EXISTS tbl_cartera (
	id serial not null,
  	id_cliente serial references tbl_cliente(id_cliente),
  	codigo_cartera varchar(10) not null,
  	codigo_gestor varchar(10)  not null,
  	numero_cliente_cif varchar(30) ,
  	nombre_cliente varchar(100) not null,
  	identificacion varchar(20),
  	numero_cuenta varchar(30),
  	numero_tarjeta varchar(30),
  	saldo_colones decimal(18,6) default 0 ,  -- si monto_principales_colones y interereses_colones son 0 ponere saldo_colones   ---- si no se toma el saldo principal
  	intereses_colones decimal(18,6) default 0 ,----interereses_colones tal cual
  	id_moneda_colones serial references moneda(id_moneda) ,--id de la moneda colones
  	saldo_dolares decimal(18,6)  default 0, --- saldo_dolares tal cual
  	intereses_dolares decimal(18,6)  default 0,-- intereses dolares tal cual
  	id_moneda_dolares serial references moneda(id_moneda),--id de la moneda colones
  	tipo_producto varchar(20),
  	bucket varchar(15),
  	dias_mora varchar(15),
  	placement varchar(15),
  	fecha_asignacion timestamp,
  	estado varchar(3) default 'ING',
 	leyUsura varchar(1) default '0',
 	usuario_ingreso varchar(30) not null,
 	fecha_ingreso timestamp  default current_timestamp,
 	usuario_modifico varchar(30) default null ,
 	fecha_modifico timestamp default null,
  	primary key ("id")
 );


CREATE TABLE if not exists tbl_cliente_usuario(--
	id_cliente_usuario serial primary key,
	id_cliente int8 references tbl_cliente(id_cliente),
	id_persona int8 references tbl_usuario(id_persona),
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

/*****************************************************************************/
/*****************************************************************************/

CREATE TABLE if not exists tbl_carteraMapa(
	id_carteraMapa serial primary key,
	nombre_columna varchar(100),
    tipo_dato varchar(50),
    estado varchar(3) default 'ACT',-- Activo, Inactivo
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);


CREATE TABLE if not exists tbl_perfilCartera(
	id_perfilCartera serial primary key,
	id_cliente serial references tbl_cliente(id_cliente),
	codigo_cartera varchar(5),
	columna_excel int4,
	id_carteraMapa serial references tbl_carteraMapa(id_carteraMapa),
    estado varchar(3) default 'ACT',-- Activo, Inactivo
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);


/**************************************************/

CREATE TABLE if not exists tbl_contacto(
	id_contacto serial primary key,	
	nombre varchar(300),
	cedula varchar(60),
	estado varchar(3) default 'ACT',-- Activo, Inactivo
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE if not exists tbl_telefono(
	id_telefono serial primary key,	
	id_contacto serial references tbl_contacto(id_contacto),
	id_tipoTelefono int8 references tipotelefono(id_tipoTelefono),
	telefono varchar(50),
	ranking int default 0,
	estado varchar(3) default 'ACT',-- Activo, Inactivo
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE if not exists tbl_correo(
	id_correo serial primary key,
	id_contacto serial references tbl_contacto(id_contacto),
	correo varchar(100),
	ranking int,
	estado varchar(3) default 'ACT',-- Activo, Inactivo
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE if not exists tbl_direccion(
	id_direccion serial primary key,
	id_contacto serial references tbl_contacto(id_contacto),
	direccion varchar(300),
	ranking int,
	estado varchar(3) default 'ACT',-- Activo, Inactivo
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

/********************************************************************************/
CREATE TABLE IF NOT EXISTS Estado (
    id_Estado SERIAL PRIMARY KEY,
    codigo varchar(3),
    descripcion varchar(20),    
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);


/**************************************************/
-- tablas temporales
/**************************************************/
CREATE TABLE if not exists tmp_datos_financieros(
	codigo_cartera varchar(5),	
	operacion varchar(50),
	saldo_total numeric(19,4),
	saldo_vencido numeric(19,4),
	saldo_corriente numeric(19,4),
	mora30 numeric(19,4),
	mora60 numeric(19,4),
	mora90 numeric(19,4),
	mora120 numeric(19,4),
	mora150 numeric(19,4),
	mora180 numeric(19,4),
	mora180mas numeric(19,4),
	fecha_ult_pago date,
	valor_ult_pago numeric(19,4)
);

CREATE TABLE if not exists tmp_deudores (
    codigo_cartera varchar(5),
    codigo_gestor varchar(5),
    documento varchar(50),
    cliente varchar(50),
    nombre varchar(100),
    saldo numeric(19,4),
    saldo_dolares numeric(19,4),
	tipificacion varchar(5),
	suptipificacion varchar(5),
	fec_ult_pago date,
	fec_ingreso date,
	estatus varchar(1),    
    marca1 varchar(250),
    marca2 varchar(250),
    marca3 varchar(250)    
);

CREATE TABLE if not exists tmp_direcciones(
	codigo_cartera varchar(5),
	documento varchar(50),
	tipo int,
	direccion varchar(250),	
	estatus int
);

CREATE TABLE if not exists tmp_telefonos(
	codigo_cartera varchar(5),
	documento varchar(50),
	tipo int,
	telefono varchar(50),
	estatus int
);

CREATE TABLE if not exists tmp_operaciones(
	codigo_cartera varchar(5),
	documento varchar(50),
	operacion varchar(50),
	saldo numeric(19,4),
	saldo_dolares numeric(19,4),
	mora varchar(250),
	marca1 varchar(250),
	marca2 varchar(250),
	marca3 varchar(250),
	estatus varchar(1)
);

CREATE TABLE if not exists tmp_correos(    
	codigo_cartera varchar(5),
	documento varchar(50),
	tipo int,
	correo varchar(50),
	estatus int
);

/******************************************************************************
*************************** Ingresar Datos ************************************
*******************************************************************************/

-- Estado
/*
ING - Ingresar
ACT - Activo
INA - Inactivo
DAT - Datos

-- Estados de una promesa.
SEG - Seguimiento, antes de recordatorio
PRO - Promesa, si cuando lo llame recordando, dice que si paga maÃƒÆ’Ã‚Â±ana.
INC - Incumplida, al resivir los pago, se comprueba que no pago.
EFE - Efectiva, al resivir los pago, se comprueba que si pago.
DEL - Registro borrado
REP - Reprogramada
HIS - HistÃ³rico
select * from Estado
*/

insert into tbl_cliente(nombre,codigo,estado,usuarioIngreso) values('Credomatic','00017','ACT','hbonilla');

insert into Estado(codigo,descripcion,usuarioIngreso) values('ING','Ingresar','hbonilla');
insert into Estado(codigo,descripcion,usuarioIngreso) values('ACT','Activo','hbonilla');
insert into Estado(codigo,descripcion,usuarioIngreso) values('INA','InActivo','hbonilla');
insert into Estado(codigo,descripcion,usuarioIngreso) values('DAT','Datos','hbonilla');
insert into estado(codigo,descripcion,usuarioingreso) VALUES('SEG','Seguimiento','hbonilla');
insert into estado(codigo,descripcion,usuarioingreso) VALUES('PRO','Promesa','hbonilla');
insert into estado(codigo,descripcion,usuarioingreso) VALUES('INC','Incumplida','hbonilla');
insert into estado(codigo,descripcion,usuarioingreso) VALUES('EFE','Efectiva','hbonilla');
insert into estado(codigo,descripcion,usuarioingreso) VALUES('DEL','Registro borrado','hbonilla');
insert into estado(codigo,descripcion,usuarioingreso) VALUES('REP','Reprogramada','hbonilla');
insert into estado(codigo,descripcion,usuarioingreso) VALUES('HIS','Histórico','hbonilla');


/*
select * from list_razonmora lr;
select * from RazonMora rm;
*/
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('Desempleo titular',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('Gastos medicos titular',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('Gastos funerarios familiar',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('Disminución de ingresos bonificaciones o comisiones',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('Incapacidad permanente titular',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('Desempleo esposo(A)',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('Gastos medicos familiar',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('Gastos inesperados por accidente de tránsito',null,'hbonilla');

-- select * from TipoTelefono tt;
insert  into TipoTelefono(descripcion,codigo,usuarioIngreso) values('Movil Cliente','TMC','hbonilla');
insert  into TipoTelefono(descripcion,codigo,usuarioIngreso) values('Trabajo Cliente','TTC','hbonilla');
insert  into TipoTelefono(descripcion,codigo,usuarioIngreso) values('Casa Cliente','TCC','hbonilla');

-- select * from Tipificacion
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('Promesa de pago',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('Recordatorio de pago',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('Mensaje grabadora',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('Mensaje familiar',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('Mensaje compañero',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('No contesta',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('Contacto sin promesa',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('Ilocalizado telefonicamente',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('Retirar de cartera',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('Escalar',null,'hbonilla');



/*
 PROMESA DE PAGO
 select * from SubTipificacion
 */
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(1,'Pago total',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(1,'Pago cuotas',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(1,'Pago parcial',null,'hbonilla');


-- RECORDATORIO DE PAGO
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(2,'Pago total',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(2,'Pago cuotas',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(2,'Pago parcial',null,'hbonilla');



-- MENSAJE GRABADORA
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(3,'Titular no responde llamada',null,'hbonilla');


-- MENSAJE FAMILIAR
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Esposo(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Madre',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Padre',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Hermano(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Abuelo(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Hijo(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Tio(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Primo(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Suegro(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(5,'Mensaje compañero',null,'hbonilla');



-- NO CONTESTA
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(6,'Titular no responde llamada',null,'hbonilla');

 
 --	Contacto sin Promesa 
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(7,'Cliente sin interes de pagar',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(7,'Cliente no reconoce obligación',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(7,'Cliente colgó la llamada',null,'hbonilla');

-- ILOCALIZADO TELEFONICAMENTE
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(8,'Número reasignado',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(8,'Cambio de trabajo',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(8,'Número equivocado',null,'hbonilla');

/*
 Retirar de cartera 
 */
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(9,'Fallecidos',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(9,'Privados de libertad',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(9,'Solicitud del banco',null,'hbonilla');


insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(10,'Supervisor',null,'hbonilla');


insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'Fuera del país',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(7,'Fuera del país',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(5,'Fuera del país',null,'hbonilla');





-- MONEDAS
insert into moneda(codigo,simbolo,descripcion,usuarioIngreso) values('CRC','₡','Colon Costarricense','hbonilla');
insert into moneda(codigo,simbolo,descripcion,usuarioIngreso) values('USD','$','Dolar Estadounidense','hbonilla');

-- Rol de los usuarios del sistema.
insert into tbl_rolusuario (nombre,descripcion,usuarioIngreso) values('Gestor','Gestiona llamadas y promesas','hbonilla');
insert into tbl_rolusuario (nombre,descripcion,usuarioIngreso) values('Supervisor','Supervisa la gestion de llamadas y promesas','hbonilla');


--*****************************************************************************************
/*
 --- Mensage en Grabadora - 3
MENSAJE EN BUZON - MEB
BUZON LLENO - BLL
 */
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('Mensaje en buzon','MEB',3,null,'hbonilla');
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('Buzon lleno','BLL',3,null,'hbonilla');

/*
--- Mesaje Familiar - 4
NO TOMO MESAJE - NTM *** 
TOMO MENSAJE - TMJ   ***
*/
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('No tomo mensaje','NTM',4,null,'hbonilla');
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('Tomo mensaje','TMJ',4,null,'hbonilla');

/*
 -- Mensaje Companero - 5
NO TOMO MESAJE - NTM
TOMO MENSAJE - TMJ
*/
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('No tomo mensaje','NTM',5,null,'hbonilla');
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('Tomo mensaje','TMJ',5,null,'hbonilla');

/*
-- Ilocalizado Telefonicamente - 8
-- Numero Reasignado - 22
NO CONOCE A TITULAR
*/
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('No conoce a titular','NCT',8,22,'hbonilla');

/*
-- Ilocalizado Telefonicamente - 8
-- Cambio de Trabajo - 23
NO TOMO MESAJE - NTM ***
TOMO MENSAJE - TMJ   ***
*/
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('No tomo mensaje','NTM',8,23,'hbonilla');
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('Tomo mensaje','TMJ',8,23,'hbonilla');


/*
-- Ilocalizado Telefonicamente - 8
-- Numero Equivocado - 24
NO CONOCE A TITULAR - NCT
*/
insert into tbl_resultadogestion(descripcion,codigo,id_tipificacion,id_subtipificacion,usuarioIngreso) values('No conoce a titular','NCT',8,24,'hbonilla');

/*
select * from tbl_resultadotercero
-- TOMO MENSAJE
NO BRINDO HORA PARA LOCALIZAR - NBH
BRINDO TELEFONO o CORREO PARA LOCALIZAR AL TITULAR BTC ***
BRINDO TELEFONO DE LOCALIZAR - BTL
BRINDO EMAIL DE TITULAR - BET
NO BRINDO TELEFONO, NI EMAIL PARA LOCALIZAR A TITULAR - NTE

select * from tbl_resultadogestion tr where tr.id_tipificacion = 4;
select * from tbl_resultadogestion tr where tr.id_tipificacion = 5;
select * from tbl_resultadogestion tr where tr.id_tipificacion = 8;
*/

insert into tbl_resultadotercero(id_resultadogestion,id_tipificacion,descripcion,codigo,usuarioIngreso) values(3,4,'No brindo hora para localizar','NBH','hbonilla');
insert into tbl_resultadotercero(id_resultadogestion,id_tipificacion,descripcion,codigo,usuarioIngreso) values(4,4,'Brindo teléfono o correo para localizar al titular','BTC','hbonilla');

insert into tbl_resultadotercero(id_resultadogestion,id_tipificacion,descripcion,codigo,usuarioIngreso) values(5,5,'No brindo hora para localizar','NBH','hbonilla');
insert into tbl_resultadotercero(id_resultadogestion,id_tipificacion,descripcion,codigo,usuarioIngreso) values(6,5,'Brindo teléfono o correo para localizar al titular','BTC','hbonilla');

insert into tbl_resultadotercero(id_resultadogestion,id_tipificacion,descripcion,codigo,usuarioIngreso) values(8,8,'No brindo hora para localizar','NBH','hbonilla');
insert into tbl_resultadotercero(id_resultadogestion,id_tipificacion,descripcion,codigo,usuarioIngreso) values(9,8,'Brindo teléfono o correo para localizar al titular','BTC','hbonilla');

/******************************************************************************
******************************************************************************/

/*
******************************************************************************************************** 
*/

create table if not EXISTS tbl_central (
  id serial not null,
  nombre_central varchar(50),
  protocolo varchar(10),
  ip_central varchar(30),
  directorio varchar(30),
  primary key ("id")
 );

INSERT INTO tbl_central(nombre_central, protocolo, ip_central, directorio) VALUES('PBX', 'http://', '190.106.65.237', '/PBXPortal/');


--*************************************************************************************************
create table if not EXISTS tbl_prefijo_salida (
  id serial not null,
  id_cliente serial references tbl_cliente(id_cliente) not null,
  id_central serial references tbl_central(id) not null,
  prefijo varchar(10),
  nombre varchar(20),
  descripcion varchar(60),
  primary key ("id")
 );


--*************************************************************************************************
INSERT INTO tbl_prefijo_salida(id_cliente,id_central,prefijo, nombre, descripcion) VALUES(1,1,'4', 'Privado', 'Salida de la llamada por un número privado');
INSERT INTO tbl_prefijo_salida(id_cliente,id_central,prefijo, nombre, descripcion) VALUES(1,1,'6', 'Celular', 'Salida de la llamada por un número celular al azar');
INSERT INTO tbl_prefijo_salida(id_cliente,id_central,prefijo, nombre, descripcion)VALUES(1,1,'8', 'Telefonica', 'Salida de la llamada por un número de movistar telefonica');
INSERT INTO tbl_prefijo_salida(id_cliente,id_central,prefijo, nombre, descripcion) VALUES(1,1,'9', 'Claro', 'Salida de la llamada por un número de claro');


--*************************************************************************************************

create table if not EXISTS tbl_url_llamada (
  id serial not null,
  id_central serial references tbl_central(id) not null,
  servicio varchar(50),
  parametro varchar(100),
  descripcion varchar(50),
  primary key ("id")
);


INSERT INTO public.tbl_url_llamada
(id_central,servicio, parametro, descripcion)
VALUES(1,'llamar.php?ext=', '&numero=', 'llamar');

INSERT INTO public.tbl_url_llamada
(id_central,servicio, parametro, descripcion)
VALUES(1,'consultar.php?call_log_id=', '', 'consultar');

INSERT INTO public.tbl_url_llamada
(id_central,servicio, parametro, descripcion)
VALUES(1,'llamar.php?ext=', '&escuchar=', 'escuchar');

INSERT INTO public.tbl_url_llamada
(id_central,servicio, parametro, descripcion)
VALUES(1,'consultar.php?call_log_id=', '&bajar=1', 'descargar');

/*
*************************************************************************************************
*************************************************************************************************
*/
create table if not EXISTS tbl_pagos_historial (
	"id" serial not null,
  	"codigo_cartera" varchar(6) not null,
  	"numero_cuenta" varchar(20)  not null,
  	"fecha_pago" timestamp,
  	"codigo_gestor" varchar(20) ,
  	"tipo_pago" varchar(20),
  	"total_colones" decimal(18,6) default 0,
  	"total_dolares" decimal(18,6) default 0,
  	"usuario_ingreso" varchar(30) not null,
 	"fecha_ingreso" timestamp not null default current_timestamp,
 	"usuario_modifico" varchar(30) default '' ,
 	"fecha_modifico" timestamp default null ,
    primary key ("id")
);

/*
*************************************************************************************************
*************************************************************************************************
*/
CREATE TABLE IF NOT EXISTS tbl_GestionSaldo (
    id_GestionSaldo SERIAL PRIMARY KEY,
    id_gestion BIGSERIAL references tbl_gestion(id_gestion),
    saldo decimal(18,6) default 0,
    intereses decimal(18,6) default 0,
    id_Moneda SERIAL references moneda(id_Moneda),
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);
/******************************************************************************
************************************** fin ************************************
*******************************************************************************/





 