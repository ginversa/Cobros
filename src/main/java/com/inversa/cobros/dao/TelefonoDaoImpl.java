/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblTelefono;
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
public class TelefonoDaoImpl implements TelefonoDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblTelefono> findAll() {
        TypedQuery<TblTelefono> query = em.createNamedQuery("TblTelefono.findAll", TblTelefono.class);
        query.setParameter("estado", "ACT");
        List<TblTelefono> results = query.getResultList();
        return results;
    }

    @Override
    public TblTelefono findById(TblTelefono obj) {
        return em.find(TblTelefono.class, obj.getIdTelefono());
    }

    @Override
    public TblTelefono findByTelefono(TblTelefono obj) {
        TypedQuery<TblTelefono> query = em.createNamedQuery("TblTelefono.findByTelefono", TblTelefono.class);
        query.setParameter("telefono", obj.getTelefono());
        query.setParameter("estado", "ACT");
        TblTelefono results = query.getSingleResult();
        return results;
    }

    @Override
    public List<TblTelefono> findByRanking(TblTelefono obj) {
        TypedQuery<TblTelefono> query = em.createNamedQuery("TblTelefono.findByRanking", TblTelefono.class);
        query.setParameter("ranking", obj.getRanking());
        query.setParameter("estado", "ACT");
        List<TblTelefono> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblTelefono> findByUsuarioingreso(TblTelefono obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblTelefono> findByFechaingreso(TblTelefono obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblTelefono> findByUsuariomodifico(TblTelefono obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblTelefono> findByFechamodifico(TblTelefono obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(TblTelefono obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblTelefono obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblTelefono obj) {
        em.merge(obj);
        em.remove(obj);
    }
    
    /**
     * 
     * @param obj
     * @return List<TblTelefono>
     */
    public List<TblTelefono> findByContactoEstado(Integer idContacto,String estado){
        String sqlString = "select * from tbl_telefono tc where id_contacto = ? and estado = ? order by ranking desc";
        TypedQuery<TblTelefono> query = (TypedQuery<TblTelefono>) em.createNativeQuery(sqlString, TblTelefono.class);
        query.setParameter(1, idContacto);
        query.setParameter(2, estado);
        List<TblTelefono> results = query.getResultList();
        return results;
    }

}
