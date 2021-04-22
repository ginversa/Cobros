/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Tipodescuento;
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
public class TipodescuentoDaoImpl implements TipodescuentoDao{

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;
    
    @Override
    public List<Tipodescuento> findAll() {
        TypedQuery<Tipodescuento> query = em.createNamedQuery("Tipodescuento.findAll", Tipodescuento.class);
        List<Tipodescuento> results = query.getResultList();
        return results;
    }

    @Override
    public Tipodescuento findByIdtipodescuento(Tipodescuento obj) {
        return em.find(Tipodescuento.class, obj.getIdtipodescuento());
    }

    @Override
    public Tipodescuento findByCodigo(Tipodescuento obj) {
        TypedQuery<Tipodescuento> query = em.createNamedQuery("Tipodescuento.findByCodigo", Tipodescuento.class);
        query.setParameter("codigo", obj.getCodigo());
        List<Tipodescuento> found = query.getResultList();
        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }
    }
    
}
