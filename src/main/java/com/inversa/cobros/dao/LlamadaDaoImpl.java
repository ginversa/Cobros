/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblLlamada;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Z420WK
 */

@Stateless
public class LlamadaDaoImpl implements LlamadaDao{
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblLlamada> findAll() {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findAll", TblLlamada.class);
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public TblLlamada findById(TblLlamada obj) {
        return em.find(TblLlamada.class, obj.getIdLlamada());
    }

    @Override
    public TblLlamada findByCallLogId(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByCallLogId", TblLlamada.class);
        query.setParameter("callLogId", obj.getCallLogId());
        TblLlamada results = query.getSingleResult();
        return results;
    }

    @Override
    public List<TblLlamada> findByDateIni(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByDateIni", TblLlamada.class);
        query.setParameter("dateIni", obj.getDateIni());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByDateEnd(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByDateEnd", TblLlamada.class);
        query.setParameter("dateEnd", obj.getDateEnd());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByCallFromNumber(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByCallFromNumber", TblLlamada.class);
        query.setParameter("callFromNumber", obj.getCallFromNumber());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByCallToNumber(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByCallToNumber", TblLlamada.class);
        query.setParameter("callToNumber", obj.getCallToNumber());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByDialstatus(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByDialstatus", TblLlamada.class);
        query.setParameter("dialstatus", obj.getDialstatus());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByEstado(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByEstado", TblLlamada.class);
        query.setParameter("estado", obj.getEstado());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByUsuarioingreso(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByUsuarioingreso", TblLlamada.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByFechaingreso(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByFechaingreso", TblLlamada.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByUsuariomodifico(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByUsuariomodifico", TblLlamada.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByFechamodifico(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByFechamodifico", TblLlamada.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(TblLlamada obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblLlamada obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblLlamada obj) {
        em.merge(obj);
        em.remove(obj);
    }
    
}
