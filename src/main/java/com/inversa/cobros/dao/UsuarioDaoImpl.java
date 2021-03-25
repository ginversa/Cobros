/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblUsuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Z420WK
 */
@Stateless
public class UsuarioDaoImpl implements UsuarioDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblUsuario> findAll() {
        TypedQuery<TblUsuario> query = em.createNamedQuery("TblUsuario.findAll", TblUsuario.class);
        List<TblUsuario> results = query.getResultList();
        return results;
    }

    @Override
    public TblUsuario findById(TblUsuario obj) {
        return em.find(TblUsuario.class, obj.getIdPersona());
    }

    @Override
    public TblUsuario findByUsuario(TblUsuario obj) {
        Query query = em.createNamedQuery("TblUsuario.findByUsuario", TblUsuario.class);
        query.setParameter("usuario", obj.getUsuario());
        TblUsuario results = (TblUsuario) query.getSingleResult();
        return results;
    }

    @Override
    public TblUsuario findByClave(TblUsuario obj) {
        Query query = em.createNamedQuery("TblUsuario.findByClave", TblUsuario.class);
        query.setParameter("clave", obj.getClave());
        TblUsuario results = (TblUsuario) query.getSingleResult();
        return results;
    }

    @Override
    public TblUsuario findByCodigoGestor(TblUsuario obj) {
        Query query = em.createNamedQuery("TblUsuario.findByCodigoGestor", TblUsuario.class);
        query.setParameter("codigoGestor", obj.getCodigoGestor());
        TblUsuario results = (TblUsuario) query.getSingleResult();
        return results;
    }

    @Override
    public List<TblUsuario> findByEstado(TblUsuario obj) {
        TypedQuery<TblUsuario> query = em.createNamedQuery("TblUsuario.findByEstado", TblUsuario.class);
        query.setParameter("estado", obj.getEstado());
        List<TblUsuario> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblUsuario> findByUsuarioingreso(TblUsuario obj) {
        TypedQuery<TblUsuario> query = em.createNamedQuery("TblUsuario.findByUsuarioingreso", TblUsuario.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblUsuario> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblUsuario> findByFechaingreso(TblUsuario obj) {
        TypedQuery<TblUsuario> query = em.createNamedQuery("TblUsuario.findByFechaingreso", TblUsuario.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<TblUsuario> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblUsuario> findByUsuariomodifico(TblUsuario obj) {
        TypedQuery<TblUsuario> query = em.createNamedQuery("TblUsuario.findByUsuariomodifico", TblUsuario.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<TblUsuario> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblUsuario> findByFechamodifico(TblUsuario obj) {
        TypedQuery<TblUsuario> query = em.createNamedQuery("TblUsuario.findByFechamodifico", TblUsuario.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<TblUsuario> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(TblUsuario obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblUsuario obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblUsuario obj) {
        em.merge(obj);
        em.remove(obj);
    }

    @Override
    public TblUsuario findByUsuarioAndClave(TblUsuario obj) {
        TypedQuery<TblUsuario> query = em.createNamedQuery("TblUsuario.findByUsuarioAndClave", TblUsuario.class);
        query.setParameter("usuario", obj.getUsuario());
        query.setParameter("clave", obj.getClave());
        
        List<TblUsuario> found = query.getResultList();
        
         if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }
    }

}
