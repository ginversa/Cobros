/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Razonmora;
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
public class RazonmoraDaoImpl implements RazonmoraDao{
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<Razonmora> findAll() {
        TypedQuery<Razonmora> query = em.createNamedQuery("Razonmora.findAll", Razonmora.class);
        List<Razonmora> results = query.getResultList();
        return results;
    }

    @Override
    public Razonmora findById(Razonmora obj) {
        return em.find(Razonmora.class, obj.getIdrazonmora());
    }

    @Override
    public List<Razonmora> findByCodigoCartera(Razonmora obj) {
        TypedQuery<Razonmora> query = em.createNamedQuery("Razonmora.findByCodigoCartera", Razonmora.class);
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<Razonmora> results = query.getResultList();
        return results;
    }

    @Override
    public List<Razonmora> findByEstado(Razonmora obj) {
        TypedQuery<Razonmora> query = em.createNamedQuery("Razonmora.findByEstado", Razonmora.class);
        query.setParameter("estado", obj.getEstado());
        List<Razonmora> results = query.getResultList();
        return results;
    }

    @Override
    public List<Razonmora> findByUsuarioingreso(Razonmora obj) {
        TypedQuery<Razonmora> query = em.createNamedQuery("Razonmora.findByUsuarioingreso", Razonmora.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<Razonmora> results = query.getResultList();
        return results;
    }

    @Override
    public List<Razonmora> findByFechaingreso(Razonmora obj) {
        TypedQuery<Razonmora> query = em.createNamedQuery("Razonmora.findByFechaingreso", Razonmora.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<Razonmora> results = query.getResultList();
        return results;
    }

    @Override
    public List<Razonmora> findByUsuariomodifico(Razonmora obj) {
        TypedQuery<Razonmora> query = em.createNamedQuery("Razonmora.findByUsuariomodifico", Razonmora.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<Razonmora> results = query.getResultList();
        return results;
    }

    @Override
    public List<Razonmora> findByFechamodifico(Razonmora obj) {
        TypedQuery<Razonmora> query = em.createNamedQuery("Razonmora.findByFechamodifico", Razonmora.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<Razonmora> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(Razonmora obj) {
        em.persist(obj);
    }

    @Override
    public void update(Razonmora obj) {
        em.merge(obj);
    }

    @Override
    public void delete(Razonmora obj) {
        em.merge(obj);
        em.remove(obj);
    }
    
}
