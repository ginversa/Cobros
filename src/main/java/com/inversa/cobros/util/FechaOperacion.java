/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import java.util.Date;

/**
 *
 * @author Z420WK
 */
public class FechaOperacion {
    
    
    /**
     * call_length; es el tiempo total de la llamada. Ese tiemoo es desde que
     * entra al sistema y hace ring
     * @param date_ini
     * @param date_end
     * @return call_length
     */
    public Date restarFechar(Date date_ini, Date date_end) {

        Long call_length = Long.valueOf(0);
        
        boolean isTrue_date_ini = date_ini != null ? true:false;
        boolean isTrue_date_end = date_end != null ? true:false;
        
        if (isTrue_date_ini && isTrue_date_end) {
            call_length = date_end.getTime() - date_ini.getTime();
        }

        return new Date(call_length);
    }
}
