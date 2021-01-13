/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblDeudor;
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
public class DeudorDaoImpl implements DeudorDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblDeudor> findAll() {
        TypedQuery<TblDeudor> query = em.createNamedQuery("TblDeudor.findAll",TblDeudor.class);
        List<TblDeudor> results = query.getResultList();
        return results;
    }

    @Override
    public TblDeudor findById(TblDeudor obj) {
        return em.find(TblDeudor.class, obj.getIdDeudor());
    }

    @Override
    public List<TblDeudor> findByCodigoGestor(TblDeudor obj) {
        Query query = em.createNamedQuery("TblDeudor.findByCodigoGestor",TblDeudor.class);
        query.setParameter("codigoGestor", obj.getCodigoGestor());
        List<TblDeudor> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(TblDeudor obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblDeudor obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblDeudor obj) {
        em.merge(obj);
        em.remove(obj);
    }

    @Override
    public List<TblDeudor> findByCarteraGestorDocumento(TblDeudor obj) {
        Query query = em.createNamedQuery("TblDeudor.findByCarteraGestorDocumento",TblDeudor.class);
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        query.setParameter("codigoGestor", obj.getCodigoGestor());
        query.setParameter("documento", obj.getDocumento());
        List<TblDeudor> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblDeudor> findByGestorIfNotExistsGestion(TblDeudor obj) {
        String sqlNative = "SELECT td.*\n"
                + "  FROM tbl_deudor td\n"
                + " WHERE NOT EXISTS (select tg.id_gestion\n"
                + "                    from tbl_gestion tg inner join tbl_promesa tp on tp.id_gestion = tg.id_gestion\n"
                + "                    where tg.codigo_cartera = td.codigo_cartera \n"
                + "                      and tg.codigo_gestor = ?1\n"
                + "                      and tg.documento = td.documento)\n"
                + "    and td.saldo > 0\n"
                + "    and td.codigo_gestor = ?2\n"
                + "    and date_trunc('day', td.fechaingreso) = to_date(?3,'YYYY-MM-DD')";
        Query query = em.createNativeQuery(sqlNative,TblDeudor.class);
        /*
        System.out.println("CodigoGestor: "+obj.getCodigoGestor());
        System.out.println("FechaIngreso: "+obj.getFechaIngreso());
        System.out.println(query.toString());
        */
        List<TblDeudor> results = query.setParameter(1, obj.getCodigoGestor()).setParameter(2, obj.getCodigoGestor()).setParameter(3, obj.getFechaIngreso()).getResultList();
        
        return results;
    }

}
