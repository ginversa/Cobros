
CREATE OR REPLACE FUNCTION gestiones_productividad_porHora(pFechaIngreso date, pCodigoCartera text)
 RETURNS TABLE(nombrecartera character varying, indicador text, hora7am numeric, hora8am numeric, hora9am numeric, hora10am numeric, hora11am numeric, hora12am numeric, hora1am numeric, hora2am numeric, hora3am numeric, hora4am numeric, hora5am numeric, hora6am numeric, total numeric)
 LANGUAGE plpgsql AS $function$
BEGIN    
/************************************* Clasificacion de las Gestiones **********************************************/
create temporary table "tmp" on commit drop as select distinct tl.id_gestion, tl.id_llamada,tl.fechaingreso, tl.call_from_number , tl.id_tipificacion , tl.id_subtipificacion, t.descripcion Descripcion_Tipificacion, s.descripcion Descripcion_Subtipitificacion,
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
select distinct tmp_01.id_gestion
     , tmp_01.id_llamada
     , tmp_01.fechaingreso
     , tmp_01.Descripcion_Tipificacion
     , tmp_01.Descripcion_Subtipitificacion 
  from tmp tmp_01
 where concat(tmp_01.peso_gestion,tmp_01.id_gestion) in (select distinct concat(min(tmp_02.peso_gestion),tmp_02.id_gestion)
                                             from tmp tmp_02
                                         group by tmp_02.id_gestion)
order by tmp_01.id_gestion;

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

/************************************* Resumen **********************************************/
create temporary table "tmp3" on commit drop as
select 
a.nombre_cartera,
hora, 
count(distinct(usuarioingreso))agentes,
count(distinct(a.identificacion))workload,
count(distinct(rpc))rpc,
round(cast(cast(count(distinct(rpc))as float)/cast(nullif(count(distinct(a.identificacion)),0) as float)as numeric),4) as por_RPC,
count(distinct(pdp))pdp,
round(cast(cast(count(distinct(pdp))as float)/cast(nullif(count(distinct(rpc)),0) as float)as numeric),4) as por_PDP,
sum(b.saldodolar) as valorworkload,
sum(d.saldodolar) as valor_pdp,
sum(d.saldodolar)/nullif(count(distinct(pdp)),0) as CCP
from tmp2 a left join tmp_saldos b on (a.nombre_cartera=b.nombre_cartera and a.identificacion=b.identificacion and a.id_gestion=b.id_gestion)
left join tmp_saldos d on (a.nombre_cartera=d.nombre_cartera and a.pdp=d.identificacion and a.id_gestion=d.id_gestion)
group by a.nombre_cartera,hora;

RETURN QUERY
/************************************* Pivot Resumen **********************************************/
select nombre_cartera,'01 - Agentes' as indicador,
sum(case when hora = '7' then agentes else 0 end) as hora7am,
sum(case when hora = '8' then agentes else 0 end) as hora8am,
sum(case when hora = '9' then agentes else 0 end) as hora9am,
sum(case when hora = '10' then agentes else 0 end) as hora10am,
sum(case when hora = '11' then agentes else 0 end) as hora11am,
sum(case when hora = '12' then agentes else 0 end) as hora12md,
sum(case when hora = '13' then agentes else 0 end) as hora1pm,
sum(case when hora = '14' then agentes else 0 end) as hora2pm,
sum(case when hora = '15' then agentes else 0 end) as hora3pm,
sum(case when hora = '16' then agentes else 0 end) as hora4pm,
sum(case when hora = '17' then agentes else 0 end) as hora5pm,
sum(case when hora = '18' then agentes else 0 end) as hora6pm,
round(cast(avg(agentes)as numeric),0) total
from tmp3 group by nombre_cartera
union all 
select nombre_cartera,'02 - Workload' as indicador,
sum(case when hora = '7' then workload else 0 end) as hora7am,
sum(case when hora = '8' then workload else 0 end) as hora8am,
sum(case when hora = '9' then workload else 0 end) as hora9am,
sum(case when hora = '10' then workload else 0 end) as hora10am,
sum(case when hora = '11' then workload else 0 end) as hora11am,
sum(case when hora = '12' then workload else 0 end) as hora12md,
sum(case when hora = '13' then workload else 0 end) as hora1pm,
sum(case when hora = '14' then workload else 0 end) as hora2pm,
sum(case when hora = '15' then workload else 0 end) as hora3pm,
sum(case when hora = '16' then workload else 0 end) as hora4pm,
sum(case when hora = '17' then workload else 0 end) as hora5pm,
sum(case when hora = '18' then workload else 0 end) as hora6pm,
sum(workload) total
from tmp3 group by nombre_cartera
union all 
select nombre_cartera,'03 - RPC' as indicador,
sum(case when hora = '7' then rpc else 0 end) as hora7,
sum(case when hora = '8' then rpc else 0 end) as hora8,
sum(case when hora = '9' then rpc else 0 end) as hora9,
sum(case when hora = '10' then rpc else 0 end) as hora10,
sum(case when hora = '11' then rpc else 0 end) as hora11,
sum(case when hora = '12' then rpc else 0 end) as hora12,
sum(case when hora = '13' then rpc else 0 end) as hora1,
sum(case when hora = '14' then rpc else 0 end) as hora2,
sum(case when hora = '15' then rpc else 0 end) as hora3,
sum(case when hora = '16' then rpc else 0 end) as hora4,
sum(case when hora = '17' then rpc else 0 end) as hora5,
sum(case when hora = '18' then rpc else 0 end) as hora6,
sum(rpc) total
from tmp3 group by nombre_cartera
union all 
select nombre_cartera,'04 - PDP' as indicador,
sum(case when hora = '7' then pdp else 0 end) as hora7,
sum(case when hora = '8' then pdp else 0 end) as hora8,
sum(case when hora = '9' then pdp else 0 end) as hora9,
sum(case when hora = '10' then pdp else 0 end) as hora10,
sum(case when hora = '11' then pdp else 0 end) as hora11,
sum(case when hora = '12' then pdp else 0 end) as hora12,
sum(case when hora = '13' then pdp else 0 end) as hora1,
sum(case when hora = '14' then pdp else 0 end) as hora2,
sum(case when hora = '15' then pdp else 0 end) as hora3,
sum(case when hora = '16' then pdp else 0 end) as hora4,
sum(case when hora = '17' then pdp else 0 end) as hora5,
sum(case when hora = '18' then pdp else 0 end) as hora6,
sum(pdp) total
from tmp3 group by nombre_cartera
union all 
select nombre_cartera,'05 - Balance' as indicador,
sum(case when hora = '7' then valorworkload else 0 end) as hora7,
sum(case when hora = '8' then valorworkload else 0 end) as hora8,
sum(case when hora = '9' then valorworkload else 0 end) as hora9,
sum(case when hora = '10' then valorworkload else 0 end) as hora10,
sum(case when hora = '11' then valorworkload else 0 end) as hora11,
sum(case when hora = '12' then valorworkload else 0 end) as hora12,
sum(case when hora = '13' then valorworkload else 0 end) as hora1,
sum(case when hora = '14' then valorworkload else 0 end) as hora2,
sum(case when hora = '15' then valorworkload else 0 end) as hora3,
sum(case when hora = '16' then valorworkload else 0 end) as hora4,
sum(case when hora = '17' then valorworkload else 0 end) as hora5,
sum(case when hora = '18' then valorworkload else 0 end) as hora6,
sum(valorworkload) total
from tmp3 group by nombre_cartera
union all 
select nombre_cartera,'06 - Balance Cash Collected' as indicador,
sum(case when hora = '7' then valor_pdp else 0 end) as hora7,
sum(case when hora = '8' then valor_pdp else 0 end) as hora8,
sum(case when hora = '9' then valor_pdp else 0 end) as hora9,
sum(case when hora = '10' then valor_pdp else 0 end) as hora10,
sum(case when hora = '11' then valor_pdp else 0 end) as hora11,
sum(case when hora = '12' then valor_pdp else 0 end) as hora12,
sum(case when hora = '13' then valor_pdp else 0 end) as hora1,
sum(case when hora = '14' then valor_pdp else 0 end) as hora2,
sum(case when hora = '15' then valor_pdp else 0 end) as hora3,
sum(case when hora = '16' then valor_pdp else 0 end) as hora4,
sum(case when hora = '17' then valor_pdp else 0 end) as hora5,
sum(case when hora = '18' then valor_pdp else 0 end) as hora6,
sum(valor_pdp) total
from tmp3 group by nombre_cartera
union all 
select nombre_cartera,'07 - Cash Collected  By  Promise' as indicador,
sum(case when hora = '7' then CCP else 0 end) as hora7,
sum(case when hora = '8' then CCP else 0 end) as hora8,
sum(case when hora = '9' then CCP else 0 end) as hora9,
sum(case when hora = '10' then CCP else 0 end) as hora10,
sum(case when hora = '11' then CCP else 0 end) as hora11,
sum(case when hora = '12' then CCP else 0 end) as hora12,
sum(case when hora = '13' then CCP else 0 end) as hora1,
sum(case when hora = '14' then CCP else 0 end) as hora2,
sum(case when hora = '15' then CCP else 0 end) as hora3,
sum(case when hora = '16' then CCP else 0 end) as hora4,
sum(case when hora = '17' then CCP else 0 end) as hora5,
sum(case when hora = '18' then CCP else 0 end) as hora6,
sum(CCP) total
from tmp3 group by nombre_cartera
union all 
select nombre_cartera,'08 - % RPC' as indicador,
sum(case when hora = '7' then por_RPC else 0 end) as hora7,
sum(case when hora = '8' then por_RPC else 0 end) as hora8,
sum(case when hora = '9' then por_RPC else 0 end) as hora9,
sum(case when hora = '10' then por_RPC else 0 end) as hora10,
sum(case when hora = '11' then por_RPC else 0 end) as hora11,
sum(case when hora = '12' then por_RPC else 0 end) as hora12,
sum(case when hora = '13' then por_RPC else 0 end) as hora1,
sum(case when hora = '14' then por_RPC else 0 end) as hora2,
sum(case when hora = '15' then por_RPC else 0 end) as hora3,
sum(case when hora = '16' then por_RPC else 0 end) as hora4,
sum(case when hora = '17' then por_RPC else 0 end) as hora5,
sum(case when hora = '18' then por_RPC else 0 end) as hora6,
sum(cast(nullif(por_RPC,0)as numeric))/count(case when por_RPC >= 0.0001 then por_RPC else null end) as total
from tmp3 group by nombre_cartera
union all 
select nombre_cartera,'09 - % PDP' as indicador,
sum(case when hora = '7' then por_pdp else 0 end) as hora7,
sum(case when hora = '8' then por_pdp else 0 end) as hora8,
sum(case when hora = '9' then por_pdp else 0 end) as hora9,
sum(case when hora = '10' then por_pdp else 0 end) as hora10,
sum(case when hora = '11' then por_pdp else 0 end) as hora11,
sum(case when hora = '12' then por_pdp else 0 end) as hora12,
sum(case when hora = '13' then por_pdp else 0 end) as hora1,
sum(case when hora = '14' then por_pdp else 0 end) as hora2,
sum(case when hora = '15' then por_pdp else 0 end) as hora3,
sum(case when hora = '16' then por_pdp else 0 end) as hora4,
sum(case when hora = '17' then por_pdp else 0 end) as hora5,
sum(case when hora = '18' then por_pdp else 0 end) as hora6,
avg(por_pdp) total
from tmp3 group by nombre_cartera;

END;
$function$
;
