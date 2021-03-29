/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.ejb;

import com.inversa.reporte.model.GestionProductividadPorAsesor;
import com.inversa.reporte.model.GestionProductividadPorHora;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.inversa.reporte.dao.GestionProductividadDao;

/**
 *
 * @author Z420WK
 */

@Stateless
public class GestionProductividadServiceImpl implements GestionProductividadService,GestionProductividadServiceRemote{
    
    @Inject
    private GestionProductividadDao dao;

    @Override
    public List<GestionProductividadPorHora> getProductividadPorHora(Date fecha, String codigoCartera) {
        return dao.getProductividadPorHora(fecha, codigoCartera);
    }

    @Override
    public List<GestionProductividadPorAsesor> getProductividadPorAsesor(Date fecha, String codigoCartera) {
        return dao.getProductividadPorAsesor(fecha, codigoCartera);
    }
    
}
