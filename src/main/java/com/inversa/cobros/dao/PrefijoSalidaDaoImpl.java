/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblPrefijoSalida;
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
public class PrefijoSalidaDaoImpl implements PrefijoSalidaDao{
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblPrefijoSalida> findAll() {
        TypedQuery<TblPrefijoSalida> query = em.createNamedQuery("TblPrefijoSalida.findAll", TblPrefijoSalida.class);
        List<TblPrefijoSalida> results = query.getResultList();
        return results;
    }

    @Override
    public TblPrefijoSalida findById(TblPrefijoSalida obj) {
        return em.find(TblPrefijoSalida.class, obj.getId());
    }
    
}
