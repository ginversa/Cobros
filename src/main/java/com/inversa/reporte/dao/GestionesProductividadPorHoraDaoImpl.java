/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.dao;

import com.inversa.reporte.model.GestionesProductividadPorHora;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Z420WK
 */
@Stateless
public class GestionesProductividadPorHoraDaoImpl implements GestionesProductividadPorHoraDao{
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<GestionesProductividadPorHora> getProductividadPorHora(Date fecha, String codigoCartera) {
        Query query = em.createNativeQuery("select * from gestiones_productividad_porHora(?1,?2)", GestionesProductividadPorHora.class);
        query.setParameter(1, fecha,TemporalType.DATE);
        query.setParameter(2, codigoCartera);
        List<GestionesProductividadPorHora> results = query.getResultList();
        return results;
    }
    
}
