/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.dao;

import com.inversa.findme.model.LaboralHistorico;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Z420WK
 */
@Stateless
public class LaboralHistoricoDaoImpl implements LaboralHistoricoDao{
    
    @PersistenceContext(unitName = "findmePU")
    EntityManager em;

    @Override
    public LaboralHistorico getPoseeTrabajo(String identificacion) {
        Query query = em.createNativeQuery("select lh.* from laboral_historico lh where lh.cedula = ?1 and lh.periodo in (select max(periodo) from laboral_historico)", LaboralHistorico.class);
        query.setParameter(1, identificacion);        
        List<LaboralHistorico> found = query.getResultList();
        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }
    }

    @Override
    public LaboralHistorico getRangoBalance(String identificacion, BigDecimal cantidad, int condicion) {
        String sql = null;
        if(condicion == -1){// menor que
            sql = "select lh.* from laboral_historico lh where lh.cedula = ?1 and CAST (lh.ingreso AS numeric) < ?2";
        }
        
        if(condicion == 0){// igual
            sql = "select lh.* from laboral_historico lh where lh.cedula = ?1 and CAST (lh.ingreso AS numeric) = ?2";
        }
        
        if(condicion == 1){// mayor que
            sql = "select lh.* from laboral_historico lh where lh.cedula = ?1 and CAST (lh.ingreso AS numeric) > ?2";
        }
        
        Query query = em.createNativeQuery(sql, LaboralHistorico.class);
        query.setParameter(1, identificacion);
        query.setParameter(2, cantidad);
        List<LaboralHistorico> found = query.getResultList();
        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found.get(0);
        }
    }
    
}
