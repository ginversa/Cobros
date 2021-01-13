/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Tipificacion;
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
public class TipificacionDaoImpl implements TipificacionDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<Tipificacion> findAll() {
        TypedQuery<Tipificacion> query = em.createNamedQuery("Tipificacion.findAll", Tipificacion.class);
        List<Tipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public Tipificacion findById(Tipificacion obj) {
        return em.find(Tipificacion.class, obj.getIdTipificacion());
    }

    @Override
    public List<Tipificacion> findByDescripcion(Tipificacion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tipificacion> findByCodigoCartera(Tipificacion obj) {
        TypedQuery<Tipificacion> query = em.createNamedQuery("Tipificacion.findByCodigoCartera", Tipificacion.class);
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<Tipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Tipificacion> findByEstado(Tipificacion obj) {
        TypedQuery<Tipificacion> query = em.createNamedQuery("Tipificacion.findByEstado", Tipificacion.class);
        query.setParameter("estado", obj.getEstado());
        List<Tipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Tipificacion> findByUsuarioingreso(Tipificacion obj) {
        TypedQuery<Tipificacion> query = em.createNamedQuery("Tipificacion.findByUsuarioingreso", Tipificacion.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<Tipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Tipificacion> findByFechaingreso(Tipificacion obj) {
        TypedQuery<Tipificacion> query = em.createNamedQuery("Tipificacion.findByFechaingreso", Tipificacion.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<Tipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Tipificacion> findByUsuariomodifico(Tipificacion obj) {
        TypedQuery<Tipificacion> query = em.createNamedQuery("Tipificacion.findByUsuariomodifico", Tipificacion.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<Tipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Tipificacion> findByFechamodifico(Tipificacion obj) {
        TypedQuery<Tipificacion> query = em.createNamedQuery("Tipificacion.findByFechamodifico", Tipificacion.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<Tipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(Tipificacion obj) {
        em.persist(obj);
    }

    @Override
    public void update(Tipificacion obj) {
        em.merge(obj);
    }

    @Override
    public void delete(Tipificacion obj) {
        em.merge(obj);
        em.remove(obj);
    }

}
