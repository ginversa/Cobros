/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblContacto;
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
public class ContactoDaoImpl implements ContactoDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblContacto> findAll() {
        TypedQuery<TblContacto> query = em.createNamedQuery("TblContacto.findAll", TblContacto.class);
        List<TblContacto> results = query.getResultList();
        return results;
    }

    @Override
    public TblContacto findById(TblContacto obj) {
        return em.find(TblContacto.class, obj.getIdContacto());
    }

    @Override
    public TblContacto findByNombre(TblContacto obj) {
        TypedQuery<TblContacto> query = em.createNamedQuery("TblContacto.findByNombre", TblContacto.class);
        query.setParameter("nombre", obj.getNombre());
        TblContacto results = query.getSingleResult();
        return results;
    }

    @Override
    public TblContacto findByCedula(TblContacto obj) {
        TypedQuery<TblContacto> query = em.createNamedQuery("TblContacto.findByCedula", TblContacto.class);
        query.setParameter("cedula", obj.getCedula());
        List<TblContacto> found = query.getResultList();
        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }
    }

    @Override
    public List<TblContacto> findByUsuarioingreso(TblContacto obj) {
        TypedQuery<TblContacto> query = em.createNamedQuery("TblContacto.findByUsuarioingreso", TblContacto.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblContacto> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblContacto> findByFechaingreso(TblContacto obj) {
        TypedQuery<TblContacto> query = em.createNamedQuery("TblContacto.findByFechaingreso", TblContacto.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<TblContacto> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblContacto> findByUsuariomodifico(TblContacto obj) {
        TypedQuery<TblContacto> query = em.createNamedQuery("TblContacto.findByUsuariomodifico", TblContacto.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<TblContacto> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblContacto> findByFechamodifico(TblContacto obj) {
        TypedQuery<TblContacto> query = em.createNamedQuery("TblContacto.findByFechamodifico", TblContacto.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<TblContacto> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(TblContacto obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblContacto obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblContacto obj) {
        em.merge(obj);
        em.remove(obj);
    }

}
