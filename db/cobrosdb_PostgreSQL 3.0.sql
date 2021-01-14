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
  	nombre_cliente varchar(50),
  	documento varchar(50),	
  	codigo_gestor varchar(5),
  	saldo DECIMAL(19,4) default 0,
  	moneda VARCHAR(3),-- CRC
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
  	fecha_pago timestamp,
  	mtoPago DECIMAL(19,4) default 0,
  	moneda VARCHAR(3),
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
	estado varchar(3) default 'ACT',-- Activo, Inactivo
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE if not exists tbl_cartera(
	id_cartera BIGSERIAL primary key,
	id_cliente serial references tbl_cliente(id_cliente),
	nombre varchar(100),
	codigo_cartera varchar(5),
	usuarioIngreso varchar(50),
	fechaIngreso timestamp default CURRENT_TIMESTAMP,
    usuarioModifico varchar(50),
	fechaModifico timestamp
);

CREATE TABLE if not exists tbl_cliente_usuario(
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
	ranking int,
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
ACT - Activo
INA - Inactivo
ING - Ingresar
DAT - Datos
select * from Estado
 */

insert into Estado(codigo,descripcion,usuarioIngreso) values('ING','Ingresar','hbonilla');
insert into Estado(codigo,descripcion,usuarioIngreso) values('ACT','Activo','hbonilla');
insert into Estado(codigo,descripcion,usuarioIngreso) values('INA','InActivo','hbonilla');
insert into Estado(codigo,descripcion,usuarioIngreso) values('DAT','Datos','hbonilla');


/*
select * from list_razonmora lr;
select * from RazonMora rm;
*/
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('DESEMPLEO TITULAR',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('GASTOS MEDICOS TITULAR',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('GASTOS FUNERARIOS FAMILIAR',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('DISMINUCION DE INGRESOS BONIFICACIONES O COMISIONES',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('INCAPACIDAD PERMANENTE TITULAR',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('DESEMPLEO ESPOSO(A)',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('GASTOS MEDICOS FAMILIAR',null,'hbonilla');
insert into razonmora(descripcion,codigo_cartera, usuarioIngreso) values('GASTOS INESPERADOS POR ACCIDENTE DE TRANSITO',null,'hbonilla');

-- select * from TipoTelefono tt;
insert  into TipoTelefono(descripcion,codigo,usuarioIngreso) values('Movil Cliente','TMC','hbonilla');
insert  into TipoTelefono(descripcion,codigo,usuarioIngreso) values('Trabajo Cliente','TTC','hbonilla');
insert  into TipoTelefono(descripcion,codigo,usuarioIngreso) values('Casa Cliente','TCC','hbonilla');

-- select * from Tipificacion
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('PROMESA DE PAGO',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('RECORDATORIO DE PAGO',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('MENSAJE GRABADORA',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('MENSAJE FAMILIAR',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('MENSAJE COMPAÑERO',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('NO CONTESTA',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('CONTACTO SIN PROMESA',null,'hbonilla');
insert  into Tipificacion(descripcion,codigo_cartera,usuarioIngreso) values('ILOCALIZADO TELEFONICAMENTE',null,'hbonilla');



/*
 PROMESA DE PAGO
 select * from SubTipificacion
 */
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(1,'PAGO TOTAL',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(1,'PAGO CUOTAS',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(1,'PAGO PARCIAL',null,'hbonilla');


-- RECORDATORIO DE PAGO
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(2,'PAGO TOTAL',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(2,'PAGO CUOTAS',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(2,'PAGO PARCIAL',null,'hbonilla');



-- MENSAJE GRABADORA
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(3,'TITULAR NO RESPONDIO LLAMADA',null,'hbonilla');



-- MENSAJE FAMILIAR
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'ESPOSO(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'MADRE',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'PADRE',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'HERMANO(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'ABUELO(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'HIJO(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'TIO(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'PRIMO(A)',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(4,'SUEGRO(A)',null,'hbonilla');


-- MENSAJE COMPAÃƒâ€˜ERO
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(5,'MENSAJE COMPAÑERO',null,'hbonilla');



-- NO CONTESTA
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(6,'TITULAR NO RESPONDIO LLAMADA',null,'hbonilla');

 
 --	Contacto sin Promesa
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(7,'CLIENTE SIN INTERESES DE PAGAR',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(7,'CLIENTE NO RECONOCE OBLIGACION',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(7,'CLIENTE COLGO LA LLAMADA',null,'hbonilla');

-- ILOCALIZADO TELEFONICAMENTE
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(8,'NUMERO REASIGNADO',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(8,'CAMBIO DE TRABAJO',null,'hbonilla');
insert into SubTipificacion(id_tipificacion,descripcion,codigo_cartera,usuarioIngreso) values(8,'NUMERO EQUIVOCADO',null,'hbonilla');

-- MONEDAS
insert into moneda(codigo,simbolo,descripcion,usuarioIngreso) values('CRC','₡','Colon Costarricense','hbonilla');
insert into moneda(codigo,simbolo,descripcion,usuarioIngreso) values('USD','$','Dolar Estadounidense','hbonilla');

-- Rol de los usuarios del sistema.
insert into tbl_rolusuario (nombre,descripcion,usuarioIngreso) values('Gestor','Gestiona llamadas y promesas','hbonilla');
insert into tbl_rolusuario (nombre,descripcion,usuarioIngreso) values('Supervisor','Supervisa la gestion de llamadas y promesas','hbonilla');
/******************************************************************************
************************************** fin ************************************
*******************************************************************************/
