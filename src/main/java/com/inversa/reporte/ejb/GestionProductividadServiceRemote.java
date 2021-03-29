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
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface GestionProductividadServiceRemote {
    
    public List<GestionProductividadPorHora> getProductividadPorHora(Date fecha,String codigoCartera);
    
    public List<GestionProductividadPorAsesor> getProductividadPorAsesor(Date fecha,String codigoCartera);
    
}
