/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Cartera;
import com.inversa.cobros.model.TblGestion;
import java.util.List;
import javax.ejb.Stateless;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Z420WK
 */
@Stateless
public class GestionDaoImpl implements GestionDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblGestion> findAll() {
        TypedQuery<TblGestion> query = em.createNamedQuery("TblGestion.findAll", TblGestion.class);
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public TblGestion findById(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByIdGestion");
        query.setParameter("idGestion", obj.getIdGestion());
        List<TblGestion> found = query.getResultList();

        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }
    }

    @Override
    public List<TblGestion> findByCodigoGestor(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByCodigoGestor");
        query.setParameter("codigoGestor", obj.getCodigoGestor());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblGestion> findByCodigoCartera(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByCodigoCartera");
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblGestion> findByNombreCliente(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByNombreCliente");
        query.setParameter("nombreCliente", obj.getNombreCliente());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblGestion> findByIdentificacion(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByIdentificacion");
        query.setParameter("identificacion", obj.getIdentificacion());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblGestion> findByFechaGestion(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByFechaGestion");
        query.setParameter("fechaGestion", obj.getFechaGestion());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblGestion> findByEstado(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByEstado");
        query.setParameter("estado", obj.getEstado());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblGestion> findByUsuarioingreso(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByUsuarioingreso");
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblGestion> findByFechaingreso(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByFechaingreso");
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblGestion> findByUsuariomodifico(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByUsuariomodifico");
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblGestion> findByFechamodifico(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByFechamodifico");
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public Long insert(TblGestion obj) {
        Long idGestion = null;
        try {
            em.persist(obj);
            em.flush();
            em.refresh(obj);
            System.out.println("Gestion ID: " + obj.getIdGestion());
            idGestion = obj.getIdGestion();
        } catch (ConstraintViolationException e) {            
            System.out.println("insert Gestion : " + e.getMessage());
        }
        
        return idGestion;
    }

    @Override
    public void update(TblGestion obj) {
        em.merge(obj);
        em.flush();
        //em.refresh(obj);
        System.out.println("Gestion ID: " + obj.getIdGestion());
    }

    @Override
    public void delete(TblGestion obj) {
        em.merge(obj);
        em.remove(obj);
    }

    @Override
    public TblGestion findByCodigoCarteraANDIdentificacion(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByCodigoCarteraANDIdentificacion");
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        query.setParameter("identificacion", obj.getIdentificacion());
        List<TblGestion> found = query.getResultList();

        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }
    }

    @Override
    public List<TblGestion> findByIdentificacionANDCodigoCartera(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByIdentificacionANDCodigoCartera");
        query.setParameter("identificacion", obj.getIdentificacion());
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<TblGestion> results = query.getResultList();
        return results;
    }

    @Override
    public List<Cartera> findCarteraByDistinc() {
        Query query = em.createNativeQuery("select distinct tg.codigo_cartera, tg.nombre_cartera from tbl_gestion tg", Cartera.class);
        List<Cartera> results = query.getResultList();
        if (results != null && !results.isEmpty() && results.size() > 0) {
            return results;
        }

        return null;
    }

    @Override
    public List<TblGestion> findByCodigoGestorANDCodigoCartera(TblGestion obj) {
        Query query = em.createNamedQuery("TblGestion.findByCodigoGestorANDCodigoCartera", TblGestion.class);
        query.setParameter("codigoGestor", obj.getCodigoGestor());
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<TblGestion> results = query.getResultList();
        return results;
    }

}
