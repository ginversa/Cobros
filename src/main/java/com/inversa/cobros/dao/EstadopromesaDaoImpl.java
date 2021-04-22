/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Estadopromesa;
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
public class EstadopromesaDaoImpl implements EstadopromesaDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<Estadopromesa> findAll() {
        TypedQuery<Estadopromesa> query = em.createNamedQuery("Estadopromesa.findAll", Estadopromesa.class);
        List<Estadopromesa> results = query.getResultList();
        return results;
    }

    @Override
    public Estadopromesa findByIdestadopromesa(Estadopromesa obj) {
        return em.find(Estadopromesa.class, obj.getIdestadopromesa());
    }

    @Override
    public Estadopromesa findByCodigo(Estadopromesa obj) {
        TypedQuery<Estadopromesa> query = em.createNamedQuery("Estadopromesa.findByCodigo", Estadopromesa.class);
        query.setParameter("codigo", obj.getCodigo());
        List<Estadopromesa> found = query.getResultList();
        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }
    }

}
