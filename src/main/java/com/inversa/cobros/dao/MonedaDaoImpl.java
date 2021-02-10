/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Moneda;
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
public class MonedaDaoImpl implements MonedaDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<Moneda> findAll() {
        TypedQuery<Moneda> query = em.createNamedQuery("Moneda.findAll", Moneda.class);
        List<Moneda> results = query.getResultList();
        return results;
    }

    @Override
    public Moneda findByIdMoneda(Moneda obj) {
        return em.find(Moneda.class, obj.getIdMoneda());
    }

    @Override
    public Moneda findByCodigo(Moneda obj) {
        TypedQuery<Moneda> query = em.createNamedQuery("Moneda.findByCodigo", Moneda.class);
        query.setParameter("codigo", obj.getCodigo());
        Moneda results = query.getSingleResult();
        return results;
    }

}
