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

/**
 *
 * @author Z420WK
 */
public interface GestionProductividadDao {
    
    public List<GestionProductividadPorHora> getProductividadPorHora(Date fecha,String codigoCartera);
    
    public List<GestionProductividadPorAsesor> getProductividadPorAsesor(Date fecha,String codigoCartera);
}
