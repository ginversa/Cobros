/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblResultadogestion;
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
public class ResultadogestionDaoImpl implements ResultadogestionDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblResultadogestion> findAll() {
        TypedQuery<TblResultadogestion> query = em.createNamedQuery("TblResultadogestion.findAll", TblResultadogestion.class);
        List<TblResultadogestion> results = query.getResultList();
        return results;
    }

    @Override
    public TblResultadogestion findByIdResultadogestion(TblResultadogestion obj) {
        return em.find(TblResultadogestion.class, obj.getIdResultadogestion());

    }

    @Override
    public TblResultadogestion findByDescripcion(TblResultadogestion obj) {
        TypedQuery<TblResultadogestion> query = em.createNamedQuery("TblResultadogestion.findByDescripcion", TblResultadogestion.class);
        query.setParameter("descripcion", obj.getDescripcion());
        TblResultadogestion results = query.getSingleResult();
        return results;
    }

    @Override
    public TblResultadogestion findByCodigo(TblResultadogestion obj) {
        TypedQuery<TblResultadogestion> query = em.createNamedQuery("TblResultadogestion.findByCodigo", TblResultadogestion.class);
        query.setParameter("codigo", obj.getCodigo());
        TblResultadogestion results = query.getSingleResult();
        return results;
    }

    @Override
    public List<TblResultadogestion> findByUsuarioingreso(TblResultadogestion obj) {
        TypedQuery<TblResultadogestion> query = em.createNamedQuery("TblResultadogestion.findByUsuarioingreso", TblResultadogestion.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblResultadogestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblResultadogestion> findByFechaingreso(TblResultadogestion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblResultadogestion> findByUsuariomodifico(TblResultadogestion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblResultadogestion> findByFechamodifico(TblResultadogestion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(TblResultadogestion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(TblResultadogestion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(TblResultadogestion obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
