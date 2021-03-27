/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.ejb;

import com.inversa.reporte.model.GestionesProductividadPorHora;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface GestionesProductividadPorHoraServiceRemote {
    
    public List<GestionesProductividadPorHora> getProductividadPorHora(Date fecha,String codigoCartera);
    
}
