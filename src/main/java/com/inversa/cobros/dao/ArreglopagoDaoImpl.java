/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Arreglopago;
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
public class ArreglopagoDaoImpl implements ArreglopagoDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<Arreglopago> findAll() {
        TypedQuery<Arreglopago> query = em.createNamedQuery("Arreglopago.findAll", Arreglopago.class);
        List<Arreglopago> results = query.getResultList();
        return results;
    }

    @Override
    public Arreglopago findByIdarreglopago(Arreglopago obj) {
        return em.find(Arreglopago.class, obj.getIdarreglopago());
    }

    @Override
    public Arreglopago findByCodigo(Arreglopago obj) {
        TypedQuery<Arreglopago> query = em.createNamedQuery("Arreglopago.findByCodigo", Arreglopago.class);
        query.setParameter("codigo", obj.getCodigo());
        List<Arreglopago> found = query.getResultList();
        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }

    }

}
