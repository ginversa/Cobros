/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblPersona;
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
public class PersonaDaoImpl implements PersonaDao{
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblPersona> findAll() {
        TypedQuery<TblPersona> query = em.createNamedQuery("TblPersona.findAll", TblPersona.class);
        List<TblPersona> results = query.getResultList();
        return results;
    }

    @Override
    public TblPersona findById(TblPersona obj) {
        return em.find(TblPersona.class, obj.getIdPersona());
    }

    @Override
    public TblPersona findByNombre(TblPersona obj) {
        Query query = em.createNamedQuery("TblPersona.findByNombre", TblPersona.class);
        query.setParameter("nombre", obj.getNombre());
        TblPersona results = (TblPersona) query.getSingleResult();
        return results;
    }

    @Override
    public TblPersona findByCedula(TblPersona obj) {
        Query query = em.createNamedQuery("TblPersona.findByCedula", TblPersona.class);
        query.setParameter("cedula", obj.getCedula());
        TblPersona results = (TblPersona) query.getSingleResult();
        return results;
    }

    @Override
    public List<TblPersona> findByUsuarioingreso(TblPersona obj) {
        Query query = em.createNamedQuery("TblPersona.findByUsuarioingreso", TblPersona.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblPersona> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPersona> findByFechaingreso(TblPersona obj) {
        Query query = em.createNamedQuery("TblPersona.findByFechaingreso", TblPersona.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<TblPersona> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPersona> findByUsuariomodifico(TblPersona obj) {
        Query query = em.createNamedQuery("TblPersona.findByUsuariomodifico", TblPersona.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<TblPersona> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPersona> findByFechamodifico(TblPersona obj) {
         Query query = em.createNamedQuery("TblPersona.findByFechamodifico", TblPersona.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<TblPersona> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(TblPersona obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblPersona obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblPersona obj) {
        em.merge(obj);
        em.remove(obj);
    }
    
}
