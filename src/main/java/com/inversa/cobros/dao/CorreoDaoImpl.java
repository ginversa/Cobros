/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblCorreo;
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
public class CorreoDaoImpl implements CorreoDao{
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblCorreo> findAll() {
        TypedQuery<TblCorreo> query = em.createNamedQuery("TblCorreo.findAll", TblCorreo.class);
        List<TblCorreo> results = query.getResultList();
        return results;
    }

    @Override
    public TblCorreo findById(TblCorreo obj) {
        return em.find(TblCorreo.class, obj.getIdCorreo());
    }

    @Override
    public TblCorreo findByCorreo(TblCorreo obj) {
        TypedQuery<TblCorreo> query = em.createNamedQuery("TblCorreo.findByCorreo", TblCorreo.class);
        query.setParameter("correo", obj.getCorreo());
        TblCorreo results = query.getSingleResult();
        return results;
    }

    @Override
    public List<TblCorreo> findByRanking(TblCorreo obj) {
        TypedQuery<TblCorreo> query = em.createNamedQuery("TblCorreo.findByRanking", TblCorreo.class);
        query.setParameter("ranking", obj.getRanking());
        List<TblCorreo> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCorreo> findByUsuarioingreso(TblCorreo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblCorreo> findByFechaingreso(TblCorreo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblCorreo> findByUsuariomodifico(TblCorreo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblCorreo> findByFechamodifico(TblCorreo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(TblCorreo obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblCorreo obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblCorreo obj) {
        em.merge(obj);
        em.remove(obj);
    }
    
    /**
     * 
     * @param idContacto
     * @param estado
     * @return 
     */
    public List<TblCorreo> findByContactoEstado(Integer idContacto,String estado){
        String sqlString = "select * from tbl_correo tc where id_contacto = ? and estado = ? order by ranking desc";
        TypedQuery<TblCorreo> query = (TypedQuery<TblCorreo>) em.createNativeQuery(sqlString, TblCorreo.class);
        query.setParameter(1, idContacto);
        query.setParameter(2, estado);
        List<TblCorreo> results = query.getResultList();
        return results;
    }
    
}
