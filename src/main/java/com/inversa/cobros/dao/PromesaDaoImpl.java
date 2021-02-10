/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblPromesa;
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
public class PromesaDaoImpl implements PromesaDao{
    
    @PersistenceContext(unitName="cobrosPU")
    EntityManager em;

    @Override
    public List<TblPromesa> findAll() {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findAll", TblPromesa.class);
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public TblPromesa findById(TblPromesa obj) {
        return em.find(TblPromesa.class, obj.getIdPromesa());
    }

    @Override
    public List<TblPromesa> findByOperacion(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByOperacion", TblPromesa.class);
        query.setParameter("operacion", obj.getOperacion());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByTelefono(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByTelefono", TblPromesa.class);
        query.setParameter("telefono", obj.getTelefono());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByFechaPago(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByFechaPago", TblPromesa.class);
        query.setParameter("fechaPago", obj.getFechaPago());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByUsuarioingreso(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByUsuarioingreso", TblPromesa.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByFechaingreso(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByFechaingreso", TblPromesa.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByUsuariomodifico(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByUsuariomodifico", TblPromesa.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByFechamodifico(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByFechamodifico", TblPromesa.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(TblPromesa obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblPromesa obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblPromesa obj) {
        em.merge(obj);
        em.remove(obj);
    }

    @Override
    public List<TblPromesa> findByFechaPagoAndUsuarioIngreso(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByFechaPagoAndUsuarioIngreso", TblPromesa.class);
        query.setParameter("fechaPago", obj.getFechaPago());
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblPromesa> results = query.getResultList();
        return results;
    }
    
}
