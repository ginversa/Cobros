/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblResultadotercero;
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
public class ResultadoterceroDaoImpl implements ResultadoterceroDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblResultadotercero> findAll() {
        TypedQuery<TblResultadotercero> query = em.createNamedQuery("TblResultadotercero.findAll", TblResultadotercero.class);
        List<TblResultadotercero> results = query.getResultList();
        return results;
    }

    @Override
    public TblResultadotercero findById(TblResultadotercero obj) {
        return em.find(TblResultadotercero.class, obj.getIdResultadotercero());
    }

    @Override
    public TblResultadotercero findByDescripcion(TblResultadotercero obj) {
        TypedQuery<TblResultadotercero> query = em.createNamedQuery("TblResultadotercero.findByDescripcion", TblResultadotercero.class);
        query.setParameter("descripcion", obj.getDescripcion());
        TblResultadotercero results = query.getSingleResult();
        return results;
    }

    @Override
    public TblResultadotercero findByCodigo(TblResultadotercero obj) {
        TypedQuery<TblResultadotercero> query = em.createNamedQuery("TblResultadotercero.findByCodigo", TblResultadotercero.class);
        query.setParameter("codigo", obj.getCodigo());
        TblResultadotercero results = query.getSingleResult();
        return results;
    }

    @Override
    public List<TblResultadotercero> findByUsuarioingreso(TblResultadotercero obj) {
        TypedQuery<TblResultadotercero> query = em.createNamedQuery("TblResultadotercero.findByUsuarioingreso", TblResultadotercero.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblResultadotercero> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblResultadotercero> findByFechaingreso(TblResultadotercero obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblResultadotercero> findByUsuariomodifico(TblResultadotercero obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblResultadotercero> findByFechamodifico(TblResultadotercero obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(TblResultadotercero obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(TblResultadotercero obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(TblResultadotercero obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
