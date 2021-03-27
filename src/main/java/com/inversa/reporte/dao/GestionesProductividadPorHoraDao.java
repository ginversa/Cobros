/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.dao;

import com.inversa.reporte.model.GestionesProductividadPorHora;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface GestionesProductividadPorHoraDao {
    
    public List<GestionesProductividadPorHora> getProductividadPorHora(Date fecha,String codigoCartera);
}
