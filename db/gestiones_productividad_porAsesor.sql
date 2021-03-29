
CREATE OR REPLACE FUNCTION gestiones_productividad_porAsesor(pFechaIngreso date, pCodigoCartera text)
 RETURNS TABLE(nombreCartera character varying, usuario character varying, fechaGestion float8, agentes bigint, workload bigint, contactoTitular bigint, por_contactoTitular numeric, promesadepago bigint, por_promesadepago numeric, valorworkload numeric, valor_promesadepago numeric, valorpromediopromesa numeric)
 LANGUAGE plpgsql AS $function$
BEGIN  
/************************************* Clasificacion de las Gestiones **********************************************/
create temporary table "tmp" on commit drop as 
select distinct tl.id_gestion, tl.id_llamada,tl.fechaingreso, tl.call_from_number , tl.id_tipificacion , tl.id_subtipificacion, t.descripcion Descripcion_Tipificacion, s.descripcion Descripcion_Subtipitificacion,
case 
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '1.1' then 1 
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '1.2' then 2
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '1.3' then 3
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '2.4' then 4
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '2.5' then 5
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '2.6' then 6
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.7' then 7
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.8' then 8
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.9' then 9
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.10' then 10
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.11' then 11
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.12' then 12
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.13' then 13
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.14' then 14
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.15' then 15
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '3.16' then 16
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '4.17' then 17
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '4.18' then 18
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '5.19' then 19
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '5.20' then 20
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '5.21' then 21
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '5.22' then 22
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '5.23' then 23
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '5.24' then 24
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '5.25' then 25
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '6.26' then 26
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '6.27' then 27
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '7.28' then 28
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '7.29' then 29
when cast(tl.id_tipificacion as text)||'.'|| cast(tl.id_subtipificacion as text) = '8.30' then 30
end Peso_Gestion
from tbl_llamada tl  left join tbl_gestion b on (b.id_gestion=tl.id_gestion)
left join tipificacion t on 
tl.id_tipificacion = t.id_tipificacion 
left join subtipificacion s on 
tl.id_subtipificacion = s.id_subtipificacion and tl.id_tipificacion=s.id_tipificacion 
where cast(tl.fechaingreso as date) = pFechaIngreso and b.codigo_cartera = pCodigoCartera
order by tl.id_gestion, tl.fechaingreso, peso_gestion;

/************************************* Mejor Gestion **********************************************/
create temporary table "tmp1" on commit drop as 
select distinct id_gestion, id_llamada,fechaingreso,Descripcion_Tipificacion,Descripcion_Subtipitificacion 
from tmp where concat(peso_gestion,id_gestion) in (select distinct concat(min(peso_gestion),id_gestion) from tmp group by id_gestion) order by id_gestion;

/************************************* Determina RPC y PDP **********************************************/
create temporary table "tmp2" on commit drop as
select distinct (a.id_gestion),codigo_cartera,nombre_cartera, identificacion,operacion,EXTRACT(DAY FROM a.fecha_gestion)fecha_gestion,EXTRACT(hour FROM fecha_gestion)hora,usuarioingreso ,Descripcion_Tipificacion,Descripcion_Subtipitificacion,
case 
when Descripcion_Tipificacion in ('Promesa de pago','Recordatorio de pago','Contacto sin promesa','Escalar') then identificacion
end RPC,
case 
when Descripcion_Tipificacion in ('Promesa de pago') then identificacion
end PDP
from tbl_gestion a inner join tmp1 b on (a.id_gestion=b.id_gestion) where cast(a.fecha_gestion as date) = pFechaIngreso and a.codigo_cartera = pCodigoCartera;

/************************************* Saldos **********************************************/
create temporary table "tmp_saldos1" on commit drop as
select distinct (a.operacion),a.id_gestion,a.identificacion,a.nombre_cartera,(case when tg.id_moneda =1 then (tg.intereses+tg.saldo_cartera)/616 else (tg.intereses+tg.saldo_cartera) end)as saldodolar  --((b.saldo_colones+b.intereses_colones)/616)+(b.saldo_dolares +b.intereses_dolares ) as saldodolar
from tmp2 a left join tbl_gestionsaldo tg  on (a.id_gestion=tg.id_gestion); 

create temporary table "tmp_saldos" on commit drop as
select operacion,identificacion,id_gestion,nombre_cartera,sum(saldodolar) as saldodolar
from tmp_saldos1 group by operacion,identificacion,nombre_cartera,id_gestion;

RETURN QUERY

/************************************* Resumen **********************************************/
--create temporary table "tmp3" on commit drop as
select 
a.nombre_cartera,
usuarioingreso,
fecha_gestion,
count(distinct(usuarioingreso))agentes,
count(distinct(a.identificacion))workload,
count(distinct(rpc))rpc,
round(cast(cast(count(distinct(rpc))as float)/cast(nullif(count(distinct(a.identificacion)),0) as float)as numeric),4) as por_RPC,
count(distinct(pdp))pdp,
nullif(round(cast(cast(count(distinct(pdp))as float)/cast(nullif(count(distinct(rpc)),0) as float)as numeric),4),0) as por_PDP,
nullif(sum(b.saldodolar),0) as valorworkload,
nullif(sum(d.saldodolar),0) as valor_pdp,
nullif(sum(d.saldodolar)/nullif(count(distinct(pdp)),0),0) as CCP
from tmp2 a left join tmp_saldos b on (a.nombre_cartera=b.nombre_cartera and a.identificacion=b.identificacion and a.id_gestion=b.id_gestion)
left join tmp_saldos d on (a.nombre_cartera=d.nombre_cartera and a.pdp=d.identificacion and a.id_gestion=d.id_gestion)
group by a.nombre_cartera,usuarioingreso,
fecha_gestion;

END;
$function$
;
