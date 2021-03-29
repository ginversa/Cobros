/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.dao;

import com.inversa.reporte.model.GestionProductividadPorAsesor;
import com.inversa.reporte.model.GestionProductividadPorHora;
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
public class GestionProductividadDaoImpl implements GestionProductividadDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<GestionProductividadPorHora> getProductividadPorHora(Date fecha, String codigoCartera) {
        Query query = em.createNativeQuery("select * from gestiones_productividad_porHora(?1,?2)", GestionProductividadPorHora.class);
        query.setParameter(1, fecha, TemporalType.DATE);
        query.setParameter(2, codigoCartera);
        List<GestionProductividadPorHora> results = query.getResultList();
        return results;
    }

    @Override
    public List<GestionProductividadPorAsesor> getProductividadPorAsesor(Date fecha, String codigoCartera) {
        Query query = em.createNativeQuery("select * from gestiones_productividad_porAsesor(?1, ?2);", GestionProductividadPorAsesor.class);
        query.setParameter(1, fecha, TemporalType.DATE);
        query.setParameter(2, codigoCartera);
        List<GestionProductividadPorAsesor> results = query.getResultList();
        return results;
    }

}
