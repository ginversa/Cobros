/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Tipotelefono;
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
public class TipoTelefonoDaoImpl implements TipoTelefonoDao{    
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<Tipotelefono> findAll() {
        TypedQuery<Tipotelefono> query = em.createNamedQuery("Tipotelefono.findAll", Tipotelefono.class);
        List<Tipotelefono> results = query.getResultList();
        return results;
    }

    @Override
    public Tipotelefono findById(Tipotelefono obj) {
        return em.find(Tipotelefono.class, obj.getIdTipotelefono());
    }

    @Override
    public List<Tipotelefono> findByEstado(Tipotelefono obj) {
        TypedQuery<Tipotelefono> query = em.createNamedQuery("Tipotelefono.findByEstado", Tipotelefono.class);
        query.setParameter("estado", obj.getEstado());
        List<Tipotelefono> results = query.getResultList();
        return results;
    }

    @Override
    public List<Tipotelefono> findByUsuarioingreso(Tipotelefono obj) {
        TypedQuery<Tipotelefono> query = em.createNamedQuery("Tipotelefono.findByUsuarioingreso", Tipotelefono.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<Tipotelefono> results = query.getResultList();
        return results;
    }

    @Override
    public List<Tipotelefono> findByFechaingreso(Tipotelefono obj) {
        TypedQuery<Tipotelefono> query = em.createNamedQuery("Tipotelefono.findByFechaingreso", Tipotelefono.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<Tipotelefono> results = query.getResultList();
        return results;
    }

    @Override
    public List<Tipotelefono> findByUsuariomodifico(Tipotelefono obj) {
        TypedQuery<Tipotelefono> query = em.createNamedQuery("Tipotelefono.findByUsuariomodifico", Tipotelefono.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<Tipotelefono> results = query.getResultList();
        return results;
    }

    @Override
    public List<Tipotelefono> findByFechamodifico(Tipotelefono obj) {
        TypedQuery<Tipotelefono> query = em.createNamedQuery("Tipotelefono.findByFechamodifico", Tipotelefono.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<Tipotelefono> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(Tipotelefono obj) {
        em.persist(obj);
    }

    @Override
    public void update(Tipotelefono obj) {
        em.merge(obj);
    }

    @Override
    public void delete(Tipotelefono obj) {
        em.merge(obj);
        em.remove(obj);
    }
    
}
