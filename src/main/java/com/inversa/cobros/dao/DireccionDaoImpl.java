/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblDireccion;
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
public class DireccionDaoImpl implements DireccionDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblDireccion> findAll() {
        TypedQuery<TblDireccion> query = em.createNamedQuery("TblDireccion.findAll", TblDireccion.class);
        List<TblDireccion> results = query.getResultList();
        return results;
    }

    @Override
    public TblDireccion findById(TblDireccion obj) {
        return em.find(TblDireccion.class, obj.getIdDireccion());
    }

    @Override
    public TblDireccion findByDireccion(TblDireccion obj) {
        TypedQuery<TblDireccion> query = em.createNamedQuery("TblDireccion.findByDireccion", TblDireccion.class);
        query.setParameter("direccion", obj.getDireccion());
        TblDireccion results = query.getSingleResult();
        return results;
    }

    @Override
    public List<TblDireccion> findByRanking(TblDireccion obj) {
        TypedQuery<TblDireccion> query = em.createNamedQuery("TblDireccion.findByRanking", TblDireccion.class);
        query.setParameter("ranking", obj.getRanking());
        List<TblDireccion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblDireccion> findByUsuarioingreso(TblDireccion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblDireccion> findByFechaingreso(TblDireccion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblDireccion> findByUsuariomodifico(TblDireccion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblDireccion> findByFechamodifico(TblDireccion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(TblDireccion obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblDireccion obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblDireccion obj) {
        em.merge(obj);
        em.remove(obj);
    }

}
