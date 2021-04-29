/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblSaldo;
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
public class SaldoDaoImpl implements SaldoDao{
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblSaldo> findAll() {
        TypedQuery<TblSaldo> query = em.createNamedQuery("TblSaldo.findAll", TblSaldo.class);
        List<TblSaldo> results = query.getResultList();
        return results;
    }

    @Override
    public TblSaldo findByIdSaldo(TblSaldo obj) {
        return em.find(TblSaldo.class, obj.getIdSaldo());
    }

    @Override
    public void insert(TblSaldo obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblSaldo obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblSaldo obj) {
        em.merge(obj);
        em.remove(obj);
    }
    
}
