/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Subtipificacion;
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
public class SubtipificacionDaoImpl implements SubtipificacionDao{
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<Subtipificacion> findAll() {
        TypedQuery<Subtipificacion> query = em.createNamedQuery("Subtipificacion.findAll", Subtipificacion.class);
        List<Subtipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public Subtipificacion findById(Subtipificacion obj) {
        return em.find(Subtipificacion.class, obj.getIdSubtipificacion());
    }
    
    public List<Subtipificacion> findByIdTipificacion(Subtipificacion obj){
        TypedQuery<Subtipificacion> query = em.createNamedQuery("Subtipificacion.findByIdTipificacion", Subtipificacion.class);
        query.setParameter("idTipificacion", obj.getIdTipificacion().getIdTipificacion());
        List<Subtipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Subtipificacion> findByDescripcion(Subtipificacion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Subtipificacion> findByCodigoCartera(Subtipificacion obj) {
        TypedQuery<Subtipificacion> query = em.createNamedQuery("Subtipificacion.findByCodigoCartera", Subtipificacion.class);
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<Subtipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Subtipificacion> findByEstado(Subtipificacion obj) {
        TypedQuery<Subtipificacion> query = em.createNamedQuery("Subtipificacion.findByEstado", Subtipificacion.class);
        query.setParameter("estado", obj.getEstado());
        List<Subtipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Subtipificacion> findByUsuarioingreso(Subtipificacion obj) {
        TypedQuery<Subtipificacion> query = em.createNamedQuery("Subtipificacion.findByUsuarioingreso", Subtipificacion.class);
        query.setParameter("usuarioingreso", obj.getEstado());
        List<Subtipificacion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Subtipificacion> findByFechaingreso(Subtipificacion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Subtipificacion> findByUsuariomodifico(Subtipificacion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Subtipificacion> findByFechamodifico(Subtipificacion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Subtipificacion obj) {
        em.persist(obj);
    }

    @Override
    public void update(Subtipificacion obj) {
        em.merge(obj);
    }

    @Override
    public void delete(Subtipificacion obj) {
        em.merge(obj);
        em.remove(obj);
    }
    
}
