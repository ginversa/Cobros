/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblCola;
import com.inversa.cobros.model.TblFiltrocola;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Z420WK
 */
@Stateless
public class FiltrocolaDaoImpl implements FiltrocolaDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblFiltrocola> findAll() {
        TypedQuery<TblFiltrocola> query = em.createNamedQuery("TblFiltrocola.findAll", TblFiltrocola.class);
        List<TblFiltrocola> results = query.getResultList();
        return results;
    }

    @Override
    public TblFiltrocola findByIdFiltrocola(TblFiltrocola obj) {
        return em.find(TblFiltrocola.class, obj.getIdFiltrocola());
    }

    @Override
    public void insert(TblFiltrocola obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblFiltrocola obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblFiltrocola obj) {
        em.merge(obj);
        em.remove(obj);
    }

    @Override
    public List<TblFiltrocola> findByCodigoCartera(TblFiltrocola obj) {
        TypedQuery<TblFiltrocola> query = em.createNamedQuery("TblFiltrocola.findByCodigoCartera", TblFiltrocola.class);
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<TblFiltrocola> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCola> findByCodigoGestorANDCodigoCarteraANDFiltro(Long IdFiltrocola, String codigo_cartera, String codigo_gestor, String codigoTipificacion, String codigoSubTipificacion, String codigoRazonMora, Long uno, Long dos, Long mesUP, Long mesPP) {
        String sql = "select externo.id_filtrocola\n"
                + "     , externo.id_Cola\n"
                + "     , externo.codigo_cartera\n"
                + "     , externo.codigo_gestor\n"
                + "     , externo.identificacion\n"
                + "     , externo.saldo_colones\n"
                + "     , externo.saldo_dolares\n"
                + "     , externo.estado\n"
                + "     , externo.id_gestion\n"
                + "     , externo.usuarioIngreso\n"
                + "     , externo.fechaIngreso\n"
                + "     , externo.usuarioModifico\n"
                + "     , externo.fechaModifico from (select null id_filtrocola, row_number() over() as id_Cola, cartera.codigo_cartera, null codigo_gestor, cartera.identificacion, sum(cartera.saldo_colones) saldo_colones, sum(cartera.saldo_dolares) saldo_dolares, 'SIN' estado, null id_gestion, null usuarioIngreso, null fechaIngreso, null usuarioModifico, null fechaModifico from tbl_cartera cartera where cartera.codigo_cartera = ?1 and cartera.estado != 'GES'";// and cartera.codigo_gestor = ?2

        /*
        if (codigoTipificacion != null) {
            sql = sql + " and exists (select tl.id_llamada from tbl_llamada tl where tl.operacion = cartera.numero_cuenta and tl.id_tipificacion = (select t.id_tipificacion from tipificacion t where t.codigo = '"+codigoTipificacion+"'))";
        }

        if (codigoSubTipificacion != null) {
            sql = sql + " and exists (select tl.id_llamada from tbl_llamada tl where tl.operacion = cartera.numero_cuenta and tl.id_subtipificacion = (select s.id_subtipificacion from subtipificacion s where s.codigo = '"+codigoSubTipificacion+"'))";
        }

        if (codigoRazonMora != null) {
            sql = sql + " and exists (select tl.id_llamada from tbl_llamada tl where tl.operacion = cartera.numero_cuenta and tl.idrazonmora = (select r.idrazonmora from razonmora r where r.codigo = '"+codigoRazonMora+"'))";
        }

        if ((uno != null && uno > 0)) {
            if(dos != null && dos > 0){
                sql = sql + " and (select (CURRENT_DATE::date - tl.fechaingreso::date) Dias from tbl_gestion tg inner join tbl_llamada tl on tl.id_gestion = tg.id_gestion where tg.identificacion = cartera.identificacion order by tl.id_llamada desc limit 1) between "+uno+" and "+dos;
            }else{
                sql = sql + " and (select (CURRENT_DATE::date - tl.fechaingreso::date) Dias from tbl_gestion tg inner join tbl_llamada tl on tl.id_gestion = tg.id_gestion where tg.identificacion = cartera.identificacion order by tl.id_llamada desc limit 1) > 15";
            }
        }

        if (mesUP != null && mesUP > 0) {
            if(mesUP == 4){                
                sql = sql + " and exists (select tph.id from tbl_pagos_historial tph where tph.numero_cuenta = cartera.numero_cuenta and tph.fecha_pago <= CURRENT_DATE + INTERVAL '-4 months')";
            }else{
                sql = sql + " and exists (select tph.id from tbl_pagos_historial tph where tph.numero_cuenta = cartera.numero_cuenta and tph.fecha_pago between CURRENT_DATE + INTERVAL '-"+mesUP+" months' and CURRENT_DATE)";
            }            
        }

        if (mesPP != null && mesPP > 0) {
            if(mesPP == 4){
                sql = sql + " and exists (select tp.id_promesa from tbl_promesa tp where tp.operacion = cartera.numero_cuenta and tp.idestadopromesa = (select e.idestadopromesa from estadopromesa e where e.codigo = 'SEG') and tp.fecha_pago >= CURRENT_DATE + INTERVAL '4 months' order by tp.id_promesa desc);";
                
            }else{
                sql = sql + " and exists (select tp.id_promesa from tbl_promesa tp where tp.operacion = cartera.numero_cuenta and tp.idestadopromesa = (select e.idestadopromesa from estadopromesa e where e.codigo = 'SEG') and tp.fecha_pago between CURRENT_DATE and CURRENT_DATE + INTERVAL '"+mesPP+" months' order by tp.id_promesa desc)";
            }            
        }
         */
 /*
        Agrupacion por identificacion.
        sumar saldos.
         */
        sql = sql + " group by cartera.codigo_cartera, cartera.identificacion) externo";
        sql = sql + " order by externo.saldo_colones desc";

        Query query = em.createNativeQuery(sql, TblCola.class);
        query.setParameter(1, codigo_cartera);
        //query.setParameter(2, codigo_gestor);
        List<TblCola> results = query.getResultList();
        return results;
    }

}
