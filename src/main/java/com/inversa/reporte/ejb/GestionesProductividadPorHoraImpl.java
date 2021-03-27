/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.ejb;

import com.inversa.reporte.dao.GestionesProductividadPorHoraDao;
import com.inversa.reporte.model.GestionesProductividadPorHora;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Z420WK
 */

@Stateless
public class GestionesProductividadPorHoraImpl implements GestionesProductividadPorHoraService,GestionesProductividadPorHoraServiceRemote{
    
    @Inject
    private GestionesProductividadPorHoraDao dao;

    @Override
    public List<GestionesProductividadPorHora> getProductividadPorHora(Date fecha, String codigoCartera) {
        return dao.getProductividadPorHora(fecha, codigoCartera);
    }
    
}
