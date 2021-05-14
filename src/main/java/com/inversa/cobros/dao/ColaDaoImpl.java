/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblCola;
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
public class ColaDaoImpl implements ColaDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblCola> findAll() {
        TypedQuery<TblCola> query = em.createNamedQuery("TblCola.findAll", TblCola.class);
        List<TblCola> results = query.getResultList();
        return results;
    }

    @Override
    public TblCola findByIdCola(TblCola obj) {
        return em.find(TblCola.class, obj.getIdCola());
    }

    @Override
    public List<TblCola> findByCodigoCartera(TblCola obj) {
        TypedQuery<TblCola> query = em.createNamedQuery("TblCola.findByCodigoCartera", TblCola.class);
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<TblCola> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCola> findByCodigoGestor(TblCola obj) {
        TypedQuery<TblCola> query = em.createNamedQuery("TblCola.findByCodigoGestor", TblCola.class);
        query.setParameter("codigoGestor", obj.getCodigoGestor());
        List<TblCola> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCola> findByIdentificacion(TblCola obj) {
        TypedQuery<TblCola> query = em.createNamedQuery("TblCola.findByIdentificacion", TblCola.class);
        query.setParameter("identificacion", obj.getIdentificacion());
        List<TblCola> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(TblCola obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblCola obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblCola obj) {
        em.merge(obj);
        em.remove(obj);
    }

    @Override
    public List<TblCola> findByCodigoGestorAndIdFiltro(TblCola obj) {
        Query query = em.createNativeQuery("select cola.* from tbl_cola cola where cola.id_filtrocola = ?1 and cola.codigo_cartera = ?2 and cola.estado = 'SIN' order by cola.saldo_colones desc", TblCola.class);
        query.setParameter(1, obj.getIdFiltrocola().getIdFiltrocola());
        query.setParameter(2, obj.getCodigoCartera());
        List<TblCola> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCola> findByCodigoGestorAndIdFiltroLimit_ONE(TblCola obj) {
        Query query = em.createNativeQuery("select cola.* from tbl_cola cola where cola.id_filtrocola = ?1 and cola.codigo_cartera = ?2 and cola.estado = 'SIN' order by cola.saldo_colones desc limit 1", TblCola.class);
        query.setParameter(1, obj.getIdFiltrocola().getIdFiltrocola());
        query.setParameter(2, obj.getCodigoCartera());
        List<TblCola> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCola> findByIdFiltro(TblCola obj) {
        Query query = em.createNativeQuery("select cola.* from tbl_cola cola where cola.id_filtrocola = ?1", TblCola.class);
        query.setParameter(1, obj.getIdFiltrocola().getIdFiltrocola());
        List<TblCola> results = query.getResultList();
        return results;
    }

    @Override
    public TblCola buscarSiguienteClienteEnCola(TblCola siguienteEnCola) {
        Query query = em.createNativeQuery("select cola.* from tbl_cola cola where cola.id_filtrocola = ?1 and cola.codigo_cartera = ?2 and cola.estado = 'SIN' order by cola.saldo_colones desc", TblCola.class);
        query.setParameter(1, siguienteEnCola.getIdFiltrocola().getIdFiltrocola());
        query.setParameter(2, siguienteEnCola.getCodigoCartera());
        List<TblCola> found = query.getResultList();
        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }
    }

    @Override
    public void updateIdGestion(Long id_gestion, Long id_filtrocola, Long id_cola, String identificacion) {
        try {

            int countUpdated = em.createNativeQuery("update tbl_cola set id_gestion = ?1 where id_filtrocola = ?2 and id_cola = ?3 and identificacion = ?4")
                    .setParameter(1, id_gestion)
                    .setParameter(2, id_filtrocola)
                    .setParameter(3, id_cola)
                    .setParameter(4, identificacion)
                    .executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
